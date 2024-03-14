package com.psl.assessment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.psl.assessment.dto.QuizDto;
import com.psl.assessment.model.Quiz;
import com.psl.assessment.model.Topic;
import com.psl.assessment.service.QuizService;

import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
* Controller class for handling Trainer-related operations in QuizMaster.
* @author shreeparna_sinha.
*/
@RestController
@Tag(name="Trainer Api")
@RequestMapping("/quizmaster/trainer")
public class TrainerController {
	
	@Autowired
	private QuizService quizService;
	
	/**
	* Retrieves the list of predefined topics in QuizMaster.
	*
	* @return List of Topic objects.
	*/
	@Operation(summary = "Get List Of Predefined Topics",description = "EndPoint to get the list of all topics of QuizMaster")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Listing Of Topics"),
	        @ApiResponse(responseCode = "400", description = "Bad Request",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class))),
	        @ApiResponse(responseCode = "401", description = "INVALID",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class)))})
	@GetMapping("/topics")
	@PreAuthorize("hasAuthority('TRAINER')")
	public List<Topic> getListofTopics(){
		return this.quizService.getListofTopics();
	}
	
	/**
	* Adds a new quiz to QuizMaster.
	*
	* @param quiz The Quiz object to be added.
	* @return The created Quiz object.
	*/
	@Operation(summary = "Add A New Quiz",description = "EndPoint to add new quiz to QuizMaster")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Quiz Created Successfully"),
	        @ApiResponse(responseCode = "400", description = "Question Count Is Not Valid",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class))),
	        @ApiResponse(responseCode = "401", description = "INVALID",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class)))})
	@PostMapping("/addQuiz")
	@PreAuthorize("hasAuthority('TRAINER')")
	public Quiz addQuizByTrainer(@RequestBody @Valid Quiz quiz) {

		return quizService.addQuiz(quiz);
	}
	
	/**
	* Retrieves the list of quizzes created by the trainer in QuizMaster.
	*
	* @param offset The offset for pagination.
	* @param pageSize The page size for pagination.
	* @return List of QuizDto objects.
	*/
	@Operation(summary = "Get List Of Quizzes Created By Trainer",description = "EndPoint to get the list of quizzes created by trainer of QuizMaster")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Listing Of Quizzes By Trainer"),
	        @ApiResponse(responseCode = "400", description = "Bad Request",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class))),
	        @ApiResponse(responseCode = "401", description = "INVALID",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class)))})
	@PreAuthorize("hasAuthority('TRAINER')")
	@GetMapping("/getAllQuizzesByTrainer")
	public List<QuizDto> getQuizzesByTrainer(@RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "pageSize", defaultValue = "2") int pageSize)
	{
		return quizService.getAllQuizzesByTrainer(offset,pageSize);
	}
	
	

}
