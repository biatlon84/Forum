package com.biathlon84.forum.security;

public class AccessRules {

    protected static final String[] FOR_EVERYONE = {
            "/hello",
            "/login",
            "/error",
            "/users/**",
            "/css/**",
            "/js/**"
    };

    protected static final String[] FOR_AUTHORIZED_USERS = {
            "/user/**",
            "/topic/new/**",
            "/topic/delete/**",
            "/section/delete/**",
            "/sections/**",
            "/section/new/**",
            "/post/**",
            "/myprofile/**"
    };

    protected static final String[] FOR_ADMINS = {"/admin/**",
            "/users/**",
            "/section/new"
    };

    protected static final String[] ADMINS_ROLES = {
            "HEAD_ADMIN",
            "ADMIN"
    };

}
