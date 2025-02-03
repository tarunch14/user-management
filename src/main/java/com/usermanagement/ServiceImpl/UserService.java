package com.usermanagement.ServiceImpl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.usermanagement.Entity.Users;
import com.usermanagement.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final JwtService jwtService;
	private final UserRepository uerRepo;
	private final BCryptPasswordEncoder passwordEncoder;
	private final AuthenticationManager authManager;
	
	public Users register(Users user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return uerRepo.save(user);
	}

	public String verify(Users user) {
		Authentication auth = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
		if (auth.isAuthenticated())
			return jwtService.generateToken(user.getUserName());
		return "Invalid Credentials";
	}

}
