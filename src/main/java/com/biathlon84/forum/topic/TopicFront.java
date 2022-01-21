package com.biathlon84.forum.topic;

import com.biathlon84.forum.model.entity.Section;;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TopicFront {

    private int id;

    private String userName;

    private String userFooter;

    private Section section;

    private String SectionId;
    private String title;

    private String content;

    private int views;

    private LocalDateTime creationDate;

    private LocalDateTime lastUpdateDate;
    private boolean closed;

    protected void onCreate() {
        this.creationDate = LocalDateTime.now();
    }

    protected void onUpdate() {
        this.lastUpdateDate = LocalDateTime.now();
    }

    public java.util.Date creationDateFront;

}
