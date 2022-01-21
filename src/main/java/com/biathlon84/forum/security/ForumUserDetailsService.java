package com.biathlon84.forum.security;

import com.biathlon84.forum.model.entity.User;
import com.biathlon84.forum.user.UserServiceDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("UserDetailServiceImpl")
@Slf4j
public class ForumUserDetailsService implements UserDetailsService {

    @Autowired
    private UserServiceDAO userService;

    public ForumUserDetailsService(UserServiceDAO userService) {
        this.userService = userService;
    }
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findByEmail(s);
        return formUser(user);
    }

    public static UserDetails formUser(User user){
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                user.isEnabled(),
                user.isEnabled(),
                user.isEnabled(),
                user.getRole().getGrAu());
    }
}