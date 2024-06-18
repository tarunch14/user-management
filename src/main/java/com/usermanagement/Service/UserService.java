package com.usermanagement.Service;

import com.usermanagement.Payload.UserDto;

public interface UserService {
	
	UserDto createUser(UserDto userDto);
	
	UserDto getUser(long id);
	
	UserDto updateUser(long id, UserDto userDto);
	
	String deleteUser(long id);

}
