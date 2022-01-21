package com.biathlon84.forum.email;
import com.biathlon84.forum.model.entity.EmailMessage;
import com.biathlon84.forum.user.email.EmailMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Slf4j
@Service
public class EmailMessageServiceDAO {

    @Autowired private EmailMessageRepository repository;

    public void scheduleMessage(EmailMessage message) {
        message.setScheduledSentDate(LocalDateTime.now());
        message.setSent(false);
        repository.save(message);
    }
}
