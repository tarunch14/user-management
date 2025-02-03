package com.usermanagement.Service;

import java.util.List;

import com.usermanagement.Payload.EmployeeDto;

public interface EmployeeService {
	
	EmployeeDto createUser(EmployeeDto userDto);
	
	EmployeeDto getUser(long id);
	
	List<EmployeeDto> getAllUsers();
	
	EmployeeDto updateUser(long id, EmployeeDto userDto);
	
	String deleteUser(long id);

}
