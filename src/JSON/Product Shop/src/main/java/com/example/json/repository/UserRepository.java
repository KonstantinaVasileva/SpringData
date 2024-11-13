package com.example.json.repository;

import com.example.json.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Set<User> findAllBySoldBuyerIsNotNull();
    Set<User> findAllBySoldIsNotNull();
}
