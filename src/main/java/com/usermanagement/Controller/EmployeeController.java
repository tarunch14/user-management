package com.usermanagement.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.Payload.EmployeeDto;
import com.usermanagement.Service.EmployeeService;

import jakarta.validation.Valid;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping(value = "/emp")
public class EmployeeController {

	@Autowired
	private EmployeeService userService;
	
	@PostMapping(value = "/add")
	public ResponseEntity<EmployeeDto> createUser(@Valid @RequestBody EmployeeDto userDto) {
		return new ResponseEntity<EmployeeDto>(userService.createUser(userDto), HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/view/{user_id}")
	public ResponseEntity<EmployeeDto> getUserById(@PathVariable(value = "user_id") long id) {
		return new ResponseEntity<EmployeeDto>(userService.getUser(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/view-all")
	public ResponseEntity<List<EmployeeDto>> getAllUsers() {
		return new ResponseEntity<List<EmployeeDto>>(userService.getAllUsers(), HttpStatus.OK);
	}
	
	@PutMapping(value = "/update/{user_id}")
	public ResponseEntity<EmployeeDto> updateUser(@PathVariable(value = "user_id") long id, @Valid @RequestBody EmployeeDto userDto){
		return new ResponseEntity<EmployeeDto>(userService.updateUser(id, userDto),HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/delete/{user_id}")
	public ResponseEntity<String> deleteUser(@PathVariable(value = "user_id") long id) {
		return new ResponseEntity<String>(userService.deleteUser(id),HttpStatus.NO_CONTENT);
	}
	
}
