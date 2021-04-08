package com.example.messageapp.service;

import com.example.messageapp.dto.UserDto;
import com.example.messageapp.dto.UserFilterDto;

import java.util.List;

/**
 * skeleton of your class
 * a plan of a builiding
 * responsibilites / methods that are provided in a class
 * what is done but not how (Impl)
 */
public interface UserService {
    public UserDto signUp(UserDto userDto);
    public List<UserDto> listAll();
    public List<UserDto> findByFilter(UserFilterDto userFilterDto);
}
