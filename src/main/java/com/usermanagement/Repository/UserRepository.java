package com.usermanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usermanagement.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByName(String name);
	

}
