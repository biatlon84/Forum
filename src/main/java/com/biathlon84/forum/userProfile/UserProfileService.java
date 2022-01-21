package com.biathlon84.forum.userProfile;

import com.biathlon84.forum.model.entity.User;
import com.biathlon84.forum.model.entity.UserProfile;
import com.biathlon84.forum.model.front.UserFront;
import com.biathlon84.forum.model.front.UserProfileFront;
import com.biathlon84.forum.post.PostService;
import com.biathlon84.forum.topic.TopicService;
import com.biathlon84.forum.user.UserServiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;


//import java.time.LocalDateTime;
//import java.util.Date;
//import java.sql.Timestamp;


@Service
public class UserProfileService {
    
    @Autowired
    private UserServiceDAO userService;
    
    @Autowired
    private PostService postService;
    
    @Autowired
    private TopicService topicService;

    @Autowired
    private UserProfileRepository userProfileRepository;
    
    public UserProfile findOne(int userId) {
        UserProfile userProfile = new UserProfile();
        User user = userService.findOne(userId);
        userProfile.setUser(user);
//        userProfile.setPosts(postService.findByUser(user));
//        userProfile.setTopics(topicService.findByUser(user));
        return userProfile;
    }
    
    public UserProfile findOne(String username) {
        return findOne(userService.findByUsername(username).getId());
    }

    public UserProfile findByEmail(String email) {
        return userProfileRepository.getById(userService.findByEmail(email).getId());
    }
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
    public UserProfile getDefProf(){
        Date date = Date.valueOf("1961-04-12");
        return UserProfile.builder()
                .sex("I do not care")
                .aboutMe("Are you sure that you want to know it?")
                .city("Perm")
                .name("Real live name")
                .footer("3.5 digits will bee enough for everybody!")
                .birthday(date)
                .hasPicture(false)
                .build();
    }
    public void save(UserProfile userProfile){
        userProfileRepository.save(userProfile);
    }
    public UserProfileFront getProfFront(UserProfile userProfile) {
        return getUserProfileFrontWUser(userProfile);
    }
    private UserFront convert(User user){
        return userService.convertToUserFront(user);
    }
    public UserProfileFront getUserProfileFront(UserProfile userProfile){
        String pictureName = userProfile.isHasPicture()?userProfile.getUser().getId()+".jpg":"default.png";
        UserProfileFront userProfileFront = UserProfileFront.builder()
                .name(userProfile.getName())
                .aboutMe(userProfile.getAboutMe())
                .sex(userProfile.getSex())
                .birthday(userProfile.getBirthday())
                .footer(userProfile.getFooter())
                .id(userProfile.getId())
                .city(userProfile.getCity())
                .user(convert(userProfile.getUser()))
                .picture(pictureName)
                .build();
        return userProfileFront;
    }
    public UserProfileFront getUserProfileFrontWUser(UserProfile userProfile){
        String pictureName = userProfile.isHasPicture()?userProfile.getUser().getId()+".jpg":"default.png";
        UserProfileFront userProfileFront = UserProfileFront.builder()
                .name(userProfile.getName())
                .aboutMe(userProfile.getAboutMe())
                .sex(userProfile.getSex())
                .birthday(userProfile.getBirthday())
                .footer(userProfile.getFooter())
                .id(userProfile.getId())
                .city(userProfile.getCity())
                .picture(pictureName)
                .build();
        return userProfileFront;
    }
    public UserProfile convertFoomFront(UserProfileFront info) {
        UserProfile userProfile = findOne(info.getUser().getId());
        userProfile.setAboutMe(info.getAboutMe());
        userProfile.setSex(info.getSex());
        userProfile.setFooter(info.getFooter());
        userProfile.setCity(info.getCity());
        //...
        return userProfile;
    }
}
