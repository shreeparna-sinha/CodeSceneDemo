package com.psl.assessment.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.psl.assessment.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
	
	@Query(value = "select * from Users u where u.email =:email and u.password =:password", nativeQuery = true)
	User getUserByEmail(@Param("email") String email,@Param("password") String password);
	User findByEmailAndPassword(String userName, String passWord);
	
}
