package com.psl.assessment.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.psl.assessment.dto.QuizDto;
import com.psl.assessment.dto.StatusDto;
import com.psl.assessment.dto.UserDto;
import com.psl.assessment.model.Quiz;
import com.psl.assessment.model.Role;
import com.psl.assessment.model.Topic;
import com.psl.assessment.model.User;
import com.psl.assessment.service.GMailSender;
import com.psl.assessment.service.QuizService;
import com.psl.assessment.service.UserService;

import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
//import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


/**
* Controller class for QuizMaster application, handling administrative operations.
* Provides RESTful endpoints for managing users, quizzes, topics, and roles.
*
* @RestController indicates that this class contains RESTful endpoints.
* @Tag(name="Admin Api") specifies the Swagger tag for API documentation.
* @RequestMapping("/quizmaster") sets the base path for API endpoints.
* 
*/
@RestController
@Tag(name="Admin Api")
@RequestMapping("/quizmaster/admin")
public class AdminController {
	
	
	/**
	* Autowired UserService for user-related operations.
	*/
	@Autowired
	private UserService userService;
	
	/**
	* Autowired QuizService for quiz-related operations.
	*/
	@Autowired
	private QuizService quizService;
	
	/**
	* Autowired GMailSender for sending emails.
	*/
	@Autowired
	private GMailSender gmail;

