package com.biathlon84.forum.post;

import com.biathlon84.forum.model.entity.Post;
import com.biathlon84.forum.model.entity.Topic;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biathlon84.forum.model.entity.User;


public interface PostRepository extends JpaRepository<Post, Integer> {
    
    Set<Post> findByUser(User user);
    
    Set<Post> findByTopic(Topic topic);
    
    Set<Post> findAllByOrderByCreationDateDesc();
    
    Set<Post> findTop5ByOrderByCreationDateDesc();
}
