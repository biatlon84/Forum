package com.biathlon84.forum.model.front;

import lombok.Builder;
import lombok.Data;
import java.sql.Date;

@Data
@Builder
public class UserProfileFront {

    private int id;

    private UserFront user;

    private String name;

    private String sex;

    private Date birthday;

    private String picture;

    private String city;

    private String aboutMe;

    private String footer;

}
