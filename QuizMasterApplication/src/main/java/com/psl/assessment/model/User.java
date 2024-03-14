package com.psl.assessment.model;

import com.psl.assessment.dto.UserDto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String name;
	private String email;
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role;
	@Enumerated(EnumType.STRING)
	private Status status = Status.ACTIVE;

	
	
	
	


	

	public User(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}



	public User() {
		super();
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setStatus(String status) {
		this.status = Status.parseFromString(status);
	}
	
	public UserDto UserToUserDto()
	{
		return new UserDto(this.getId(),this.getName(),this.getEmail(),this.getPassword(),this.getRole(),this.getStatus());
	}
}
