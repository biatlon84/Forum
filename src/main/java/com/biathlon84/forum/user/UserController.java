package com.biathlon84.forum.user;

import com.biathlon84.forum.model.entity.Section;
import com.biathlon84.forum.model.entity.Topic;
import com.biathlon84.forum.model.entity.User;
import com.biathlon84.forum.model.entity.UserProfile;
import com.biathlon84.forum.model.front.ProfileViewService;
import com.biathlon84.forum.model.front.UserActivity;
import com.biathlon84.forum.model.front.UserProfileFront;
import com.biathlon84.forum.post.PostService;
import com.biathlon84.forum.section.SectionService;
import com.biathlon84.forum.topic.TopicService;
import com.biathlon84.forum.user.exception.UserNotFoundException;
import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.biathlon84.forum.userProfile.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class UserController {

    @Autowired
    private UserServiceDAO userServiceDAO;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private ProfileViewService profileViewService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private PostService postService;

    @Autowired
    private SectionService sectionService;
    // String path=request.getSession().getServletContext().getRealPath("/resources/public/img/pp/");
    @Value("${upload.path}")
    String path;
    @RequestMapping(value = "/user/{username}")
    public String findUserByUsernameAndViewProfilePage(@PathVariable String username,
        Model model) {
        UserProfile userProfile;
        try {
            userProfile = userProfileService.findOne(username);
        } catch (NullPointerException e) {
            throw new UserNotFoundException();
        }
        User user = userServiceDAO.findByUsername(username);
        UserActivity userActivity = profileViewService.getFrontUserActivity(topicService.findByUser(user),postService.findByUser(user),sectionService.findAll());
        model.addAttribute("userActivity", userActivity);
        model.addAttribute("userProfileFront", userProfileService.getUserProfileFront(userProfile));
        return "user";
    }

    @RequestMapping(value = "/users")
    public String listOfAllUser(Model model) {
        model.addAttribute("users", userServiceDAO.findAll());
        return "users";
    }

    @RequestMapping(value = "/myprofile")
    public String myProfile(Authentication authentication,Model model) {

        String email = authentication.getName();
        User user = userServiceDAO.findByEmail(email);
        if(user.isRemoved()) {
                return "redirect:/";
        }
        UserProfileFront userProfileFront1;
        try {
            userProfileFront1 = userProfileService.getUserProfileFront(user.getInfo());
        } catch (NullPointerException e) {
            throw new UserNotFoundException();
        }

        UserActivity userActivity = profileViewService.getFrontUserActivity(topicService.findByUser(user),postService.findByUser(user),sectionService.findAll());
        model.addAttribute("userActivity", userActivity);
        System.out.println(userProfileFront1);

        model.addAttribute("userProfileFront", userProfileFront1);
        return "user";
    }


    @RequestMapping(value = "/myprofile/edit")
    public String myProfileEdit(Authentication authentication,Model model) {
        String email = authentication.getName();
        User user = userServiceDAO.findByEmail(email);
        if(user.isRemoved()) {
            return "redirect:/";
        }
        UserProfile userProfile;
        try {
            userProfile = userProfileService.findByEmail(email);
       } catch (NullPointerException e) {
            throw new UserNotFoundException();
       }
        UserProfileFront userProfileFront= userProfileService.getUserProfileFront(userProfile);
        model.addAttribute("userProfileFront", userProfileFront);
        return "user_edit_form";
    }


    @PostMapping(value = "/myprofile/edit")
    public String myProfileEditPost(@ModelAttribute("userProfile") UserProfile userProfile, Authentication authentication) {
        String email = authentication.getName();
        User user = userServiceDAO.findByEmail(email);
        if(user.isRemoved()) {
            return "redirect:/";
        }
        Section section = sectionService.getDefault();
        sectionService.save(section);
        Topic topic = topicService.genDefault(user,section);
        topicService.save(topic);
        postService.save(postService.getNewPost(topic, user,"def post"));
        return "redirect:/myprofile";
    }


    @RequestMapping(value = "/myprofile/edit/picture", method = RequestMethod.POST)
    public String processAndSaveProfilePicture(@RequestPart MultipartFile profilePicture, HttpServletRequest request, Authentication authentication, RedirectAttributes redirectModel) {
        if (authentication.getName() == null) {
            return "redirect:/login";
        }
        if (profilePicture.isEmpty()) {
            return "redirect:/myprofile";
        }
        User user = userServiceDAO.findByEmail(authentication.getName());
        if(user.isRemoved()) {
            return "redirect:/";
        }

        try {
            profilePicture.transferTo(new File(path + user.getId() + ".jpg"));
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        user.getInfo().setHasPicture(true);
        redirectModel.addFlashAttribute("message", "user.picture.successfully.saved");
        return "redirect:/myprofile";
    }

    @GetMapping(value = "/myprofile/delete")
    public String myProfileDeletePost(@ModelAttribute("userProfile") UserProfile userProfile,HttpServletRequest request,
                                      HttpServletResponse response, Authentication authentication) {

        String email = authentication.getName();

        try {
            userServiceDAO.deleteMark(email);
            if (authentication != null) {
                new SecurityContextLogoutHandler().logout(request, response, authentication);
            }
            return "redirect:/login?logout=true";
        } catch (NullPointerException e) {
            throw new UserNotFoundException();
        }
    }

    @RequestMapping(value = "/logout")
    public String logOutAndRedirectToLoginPage(Authentication authentication,
        HttpServletRequest request,
        HttpServletResponse response) {
        String email = authentication.getName();
        User user = userServiceDAO.findByEmail(email);
        if(user.isRemoved()) {
            return "redirect:/";
        }
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?logout=true";
    }
}
