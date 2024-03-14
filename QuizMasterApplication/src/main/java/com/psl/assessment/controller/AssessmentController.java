package com.psl.assessment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.psl.assessment.dto.QuestionDto;
import com.psl.assessment.dto.QuestionPaperDto;
import com.psl.assessment.dto.QuizDto;
import com.psl.assessment.dto.RankDto;
import com.psl.assessment.exception.AutoSubmitException;
import com.psl.assessment.exception.QuizNotSelectedException;
import com.psl.assessment.exception.TimerUpException;

import com.psl.assessment.model.Quiz;
import com.psl.assessment.model.Topic;
import com.psl.assessment.service.AnswerSheetService;
import com.psl.assessment.service.QuizService;
import com.psl.assessment.service.UserService;

import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
* Controller class for managing assessment-related endpoints in the QuizMaster application.
*/
@RestController
@RequestMapping("/quizmaster/assessment")
@Tag(name="Assessment Api")
public class AssessmentController {

	@Autowired
	private AnswerSheetService answerSheetService;
	
	@Autowired
	private QuizService quizService;
	
	@Autowired
	private UserService userService;
	
	
	private boolean flag=true;
	
	
	/**
	* Get the list of predefined topics.
	*
	* @return List of Topic objects.
	* @throws AutoSubmitException If the paper has been auto-submitted.
	*/
	@Operation(summary = "Get List Of Predefined Topics",description = "EndPoint to get the list of all topics of QuizMaster")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Listing Of Topics"),
	        @ApiResponse(responseCode = "400", description = "Paper Submitted",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class))),
	        @ApiResponse(responseCode = "401", description = "INVALID",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class)))})
	@GetMapping("/getTopics")
	@PreAuthorize("hasAuthority('STUDENT')")
	public List<Topic> getTopics() {
		if(flag==true)
		return userService.getListOfTopics();
		else
		{
			this.autoSubmit();
			throw new AutoSubmitException("Your Paper Has Been Auto Submitted ");
			
		}
	}
	
	
	/**
	* Get the list of active quizzes by topic.
	*
	* @param String type topic.
	* @param offset Offset for pagination.
	* @param pageSize Number of users per page.
	* @return List of QuizDto.
	* @throws AutoSubmitException If the paper has been auto-submitted.
	*/
	@Operation(summary = "Get List Of Active Quizzes By Topics",description = "EndPoint to get the list of active quizzes by topic of QuizMaster")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Listing Of Active Quizzes"),
	        @ApiResponse(responseCode = "400", description = "Paper Submitted",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class))),
	        @ApiResponse(responseCode = "401", description = "INVALID",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class)))})
	@GetMapping("/getQuiz/{topic}")
	@PreAuthorize("hasAuthority('STUDENT')")
	public List<QuizDto> getQuizzesByTopic(@PathVariable String topic,@RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "pageSize", defaultValue = "2") int pageSize){
		if(flag==true)
		return quizService.getActiveQuizzesByTopic(topic,offset,pageSize);
		else
		{
			this.autoSubmit();
			throw new AutoSubmitException("Your Paper Has Been Auto Submitted ");
			
		}
	}
	
	
	/**
	* Get the question paper.
	*
	* @param Id of the quiz
	* @return Question paper object
	* @throws AutoSubmitException If the paper has been auto-submitted.
	*/
	@Operation(summary = "Select One Active Quiz To Attempt",description = "EndPoint to select the quiz to attempt of QuizMaster")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Quiz Is Selected Successfully To Attempt"),
	        @ApiResponse(responseCode = "400", description = "Paper Submitted/Quiz Is Inactive",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class))),
	        @ApiResponse(responseCode = "401", description = "INVALID",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class)))})
	@GetMapping("/attemptQuiz/{id}")
	@PreAuthorize("hasAuthority('STUDENT')")
	public QuestionPaperDto attemptQuiz(@PathVariable Long id)
	{
		if(flag==true)
		{
			answerSheetService.setCurrentQuizId(id);
			System.out.println(quizService.getQuizById(id));
			return answerSheetService.getCurrentQuiz().toQuestionPaper();
			
		}
		else
		{
			this.autoSubmit();
			throw new AutoSubmitException("Your Paper Has Been Auto Submitted ");
			
		}
	}
	
	
	/**
	* Start the assessment.
	*
	* 
	* @return Response entity.
	* @throws QuizNotSelectedException If the quiz is not selected to attempt.
	*/
	@Operation(summary = "Start Assessment",description = "EndPoint to start the quiz of QuizMaster")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Assessment Is Started Successfully"),
	        @ApiResponse(responseCode = "400", description = "No Quiz Selected",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class))),
	        @ApiResponse(responseCode = "401", description = "INVALID",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class)))})
	@GetMapping("/startQuiz")
	@PreAuthorize("hasAuthority('STUDENT')")
	public ResponseEntity<String> startQuiz()
	{
		if(answerSheetService.getCurrentQuizId()==null)
		{
			throw new QuizNotSelectedException("no quiz selected");//no quiz selected
		}
		flag=false;
		Quiz quiz=answerSheetService.getCurrentQuiz();
		Integer totalTime=quiz.getDurationPerQuestion()*quiz.getQuestionCount();
		Integer time=totalTime*60000;
		Timer timer=new Timer();
		
		TimerTask task=new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				flag=true;
				System.out.println("Quiz is over");
			}
			
		};
		
		timer.schedule(task, time);
		//timer is started message
		return ResponseEntity.ok("timer is started");
		
	}
	
	
	//pagination to show one question at a time
	/**
	* Show the questions of assessment.
	*
	* 
	* @return List of QuestionDto.
	* @throws QuizNotSelectedException If the quiz is not selected to attempt.
	* @throws TimerUpException If the quiz timer is up.
	*/
	@Operation(summary = "Attempt Assessment Question ",description = "EndPoint to Attempt All Questions of Assessment of QuizMaster")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Attempt Assessment Question"),
	        @ApiResponse(responseCode = "400", description = "Paper Submitted",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class))),
	        @ApiResponse(responseCode = "401", description = "INVALID",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class)))})
	@PostMapping("/saveAndNext")
	@PreAuthorize("hasAuthority('STUDENT')")
	public List<QuestionDto> saveAndNext()
	{
		if(answerSheetService.getCurrentQuizId()==null)
		{
			throw new QuizNotSelectedException("no quiz selected");//no quiz selected
		}
		if(flag==false)
		{
			return answerSheetService.showNextQuestion();
		}
		else 
		{
			//timer is up
			throw new TimerUpException("Timer is Up");
		}
		//all question are over ->show only last question
	}
	
	
	/**
	* AutoSubmit the assessment.
	*
	* 
	* @return RankDto object.
	* 
	*/
	public RankDto autoSubmit()
	{
		Map<Long,Integer> answers=new HashMap<>();
		return answerSheetService.addAnswerSheet(answers);
	}
	
	
	
	/**
	* Submit the assessment.
	*
	* 
	* @return RankDto object.
	* @throws QuizNotSelectedException If the quiz is not selected to attempt.
	*/
	@Operation(summary = "Submit Quiz",description = "EndPoint to submit the quiz  of QuizMaster")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Quiz Is Submitted Successfully "),
	        @ApiResponse(responseCode = "400", description = "Paper Submitted",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class))),
	        @ApiResponse(responseCode = "401", description = "INVALID",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class)))})
	@PostMapping("/submit")
	@PreAuthorize("hasAuthority('STUDENT')")
	public RankDto submit(@RequestBody Map<Long,Integer> answers)
	{
		if(answerSheetService.getCurrentQuizId()==null)
		{
			throw new QuizNotSelectedException("no quiz selected");//no quiz selected
		}
		return answerSheetService.addAnswerSheet(answers);
	}
	
	
	
}
