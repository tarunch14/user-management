package com.usermanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig { 
	
	private @Autowired UserDetailsService service;
	private @Autowired JwtFilter jwtFilter;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(customizer -> customizer.disable())
		    .authorizeHttpRequests(request -> request
		    		.requestMatchers("/api/auth/register", "/api/auth/login") //bypass authentication for singnup and login
		    		.permitAll()
		    		.anyRequest().authenticated())
		    .formLogin(Customizer.withDefaults()) //enabling login form for browser, but response will be login form if we use postman
		    .httpBasic(Customizer.withDefaults()) //enabling requests through postman with user name, password
		    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
		    .build();
	}
	
	
	@Bean //to configure authentication provider to check user details from db
	AuthenticationProvider authProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(bcryptPasswordencoder());
		provider.setUserDetailsService(service);
		return provider;
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	@Bean
	BCryptPasswordEncoder bcryptPasswordencoder() {
		return new BCryptPasswordEncoder(12);
	}
	
	
//	@Bean // this bean is for multiple users to authenticate but not recomended as we are hardcoding the users we need to authenticate users through database
//	UserDetailsService userDetailsService() {
//		UserDetails user1 = User.withDefaultPasswordEncoder()
//				.username("user1")
//				.password("password")
//				.roles("USER")
//				.build();
//		
//		UserDetails user2 = User.withDefaultPasswordEncoder()
//		.username("user2")
//		.password("password")
//		.roles("ADMIN")
//		.build();
//		return new InMemoryUserDetailsManager(user1, user2);
//	}

}
