package com.biathlon84.forum.topic;

import com.biathlon84.forum.model.entity.Topic;
import com.biathlon84.forum.model.dto.NewPostForm;
import com.biathlon84.forum.model.entity.Post;
import com.biathlon84.forum.model.dto.NewTopicForm;
import com.biathlon84.forum.model.entity.User;
import com.biathlon84.forum.model.front.PostFront;
import com.biathlon84.forum.post.PostService;
import com.biathlon84.forum.section.SectionService;
import com.biathlon84.forum.user.UserServiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;


@Controller
@RequestMapping("/topic/")
public class TopicController {

    @Autowired private PostService postService;
    @Autowired private TopicService topicService;
    @Autowired private SectionService sectionService;
    @Autowired private UserServiceDAO userService;

    @RequestMapping(value = "{idTopic}", method = RequestMethod.GET)
    public String getTopicById(@PathVariable int idTopic, Model model) {

        Topic topic = topicService.findOne(idTopic);
        topic.setViews(topic.getViews() + 1);
        System.out.println("getTopicById views = "+topic.getViews());
        topicService.save(topic);

        Set<Post> posts =postService.findByTopic(idTopic);
        //post.stream().findFirst().map(a->(a.getUser().getInfo().))
        TopicFront topicFront = topicService.getFront(topic);
        model.addAttribute("topic", topicFront);
        model.addAttribute("userOwner",userService.convertToUserFront(topic.getUser()));
        model.addAttribute("posts", postService.convertToFront(posts));
        model.addAttribute("newPost", new NewPostForm());
        return "topic";
    }






    @RequestMapping(value = "{idTopic}", method = RequestMethod.POST)
    public String addPost(
//            @Valid
            @ModelAttribute("newPost") NewPostForm newPost,
            BindingResult result,
            Authentication authentication,
            @PathVariable int idTopic,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("topic", topicService.findOne(idTopic));
            model.addAttribute("posts", postService.findByTopic(idTopic));
            return "topic";
        }
        User user = userService.findByEmail(authentication.getName());
        PostFront post =postService.convertToFront(postService.getNewPost(topicService.findOne(idTopic),user,newPost.getContent()));
        post.setModificationDate(new Date(System.currentTimeMillis()));
        System.out.println(post);

        post.getUser().setLastLoginTime(new Date(System.currentTimeMillis()));

        post.getTopic().setLastUpdateDate(LocalDateTime.now());
        postService.save(postService.convertFromFront(post,user));
        //postService.modifiyByAddPost(post);


        model.asMap().clear();
        System.out.println(idTopic); //how is
        return "redirect:/topic/" + idTopic;
    }





    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String getNewTopicForm(Model model) {
        model.addAttribute("newTopic", new NewTopicForm());
        model.addAttribute("sections", sectionService.findAll());
        return "new_topic_form";
    }




    @RequestMapping(value = "new", method = RequestMethod.POST)
    public String processAndAddNewTopic(
//            @Valid
            @ModelAttribute("newTopic") NewTopicForm newTopic,
            BindingResult result,
            Authentication authentication,
            Model model) {
        System.out.println("processAndAddNewTopic");
        Topic topic = new Topic();
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        topic.setUser(user);
        topic.setTitle(newTopic.getTitle());
        topic.setContent(newTopic.getContent());
        topic.setSection(sectionService.findOne(newTopic.getSectionId()));
        topicService.save(topic);

        return "redirect:/topic/" + topic.getId();
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable int id,
                         Authentication authentication,
                         RedirectAttributes model) {
        Topic topic = topicService.findOne(id);

        if (topic == null) {
            return "redirect:/";
        }
        if (!authentication.getName().equals(topic.getUser().getEmail())) {
            return "redirect:/topic/" + id;
        }

        topicService.delete(topic);

        model.addFlashAttribute("message", "topic.successfully.deleted");
        return "redirect:/section/" + topic.getSection().getId();
    }

}
