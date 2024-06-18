package com.usermanagement.ServiceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.usermanagement.Entity.User;
import com.usermanagement.Exception.ResourceNotFound;
import com.usermanagement.Payload.UserDto;
import com.usermanagement.Repository.UserRepository;
import com.usermanagement.Service.UserService;

import io.micrometer.common.util.StringUtils;

@Service
public class UserServiceImplementation implements UserService{
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {
		User existingUser = userRepo.findByName(userDto.getName());	
		if(isDuplicateUser(existingUser, userDto)) {
			 throw new IllegalArgumentException("User already exists with given details");
		}
		User userEntity = modelMapper.map(userDto, User.class);
		userRepo.save(userEntity);
		return modelMapper.map(userEntity, UserDto.class);
	}

	@Override
	public UserDto getUser(long id) {
		User userEntity = userRepo.findById(id).orElseThrow(()->new ResourceNotFound(String.format("User with id %d not found", id)));
		return modelMapper.map(userEntity, UserDto.class);
	}

	@Override
	public UserDto updateUser(long id, UserDto userDto) {
		User existingUser = userRepo.findById(id).orElseThrow(() -> new ResourceNotFound(String.format("User with id %d not found", id)));
		if (! (StringUtils.isEmpty(userDto.getName()) && StringUtils.isEmpty(userDto.getEmail())
				&& StringUtils.isEmpty(userDto.getMobileNo()))) {
			existingUser.setName(userDto.getName());
			existingUser.setEmail(userDto.getEmail());
			existingUser.setMobileNo(userDto.getMobileNo());
		}
		userRepo.save(existingUser);
		return modelMapper.map(existingUser, UserDto.class);
	}

	@Override
	public String deleteUser(long id) {
		userRepo.findById(id).orElseThrow(() -> new ResourceNotFound(String.format("User with id %d not found", id)));
		userRepo.deleteById(id);
		return "User deleted successfully";
	}

	public boolean isDuplicateUser(User existingUser, UserDto dtoUser) {
		if (ObjectUtils.isEmpty(existingUser))
			return false;
		return existingUser.getEmail().equals(dtoUser.getEmail())
				&& existingUser.getMobileNo().equals(dtoUser.getMobileNo());
	}
	
}
