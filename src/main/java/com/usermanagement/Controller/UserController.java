package com.usermanagement.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.Entity.Users;
import com.usermanagement.ServiceImpl.UserService;

@RestController
@RequestMapping(value = "/api/auth")
public class UserController {
	
	private @Autowired UserService userService;
	
	@PostMapping(value = "/register" )
	@ResponseStatus(HttpStatus.CREATED)
	public Users register(@RequestBody Users user) {
		return userService.register(user);
	}
	
	@PostMapping(value = "/login" )
	@ResponseStatus(HttpStatus.OK)
	public String login(@RequestBody Users user) {
		return userService.verify(user);
	}

}
