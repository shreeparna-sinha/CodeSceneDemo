package com.psl.assessment.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psl.assessment.dto.AuthRequest;
import com.psl.assessment.model.User;
import com.psl.assessment.service.JwtService;
import com.psl.assessment.service.UserService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
* Controller class for user authentication.
* 
*/
@RestController
@Tag(name="Authentication Api")
@RequestMapping("/quizmaster")
public class LogInController {
	
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	/**
	* Authenticates a user using email and password.
	*
	* @param authRequest The authentication request containing email and password.
	* @return ResponseEntity containing user authentication details and token.
	* @throws UsernameNotFoundException
	*/
	@Operation(summary = "Authenticate User",description = "EndPoint to Authenticate user by Email and Password")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User Authenticated"),
	        @ApiResponse(responseCode = "401", description = "Invalid Credentials")})
	    
	@PostMapping("/authenticate")
	public ResponseEntity<Object> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		 
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
    		Map<String,Object> response = new HashMap<>();
    		Optional<User> u1 = userService.getUserByEmail(authRequest.getEmail());
    
    			response.put("message", "User is authenticated");
    			response.put("id",u1.get().getId());
    			response.put("role",u1.get().getRole());
    			response.put("token",jwtService.generateToken(authRequest.getEmail()));
    		  return ResponseEntity.status(200).body(response);
        }

    			
         else {
            throw new UsernameNotFoundException("invalid user request !");
        }
		
	}
}



