package com.example.BE_PBL6_FastOrderSystem.repository;

import com.example.BE_PBL6_FastOrderSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhoneNumber(String phoneNumber);

    User findByPhoneNumber(String phoneNumber);

    boolean existsByEmail(String email);

    List<User> findAllByRole_Name(String roleName);

    User findByEmail(String email);

    Object findByFacebookId(String facebookId);

    @Query("select count (o) from User  o where function('MONTH',o.createdAt) = ?1 AND function('YEAR', o.createdAt) = ?2")
    Long getAllPeopleRegisterByMonth(int month,int year);
}
