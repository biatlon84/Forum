package com.biathlon84.forum.user.email;

import com.biathlon84.forum.model.entity.EmailMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailMessageRepository extends JpaRepository<EmailMessage, Integer> {
    EmailMessage findById(int id);
}
