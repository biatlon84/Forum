package com.biathlon84.forum.user;

import com.biathlon84.forum.config.Routes;
import com.biathlon84.forum.model.dto.UserRegistrationForm;
import com.biathlon84.forum.model.entity.EmailMessage;
import com.biathlon84.forum.user.email.SenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@Controller
@Slf4j
public class RegistrationController {

    @Autowired private UserRegistrationService registrationService;

    @Autowired
    private SenderService senderService;

    @GetMapping(value = "/new-user")
    public String userRegistrationForm(Model model, String error) {
        log.info("User registration form");
        if (error != null) {
            model.addAttribute("error", error);
        }
        model.addAttribute("userRegistrationForm", new UserRegistrationForm());
        return Routes.NEW_USER_FORM;
    }

    @PostMapping(value = "/new-user")
    public String registerNewUser(@Valid UserRegistrationForm registrationForm, BindingResult bindingResult, Model model) {


        if (bindingResult.hasErrors()) {
            return Routes.NEW_USER_FORM;
        }

        String name = registrationForm.getUsername();
        EmailMessage emailMessage = registrationService.registerUser(registrationForm);
        try {
            String textHtml =  senderService.sendEmail(emailMessage,name,false);//31415
            System.out.println(textHtml);
        } catch (
                MessagingException e) {
            e.printStackTrace();
        } catch (
                UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        model.addAttribute("username", name);
        return Routes.REGISTRATION_CONFIRMATION;
    }
}
