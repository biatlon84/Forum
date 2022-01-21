package com.biathlon84.forum.topic;

import com.biathlon84.forum.model.entity.Topic;
import com.biathlon84.forum.section.SectionService;
import com.biathlon84.forum.model.entity.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biathlon84.forum.model.entity.Section;


@Service
public class TopicService {
    
    @Autowired
    private TopicRepository topicRepository;
    
    @Autowired
    private SectionService sectionService;
    
    public List<Topic> findAll() {
        return topicRepository.findAll();
    }
    
    public Topic findOne(int id) {
        System.out.println("find topic with id in TopicService "+id);
        return topicRepository.findById(id).get();
    }
    
    public Set<Topic> findRecent() {
        return topicRepository.findTop5ByOrderByCreationDateDesc();
    }
    public Topic genDefault(User user,Section section) {
        return new Topic(0,user,section,"default topic title!!!","content",43,new Date(System.currentTimeMillis()),new Date(System.currentTimeMillis()),false);
    }

    public Set<Topic> findAllByOrderByCreationDateDesc() {
        return topicRepository.findAllByOrderByCreationDateDesc();
    }

    public Set<Topic> findBySection(Section section) {
        return topicRepository.findBySection(section);
    }

    public Set<Topic> findBySection(String sectionName) {
        return findBySection(sectionService.findByName(sectionName));
    }

    public Topic save(Topic topic) {
        return topicRepository.save(topic);
    }
    
    public Set<Topic> findBySection(int id) {
        return findBySection(sectionService.findOne(id));
    }

    public Set<Topic> findByUser(User user) {
        return topicRepository.findByUser(user);
    }

    public void delete(int id) {
        delete(findOne(id));
    }
    
    public void delete(Topic topic) {
        topicRepository.delete(topic);
    }
    public TopicFront getFront(Topic a){
        return TopicFront.builder()
                .section(a.getSection())
                .creationDateFront(a.getCreationDate())
                .title(a.getTitle())
                .userName(a.getUser().getUsername())
                .userFooter(a.getUser().getInfo().getFooter())
                .views(a.getViews())
                .content(a.getContent())
                .id(a.getId())
                .build();
    }
    public List<TopicFront> getFrontTopics(List<Topic> topicsSQL){
        return topicsSQL.stream().map(a->getFront(a)).collect(Collectors.toList());
    }
    public List<TopicFront> getFrontTopics(Set<Topic> topicsSQL){
        return topicsSQL.stream().map(a->getFront(a)).collect(Collectors.toList());
    }

    public Topic convertFromFront(TopicFront topicFront) {
        Topic topic = findOne(topicFront.getId());
        topic.setLastUpdateDate(dateC(topicFront.getLastUpdateDate()));
        topic.setViews(topicFront.getViews());
        topic.setContent(topicFront.getContent());
        return topic;
    }
    private static LocalDateTime dateC(java.util.Date localDateTime){
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime1 = localDateTime.toInstant()
                .atZone(zoneId)
                .toLocalDateTime();
        return localDateTime1;
    }
    private static java.util.Date dateC(LocalDateTime localDateTime){
        ZoneId zoneId = ZoneId.systemDefault();
        Date date =Date.from(localDateTime.atZone(zoneId).toInstant());
        return date;
    }
}
