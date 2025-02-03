package com.usermanagement.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.usermanagement.Entity.Employee;
import com.usermanagement.Exception.ResourceNotFound;
import com.usermanagement.Payload.EmployeeDto;
import com.usermanagement.Repository.EmployeeRepository;
import com.usermanagement.Service.EmployeeService;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImplementation implements EmployeeService{
	
	private final EmployeeRepository userRepo;
	private final ModelMapper modelMapper;

	@Override
	public EmployeeDto createUser(EmployeeDto userDto) {
		Employee existingUser = userRepo.findByName(userDto.getName());	
		if(isDuplicateUser(existingUser, userDto)) {
			 throw new IllegalArgumentException("User already exists with given details");
		}
		Employee userEntity = modelMapper.map(userDto, Employee.class);
		userRepo.save(userEntity);
		return modelMapper.map(userEntity, EmployeeDto.class);
	}

	@Override
	public EmployeeDto getUser(long id) {
		Employee userEntity = userRepo.findById(id).orElseThrow(()->new ResourceNotFound(String.format("User with id %d not found", id)));
		return modelMapper.map(userEntity, EmployeeDto.class);
	}
	
	@Override
	public List<EmployeeDto> getAllUsers() {
		List<Employee> users = userRepo.findAll();
		return users.stream().map(user -> modelMapper.map(user, EmployeeDto.class)).collect(Collectors.toList());
	}

	@Override
	public EmployeeDto updateUser(long id, EmployeeDto userDto) {
		Employee existingUser = userRepo.findById(id).orElseThrow(() -> new ResourceNotFound(String.format("User with id %d not found", id)));
		if (! (StringUtils.isEmpty(userDto.getName()) && StringUtils.isEmpty(userDto.getEmail())
				&& StringUtils.isEmpty(userDto.getMobileNo()))) {
			existingUser.setName(userDto.getName());
			existingUser.setEmail(userDto.getEmail());
			existingUser.setMobileNo(userDto.getMobileNo());
		}
		userRepo.save(existingUser);
		return modelMapper.map(existingUser, EmployeeDto.class);
	}

	@Override
	public String deleteUser(long id) {
		userRepo.findById(id).orElseThrow(() -> new ResourceNotFound(String.format("User with id %d not found", id)));
		userRepo.deleteById(id);
		return "User deleted successfully";
	}

	public boolean isDuplicateUser(Employee existingUser, EmployeeDto dtoUser) {
		if (ObjectUtils.isEmpty(existingUser))
			return false;
		return existingUser.getEmail().equals(dtoUser.getEmail())
				&& existingUser.getMobileNo().equals(dtoUser.getMobileNo());
	}
	
}
