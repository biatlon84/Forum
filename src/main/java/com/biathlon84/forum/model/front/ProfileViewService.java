package com.biathlon84.forum.model.front;
import com.biathlon84.forum.model.entity.Post;
import com.biathlon84.forum.model.entity.Section;
import com.biathlon84.forum.model.entity.Topic;
import com.biathlon84.forum.post.PostService;
import com.biathlon84.forum.section.SectionService;
import com.biathlon84.forum.topic.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Service
public class ProfileViewService {

    @Autowired
    private TopicService topicService;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private PostService postService;
    private UserActivity userActivity = new UserActivity();

    public UserActivity getFrontUserActivity(Set<Topic> topics, List<Post> posts, List<Section> sections){
        userActivity.posts=postService.convertToFront(posts);
        userActivity.sections=sectionService.convertToFront(sections);
        userActivity.topics=topicService.getFrontTopics(topics);
        return userActivity;
    }

    public UserActivity getFrontUserActivity(List<Topic> topics, List<Post> posts, List<Section> sections){
        userActivity.posts=postService.convertToFront(posts);
        userActivity.sections=sectionService.convertToFront(sections);
        userActivity.topics=topicService.getFrontTopics(topics);
        return userActivity;
    }
    public UserActivity getFrontUserActivity(Set<Topic> topics, Set<Post> posts, List<Section> sections){
        userActivity.posts=postService.convertToFront(posts);
        userActivity.sections=sectionService.convertToFront(sections);
        userActivity.topics=topicService.getFrontTopics(topics);
        return userActivity;
    }
}
