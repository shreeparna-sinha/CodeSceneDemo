package com.psl.assessment.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psl.assessment.dao.UserRepository;
import com.psl.assessment.dto.RankDto;
import com.psl.assessment.model.AnswerSheet;
import com.psl.assessment.service.AnswerSheetService;
import com.psl.assessment.service.QuizService;

import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
* Controller class for handling leaderboard-related operations.
*/
@RestController
@RequestMapping("/quizmaster/leaderboard")
@Tag(name="LeaderBoard Api")
public class LeaderBoardController {
	
	@Autowired
	private AnswerSheetService answerSheetService;
	
	@Autowired
	private QuizService quizService;
	
	@Autowired
	private UserRepository userRepo;
	

	/**
	 * Retrieves and displays the leaderboard for a particular quiz.
	 *
	 * @param id The ID of the quiz for which the leaderboard is requested.
	 * @return List of RankDto representing the leaderboard.
	 */
	@Operation(summary = "Show The Leaderboard For Particular Quiz",description = "EndPoint to show top rankers of a quiz  of QuizMaster")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Leaderboard Is Displayed Successfully"),
	        @ApiResponse(responseCode = "400", description = "Paper Submitted",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class))),
	        @ApiResponse(responseCode = "401", description = "INVALID",content=@Content(mediaType = "application/json", schema = @Schema(implementation = Json.class)))})
	@GetMapping("/showLeaderboard/{id}")
	public List<RankDto> viewLeaderboard(@PathVariable Long id)
	{
		
		List<AnswerSheet> topResults= answerSheetService.getLeaderboard(id);
		List<RankDto> leaderBoard=new ArrayList<>();
		for(AnswerSheet ans : topResults)
		{
			String quizName=quizService.getQuizById(id).getName();
			String studentName=userRepo.findById(ans.getUserId()).get().getName();
			int rank=answerSheetService.getRank(ans.getMarksObtained(), id);
			leaderBoard.add(ans.toRankDto(studentName, quizName, rank));
		}
		if(leaderBoard.size()>3)
		{
			return leaderBoard;
		}
		return null;//top3 student for this quiz is not present
	}

}
