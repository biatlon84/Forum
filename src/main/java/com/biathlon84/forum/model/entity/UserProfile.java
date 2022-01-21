package com.biathlon84.forum.model.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "user_profiles")
@Data
@Builder
@AllArgsConstructor
@ToString(exclude = "user")
@EqualsAndHashCode(exclude = "user")
public class UserProfile {
    public UserProfile() {
    }

    @Id
    @Column(name = "id")
    private int id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 20)
    private String name;

    @Column(length = 20)
    private String sex;

    private Date birthday;

    private boolean hasPicture;

    @Column(length = 20)
    private String city;

    @Column(length = 150)
    private String aboutMe;

    @Column(length = 50)
    private String footer;
}
