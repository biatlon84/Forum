package com.biathlon84.forum.user.activation;

import com.biathlon84.forum.model.entity.User;
import com.biathlon84.forum.model.entity.UserProfile;
import com.biathlon84.forum.user.Role;
import com.biathlon84.forum.userProfile.UserProfileService;
import com.biathlon84.forum.user.UserServiceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ActivationService {
    @Autowired
    private UserServiceDAO userService;
    @Autowired
    private UserProfileService userProfileService;
    public void activate(String username, String activationCodeId) {
        User user = userService.findByUsername(username);
        user.setEnabled(true);
        user.setRole(Role.USER);
        UserProfile userProfile = userProfileService.getDefProf();
        userProfile.setUser(user);
        userProfileService.save(userProfile);
        userService.save(user);
    }

}
