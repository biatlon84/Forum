package com.biathlon84.forum;

import com.biathlon84.forum.model.entity.Post;
import com.biathlon84.forum.model.entity.Topic;
import com.biathlon84.forum.model.entity.User;
import com.biathlon84.forum.model.front.PostFront;
import com.biathlon84.forum.model.front.SectionFront;
import com.biathlon84.forum.model.front.UserActivity;
import com.biathlon84.forum.post.PostService;
import com.biathlon84.forum.section.SectionService;
import com.biathlon84.forum.topic.TopicFront;
import com.biathlon84.forum.topic.TopicService;
import com.biathlon84.forum.user.UserServiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Controller
public class HomeController {

    @Autowired
    private SectionService sectionService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserServiceDAO userServiceDAO;
    @Autowired
    private UserServiceDAO userService;

    @RequestMapping(value = {"/", "/home"})
    public String home(Model model, Authentication authentication) {
//        String email = authentication.getName();
//        User user = userService.findByEmail(email);
//        if(user.isRemoved()) {
//            return "redirect:/myprofile/delete";
//        }
        model.addAttribute("sections", sectionService.findAll());
        model.addAttribute("topics",topicService.findRecent() );
        model.addAttribute("posts", postService.findRecentFront());
        return "home";
    }
}
