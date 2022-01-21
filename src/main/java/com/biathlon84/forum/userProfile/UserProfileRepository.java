package com.biathlon84.forum.userProfile;

import com.biathlon84.forum.model.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile,Integer> {


}
