package com.usermanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usermanagement.Entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

	Users findByUserName(String userName); 
}
