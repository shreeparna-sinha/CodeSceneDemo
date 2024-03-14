package com.psl.assessment.model;

public enum Role {
	ADMIN("Admin"), 
	TRAINER("Trainer"), 
	STUDENT("Student");

	private String role;

	private Role(String role) {
		this.role = role;
	}

	public static Role parseFromString(String role) {
		return Role.valueOf(role.toUpperCase());
	}

	@Override
	public String toString() {
		return this.role;
	}
}

//@Data
//@NoArgsConstructor
//@Entity
//@Table(name = "Roles")
//public class Role {
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Long id;
//	private String roleName;
//}
