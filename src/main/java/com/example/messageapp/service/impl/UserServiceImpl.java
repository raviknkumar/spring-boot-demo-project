package com.example.messageapp.service.impl;

import com.example.messageapp.dto.UserDto;
import com.example.messageapp.dto.UserFilterDto;
import com.example.messageapp.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    List<UserDto> userDtoList = new ArrayList<>();
    private Integer globalId = 0;

    @Override
    public UserDto signUp(UserDto userDto) {
        userDtoList.add(userDto);
        globalId++;
        userDto.setId(globalId);
        return userDto;
    }

    @Override
    public List<UserDto> listAll() {
        return userDtoList;
    }

    @Override
    public List<UserDto> findByFilter(UserFilterDto userFilterDto) {
        return null;
    }
}
