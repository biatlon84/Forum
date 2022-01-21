package com.biathlon84.forum.model.front;
import com.biathlon84.forum.model.entity.Post;
import com.biathlon84.forum.topic.TopicFront;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
    public class PostFront {
    private int id;
    private TopicFront topic;
    private UserFront user;
    private String content;
    private Post.ContentType contentType;
    private java.util.Date creationDate;
    private java.util.Date modificationDate;
    public enum ContentType {
        TEXT,
        MARKDOWN,
        HTML
    }
}
