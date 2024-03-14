package com.psl.assessment.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.psl.assessment.dao.UserRepository;
import com.psl.assessment.model.User;


/**
* Custom implementation of Spring Security's UserDetailsService interface.
* This class is responsible for loading user details from the UserRepository
* and creating a UserDetails object for authentication and authorization purposes.
*/
@Component
public class UserInfoUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository repository;
	
	
	/**
	* Load user details by username (email in this case) from the UserRepository.
	*
	* @param email The email of the user for whom to load details.
	* @return UserDetails object containing user details.
	* @throws UsernameNotFoundException If the user is not found.
	*/
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = repository.findByEmail(email);
        return user.map(UserInfoUserDetails ::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " ));
	}

}
