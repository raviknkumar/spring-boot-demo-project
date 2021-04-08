package com.example.messageapp;

import com.example.messageapp.controller.UserController;
import com.example.messageapp.service.UserService;
import com.example.messageapp.service.impl.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MessageAppApplication {

	/*public void test(){

		UserController userController = new UserController();


		UserServiceImpl userService =  new UserServiceImpl();
		// setter way
		// userController.setUserService(userService);
	}*/

	public static void main(String[] args) {
		SpringApplication.run(MessageAppApplication.class, args);
	}

}
