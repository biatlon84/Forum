package com.biathlon84.forum;

import com.biathlon84.forum.config.Routes;
import com.biathlon84.forum.model.entity.User;
import com.biathlon84.forum.model.dto.UserRegistrationForm;
import com.biathlon84.forum.user.UserServiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class HelloController {

    @Autowired private UserServiceDAO userService;

    @GetMapping(value = {"/hello", "/welcome"})
    public String hello() {
        return "hello";
    }

    @PostMapping(value = "/hello")
    public String loginOrRegister(@ModelAttribute("email") String email, RedirectAttributes model) {
        User user = userService.findByEmail(email);
        model.addFlashAttribute("email", email);

//        if (user == null) {
//            user = userService.findByUsername(email);
//        }


        if (user == null) {
            model.addFlashAttribute("userRegistrationForm", new UserRegistrationForm());
            return Routes.redirectNewUserFormPage();
        }
        model.addFlashAttribute("username", user.getUsername());
        return Routes.redirectLoginPage();
    }

}
