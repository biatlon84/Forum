package com.biathlon84.forum.post;

import com.biathlon84.forum.model.entity.Post;
import com.biathlon84.forum.model.entity.Topic;
import com.biathlon84.forum.model.front.PostFront;
import com.biathlon84.forum.topic.TopicService;
import com.biathlon84.forum.model.entity.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.biathlon84.forum.user.UserServiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PostService {
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private TopicService topicService;
    
    @Autowired
    private UserServiceDAO userServiceDAO;
    
    public Post findOne(int id) {
        // todo fix optional
        return postRepository.findById(id).get();
    }
    
    public List<Post> findAll() {
        return postRepository.findAll();
    }
    
    public Set<Post> findRecent() {
        return postRepository.findTop5ByOrderByCreationDateDesc();
    }
    
    public Set<Post> findAllByOrderByCreationDateDesc() {
        return postRepository.findAllByOrderByCreationDateDesc();
    }
    
    public Set<Post> findByUser(User user) {
        return postRepository.findByUser(user);
    }
    
    public Set<Post> findByTopic(int idTopic) {
        return findByTopic(topicService.findOne(idTopic));
    }
    
    public Set<Post> findByTopic(Topic topic) {
        return postRepository.findByTopic(topic);
    }
    
    public void save(Post post) {
        postRepository.save(post);
    }
    
    public void delete(int id) {
        delete(findOne(id));
    }
    
    public void delete(Post post) {
        postRepository.delete(post);
    }

    public Post getNewPost(String content, String username, int idTopic){
        Post post = new Post();
        post.setTopic(topicService.findOne(idTopic));
        post.setUser(userServiceDAO.findByUsername(username));
        post.setContent(content);
        post.setCreationDate(LocalDateTime.now());
        post.setModificationDate(LocalDateTime.now());
        return post;
    }

    public Post getNewPost(Topic topic,User user,String content) {

        Post post = new Post();
        post.setTopic(topic);
        post.setUser(user);
        post.setContent(content);
        post.setCreationDate(LocalDateTime.now());
        post.setModificationDate(LocalDateTime.now());
        return post;
    }
    public List<PostFront> findRecentFront (){
        Set<Post> posts = postRepository.findTop5ByOrderByCreationDateDesc();
        return convertToFront(posts);
    }
    public List<PostFront> convertToFront(Set<Post> postsB){
        List<PostFront> posts=postsB.stream().map(a->(convertToFront(a))).collect(Collectors.toList());
        return posts;
    }

    public List<PostFront> convertToFront(List<Post> postsB){
        List<PostFront> posts=postsB.stream().map(a->(convertToFront(a))).collect(Collectors.toList());
        return posts;
    }

    public PostFront convertToFront(Post a){
        PostFront postF=PostFront.builder()
                .topic(topicService.getFront(a.getTopic()))
                .creationDate(dateC(a.getCreationDate()))
                .id(a.getId())
                .user(userServiceDAO.convertToUserFront(a.getUser()))
                .content(a.getContent())
                .build();
        return postF;
    }
    private static java.util.Date dateC(LocalDateTime localDateTime){
        ZoneId zoneId = ZoneId.systemDefault();
        Date date =Date.from(localDateTime.atZone(zoneId).toInstant());
        return date;
    }

    private static LocalDateTime dateC(java.util.Date localDateTime){
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime1 = localDateTime.toInstant()
                .atZone(zoneId)
                .toLocalDateTime();
        return localDateTime1;
    }

    public Post convertFromFront(PostFront post, User user) {
        return Post.builder()
                .content(post.getContent())
                .contentType(post.getContentType())
                .creationDate(dateC(post.getCreationDate()))
                .id(post.getId())
                .modificationDate(dateC(post.getModificationDate()))
                .user(user)
                .topic(topicService.convertFromFront(post.getTopic()))
                .build();
    }

    public void modifiyByAddPost(PostFront post) {
        //Topic topic = post.getTopic();
        //save();
       // return;
    }
}
