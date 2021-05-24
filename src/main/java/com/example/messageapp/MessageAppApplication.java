package com.example.messageapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableAutoConfiguration
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
