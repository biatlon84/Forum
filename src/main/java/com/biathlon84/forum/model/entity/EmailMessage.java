package com.biathlon84.forum.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "scheduled_emails")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String recipient;
    private String subject;
    private String content;
    private boolean sent;
    private LocalDateTime scheduledSentDate;
    private LocalDateTime sentDate;
    private EmailMessageType type;

    public enum EmailMessageType {
        CONFIRMATION,
        PASSWORD_RESET,
    }
}
