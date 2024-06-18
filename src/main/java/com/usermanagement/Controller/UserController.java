package com.usermanagement.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.Payload.UserDto;
import com.usermanagement.Service.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping(value = "/submit")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		return new ResponseEntity<UserDto>(userService.createUser(userDto), HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/view/{user_id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable(value = "user_id") long id) {
		return new ResponseEntity<UserDto>(userService.getUser(id), HttpStatus.OK);
	}
	
	@PutMapping(value = "/update/{user_id}")
	public ResponseEntity<UserDto> updateUser(@PathVariable(value = "user_id") long id, @Valid @RequestBody UserDto userDto){
		return new ResponseEntity<UserDto>(userService.updateUser(id, userDto),HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/delete/{user_id}")
	public ResponseEntity<String> deleteUser(@PathVariable(value = "user_id") long id) {
		return new ResponseEntity<String>(userService.deleteUser(id),HttpStatus.NO_CONTENT);
	}
	
}
