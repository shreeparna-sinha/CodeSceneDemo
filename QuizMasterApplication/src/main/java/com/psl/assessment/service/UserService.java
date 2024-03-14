
package com.psl.assessment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;


import com.psl.assessment.model.Role;
import com.psl.assessment.model.Topic;
import com.psl.assessment.model.User;

public interface UserService {


	public User addUser(User user);

	public List<Role> getListOfRoles();
	
	public Page<User> getAllUsers(int offset , int pageSize);
	

	public Optional<User>getUserByEmail(String email);
	public User getUserByEmailAndPassword(String email,String password);

	public List<Topic> getListOfTopics();
	

}

