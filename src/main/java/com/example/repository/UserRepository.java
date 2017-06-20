package com.example.repository;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Bender on 5/14/2017.
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);
    List<User> findByApprovedByAdmin(boolean approvedByAdmin);

}
