package com.example.messageapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import com.example.messageapp.entity.User;

public interface UserRepo extends JpaRepository<User,  Integer>, QuerydslPredicateExecutor<User> {

}
