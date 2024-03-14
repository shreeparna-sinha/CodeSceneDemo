
package com.psl.assessment.service;

import java.util.Arrays;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.psl.assessment.dao.UserRepository;
import com.psl.assessment.exception.UserAddException;
import com.psl.assessment.model.Role;
import com.psl.assessment.model.Topic;
import com.psl.assessment.model.User;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;


	@Override
	public User addUser(User user){
		
			if (user != null && user.getEmail() != null && user.getPassword()!=null) {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				return userRepository.save(user);
			} else {
				throw new UserAddException("User object cannot be null");
			}
		} 
	
	@Override
	public List<Topic> getListOfTopics() {
		// TODO Auto-generated method stub
		return Arrays.asList(Topic.values());
	}
	@Override
	public List<Role> getListOfRoles() {
		// TODO Auto-generated method stub
		return Arrays.asList(Role.values());
	}
	




	public Page<User> getAllUsers(int offset , int pageSize) {
		// TODO Auto-generated method stub
		Page<User> users = userRepository.findAll(PageRequest.of(offset, pageSize));
		return users;
	}
	
	public User getUserByEmailAndPassword(String email,String password)
	{
		 return userRepository.findByEmailAndPassword(email, password);

	}
	public Optional<User>getUserByEmail(String email){
		return userRepository.findByEmail(email);
	}






	
	
}