	/**
	* Adds a new user to QuizMaster.
	*
	* @param user The user details provided in the request body.
	* @return UserDto representing the newly added user.
	*/
	@Operation(summary = "Add new User",description = "EndPoint to create a new user for QuizMaster")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User is registered successfully"),
	        @ApiResponse(responseCode = "400", description = "No Username found",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class))),
	        @ApiResponse(responseCode = "401", description = "INVALID",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class)))})
	@PostMapping("/addUser")
	@PreAuthorize("hasAuthority('ADMIN')")
	public UserDto addUser(@RequestBody(required = false) User user) {
		User user1=user;
		UserDto newUser= userService.addUser(user).UserToUserDto();
		gmail.sendEmail(user1.getEmail(), user1.getRole(), user1.getPassword());
		return newUser;
	}
	
	/**
	* Retrieves a list of all users from QuizMaster.
	*
	* @param offset Offset for pagination.
	* @param pageSize Number of users per page.
	* @return List of UserDto representing the users.
	*/
	@Operation(summary = "Get List Of All Users",description = "EndPoint to get the list of all users of QuizMaster")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Listing Of User Done Successfully"),
	        @ApiResponse(responseCode = "400", description = "Bad Request",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class))),
	        @ApiResponse(responseCode = "401", description = "INVALID",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class)))})
	@GetMapping("/getAllUsers")
	public List<UserDto> getAllUsers(@RequestParam(name = "offset", defaultValue = "0") int offset,
	                              @RequestParam(name = "pageSize", defaultValue = "3") int pageSize) {
		Page<User> userPage  =userService.getAllUsers(offset, pageSize);
		List<UserDto> userDtoResponse = userPage.getContent().stream().map(UserDto::toDtoResponse).collect(Collectors.toList());
		return userDtoResponse;
										
	}
	
	/**
	* Retrieves a list of all topics from QuizMaster.
	*
	* 
	* @return List of Topics representing the topics.
	*/
	@Operation(summary = "Get List Of Predefined Topics",description = "EndPoint to get the list of all topics of QuizMaster")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Listing Of Topics"),
	        @ApiResponse(responseCode = "400", description = "Bad Request",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class))),
	        @ApiResponse(responseCode = "401", description = "INVALID",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class)))})
	@GetMapping("/topics")
	public List<Topic> getListofTopics(){
		return this.quizService.getListofTopics();
	}
	
	
	/**
	* Retrieves a list of roles from QuizMaster.
	*
	* 
	* @return List of roles representing the roles.
	*/
	@Operation(summary = "Get List Of Predefined Roles",description = "EndPoint to get the list of user roles of QuizMaster")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Listing Of Roles"),
	        @ApiResponse(responseCode = "400", description = "Bad Request",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class))),
	        @ApiResponse(responseCode = "401", description = "INVALID",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class)))})
	@GetMapping("/getRoles")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<Role> getRoles() {
		System.out.println("hi");
		return userService.getListOfRoles();
	}

	
	/**
	* Retrieves a list of all quizzes from QuizMaster.
	*
	* @param offset Offset for pagination.
	* @param pageSize Number of quizzes per page.
	* @return List of QuizDto representing the quizzes.
	*/
	@Operation(summary = "Get List Of Quizzes",description = "EndPoint to get the list of all quizzes of QuizMaster")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Listing Of Topics"),
	        @ApiResponse(responseCode = "400", description = "Bad Request",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class))),
	        @ApiResponse(responseCode = "401", description = "INVALID",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class)))})
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/getAllQuizzes")
	public List<QuizDto> getQuizzes(@RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "pageSize", defaultValue = "2") int pageSize)
	{
		return quizService.getAllQuizzes(offset,pageSize);
	}
	
	
	/**
	* Retrieves a list of all quizzes by topic from QuizMaster.
	*
	*@param topic.
	* @param offset Offset for pagination.
	* @param pageSize Number of users per page.
	* @return List of QuizDto representing the quizzes by topic.
	*/
	@Operation(summary = "Get List Of Particular Topic's Quiz",description = "EndPoint to get the list of all quizzes of particular topic of QuizMaster")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Listing Of Topic's quizzes"),
	        @ApiResponse(responseCode = "400", description = "Bad Request",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class))),
	        @ApiResponse(responseCode = "401", description = "INVALID",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class)))})
	@GetMapping("/getQuiz/{topic}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<QuizDto> getQuizzesByTopic(@PathVariable String topic,@RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "pageSize", defaultValue = "2") int pageSize){
	
		return quizService.getQuizzesByTopic(topic,offset,pageSize);
		
		
	}
	
	/**
	* Retrieves a list of all users from QuizMaster.
	*
	* 
	* @param Id to get the quiz.
	* @return List of UserDto representing the users.
	*/
	@Operation(summary = "Get Quiz By Id",description = "EndPoint to get Quiz by id of QuizMaster")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Quiz By Id"),
	        @ApiResponse(responseCode = "400", description = "Bad Request",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class))),
	        @ApiResponse(responseCode = "401", description = "INVALID",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class)))})
	@GetMapping("/getQuiz/id={id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public QuizDto getQuizById(@PathVariable Long id)
	{
		return quizService.getQuizById(id).convertToDto();
		
	}
	
	
	
	/**
	* Update the status of quiz from QuizMaster.
	*
	* @param Id for retrieving the quiz.
	* @param StatusDto to update the current status with this.
	* @return QuizDto with updated status.
	*/
	@Operation(summary = "Update The Status Of Quiz",description = "EndPoint to update status of a quiz of QuizMaster")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Status Updated Successfully"),
	        @ApiResponse(responseCode = "400", description = "Bad Request",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class))),
	        @ApiResponse(responseCode = "401", description = "INVALID",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class)))})
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/updateQuizStatus/{id}")
	public QuizDto updateQuizStatus(@PathVariable("id") Long id,@RequestBody StatusDto statusDto)
	{
		Quiz quiz=quizService.getQuizById(id);
		System.out.println(quiz);
		quiz.setStatus(statusDto.getStatus());
		return quizService.addQuiz(quiz).convertToDto();
		
	}
	
	

	/**
	* List the quiz by status from QuizMaster.
	*
	* 
	* @param StatusDto to list the quizzes with this.
	* @return List of QuizDto by status.
	*/
	@Operation(summary = "List Of Quiz By Status",description = "EndPoint to get list of quizzes by status of QuizMaster")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Listing of quizzes by status"),
	        @ApiResponse(responseCode = "400", description = "Bad Request",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class))),
	        @ApiResponse(responseCode = "401", description = "INVALID",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class)))})
	@PostMapping("/getAllQuizBystatus")
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<QuizDto> getAllQuizByStatus(@RequestBody StatusDto statusDto)
	{
		return quizService.getQuizzesByStatus(statusDto.getStatus());
	}
	
}
