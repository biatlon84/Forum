package com.biathlon84.forum.topic;

import com.biathlon84.forum.model.entity.Section;
import com.biathlon84.forum.model.entity.Topic;
import com.biathlon84.forum.model.entity.User;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;


public interface TopicRepository extends JpaRepository<Topic, Integer> {
    
    Set<Topic> findBySection(Section section);
    
    Set<Topic> findByUser(User user);
    
    Set<Topic> findAllByOrderByCreationDateDesc();
    
    Set<Topic> findTop5ByOrderByCreationDateDesc();
    
    
}
