package com.biathlon84.forum.model.front;
import com.biathlon84.forum.topic.TopicFront;
import java.util.List;
public class UserActivity {
    List<TopicFront> topics;
    List<SectionFront> sections;
    List<PostFront> posts;
    public UserActivity() {
    }

    public UserActivity(List<TopicFront> topics, List<SectionFront> sections, List<PostFront> posts) {
        this.topics = topics;
        this.sections = sections;
        this.posts = posts;
    }

    public List<TopicFront> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicFront> topics) {
        this.topics = topics;
    }

    public List<SectionFront> getSections() {
        return sections;
    }

    public void setSections(List<SectionFront> sections) {
        this.sections = sections;
    }

    public List<PostFront> getPosts() {
        return posts;
    }

    public void setPosts(List<PostFront> posts) {
        this.posts = posts;
    }
}
