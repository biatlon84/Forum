package com.biathlon84.forum.user.email;

import com.biathlon84.forum.email.EmailMessageServiceDAO;
import com.biathlon84.forum.model.entity.EmailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailScheduler {

    @Autowired
    private EmailMessageRepository emailMessageRepository;

    @Autowired
    private EmailMessageServiceDAO emailMessageService;

    public EmailMessage scheduleConfirmationMessage(String email) {
        String randomString = Long.toHexString((new Random()).nextLong());
        EmailMessage confirmationMessage = EmailMessage.builder()
                .recipient(email)
                .subject("subject")
                .content(randomString)
                .type(EmailMessage.EmailMessageType.CONFIRMATION)
                .build();
        emailMessageService.scheduleMessage(confirmationMessage);
        return confirmationMessage;
    }
}
