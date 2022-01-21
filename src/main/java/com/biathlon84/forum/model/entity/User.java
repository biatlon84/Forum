package com.biathlon84.forum.model.entity;

import java.time.LocalDateTime;
import javax.persistence.*;

import com.biathlon84.forum.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(unique = true)
    private String secondaryEmail;

    @Column(unique = true)
    private String emailToken;

    @Column(nullable = false, unique = true, length = 60)
    private String username;

    @Column(length = 60)
    private String password;

    private boolean enabled;

    private boolean removed;

    private LocalDateTime createdAt;

    private LocalDateTime lastLoginTime;

    private Role role;

    @OneToOne()
    @JoinColumn(name = "id")
    private UserProfile info;
}
