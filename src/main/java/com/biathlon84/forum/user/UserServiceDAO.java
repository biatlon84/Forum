package com.biathlon84.forum.user;

import com.biathlon84.forum.model.entity.User;
import com.biathlon84.forum.model.front.UserFront;
import com.biathlon84.forum.user.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import com.biathlon84.forum.userProfile.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceDAO {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileService userProfileService;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findOne(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return user;
    }

    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user;
    }

    public User findByEmailOrExit(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public void delete(String email) {
        delete(userRepository.findByEmail(email));
    }

    public void deleteMark(String email){
        User user = userRepository.findByEmail(email);
        user.setRemoved(true);
        userRepository.save(user);
    }

    public UserFront convertToUserFront(User user){
        UserFront userFront = new UserFront();
        userFront = new UserFront();
        userFront.setUsername(user.getUsername());
        userFront.setEmail(user.getEmail());
        //userFront.setLastLoginTime(dateC(user.getLastLoginTime()));
        userFront.setCreatedAt(dateC(user.getCreatedAt()));
        userFront.setId(user.getId());
        userFront.setRole(user.getRole().toString());
        userFront.setInfo(userProfileService.getProfFront(user.getInfo()));
        return userFront;
    }

    public UserFront convertToUserFrontWpro(User user){
        UserFront userFront = new UserFront();
        userFront = new UserFront();
        userFront.setUsername(user.getUsername());
        userFront.setEmail(user.getEmail());
        //userFront.setLastLoginTime(dateC(user.getLastLoginTime()));
        userFront.setCreatedAt(dateC(user.getCreatedAt()));
        userFront.setId(user.getId());
        userFront.setRole(user.getRole().toString());
        return userFront;
    }

    private java.util.Date dateC(LocalDateTime localDateTime){
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
    public User convertFromUserFront(UserFront userFront) {
        User user = findByEmail(userFront.getEmail());
        user.setLastLoginTime(dateC(userFront.getLastLoginTime()));
        user.setInfo(userProfileService.convertFoomFront(userFront.getInfo()));
        return user;
    }
}
