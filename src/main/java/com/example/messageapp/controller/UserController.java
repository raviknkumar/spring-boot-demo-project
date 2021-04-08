package com.example.messageapp.controller;

import com.example.messageapp.dto.ApiResponse;
import com.example.messageapp.dto.UserDto;
import com.example.messageapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {


    /* Constructor way */
    /*UserController(UserService userService){
        this.userService = userService;
    }*/

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    // Dependency injection
    // i need a class of userService
    // i am depending on some one else to handle the logic / responsibility
    // this is my dependency
    // i want that to be injected in my code
    // u own some responsibility and not in entire
    // to handle other responsibilites., u use depencencies
    // Constructor way, Setter way, Field way

    // @Inject, @Resource
    @Autowired private UserService userService;

    //@RequestMapping(method = RequestMethod.POST)
    @PostMapping("/signup")
    /**
     * signup handled
     * userDto : @RequestBody: to accept the user details for signup
     *
     *
     *
     */
    public ApiResponse<UserDto> signup(@RequestBody UserDto userDto){
        log.info("Signup");

        userService.signUp(userDto);
        return new ApiResponse<>(userDto, "User signed up successfully!");
    }

}
