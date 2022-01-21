package com.biathlon84.forum.user;

import com.biathlon84.forum.email.EmailMessageServiceDAO;
import com.biathlon84.forum.model.dto.UserRegistrationForm;
import com.biathlon84.forum.model.entity.EmailMessage;
import com.biathlon84.forum.model.entity.User;
import com.biathlon84.forum.user.email.EmailScheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@Slf4j
public class UserRegistrationService {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailMessageServiceDAO emailMessageService;
    @Autowired
    EmailScheduler emailScheduler;

    public EmailMessage registerUser(UserRegistrationForm form) {
        log.info("Register new user {}, {},{}", form.getEmail(), form.getUsername(),form.getPassword());
        EmailMessage message = emailScheduler.scheduleConfirmationMessage(form.getEmail());
        String confirmationCode = message.getContent();

        User newUser = buildUser(form, confirmationCode);
        userRepository.save(newUser);
        return message;
    }

    private User buildUser(UserRegistrationForm form, String confirmationCode) {
        return User.builder()
                .email(form.getEmail())
                .emailToken(confirmationCode)
                .username(form.getUsername())
                .createdAt(LocalDateTime.now())
                .enabled(false) //31415926
                .password(passwordEncoder.encode(form.getPassword()))
                .build();
    }
}
