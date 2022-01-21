package com.biathlon84.forum.model.front;

import lombok.Data;

@Data
public class UserFront {
    private int id;

    private String email;

    private String secondaryEmail;

    private String emailToken;

    private String username;

    private String password;

    private boolean enabled;

    private boolean removed;

    private java.util.Date createdAt;

    private java.util.Date lastLoginTime;

    private String role;
    private UserProfileFront info;
}
