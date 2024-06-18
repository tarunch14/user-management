package com.usermanagement.Payload;


import com.usermanagement.Validation.MobileNumber;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
	
	private String name;
	
	@Email(message = "{email.invalid.message}")
	@NotBlank(message = "{email.empty.message}")
	private String email;
	
	@MobileNumber
	private String mobileNo;

}
