package com.psl.assessment.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.psl.assessment.dao.AnswerSheetRepository;
import com.psl.assessment.dao.QuestionRepository;

import com.psl.assessment.dao.UserRepository;
import com.psl.assessment.dto.QuestionDto;
import com.psl.assessment.dto.RankDto;
import com.psl.assessment.exception.QuizInactiveException;
import com.psl.assessment.model.AnswerSheet;
import com.psl.assessment.model.Question;
import com.psl.assessment.model.Quiz;
import com.psl.assessment.model.Status;
import com.psl.assessment.model.User;

@Service
public class AnswerSheetServiceImpl implements AnswerSheetService{
	@Autowired
	private AnswerSheetRepository answerSheetRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private QuizService quizService;
	
	@Autowired
	private QuestionRepository questionRepo;
	
	
	//private Map<Long,Integer> answers=new HashMap<>();
	
	private Long currentQuizId=null;
	
	//private Iterator<Question> questionIterator;
	
	
	
	public Long getCurrentQuizId() {
		return currentQuizId;
	}



	public void setCurrentQuizId(Long currentQuizId) {
		this.currentQuizId = currentQuizId;
		//this.questionIterator=this.getCurrentQuiz().getQuestionsList().iterator();
	}
	
	public Quiz getCurrentQuiz()
	{
		//return quizService.getQuizById(currentQuizId);
		Long userId=this.getAuthenticatedStudentId();
		AnswerSheet result=answerSheetRepo.getAnswerSheet(userId, this.currentQuizId);
		if(result!=null && result.getNumberOfAttemptsLeft()==0L)
		{
			return null;//error for attempts are exhausted for this quiz
		}
		else if(quizService.getQuizById(currentQuizId).getStatus()==Status.ACTIVE) {
			return quizService.getQuizById(currentQuizId);
		}
		
		else {
			throw new QuizInactiveException("The Quiz is Inactive");
		}
		
	}



	public List<QuestionDto> showNextQuestion()
	{
		List<QuestionDto> questionsDto=new ArrayList<>();
		List<Question> questions= this.getCurrentQuiz().getQuestionsList();
		for(Question ques : questions)
		{
			questionsDto.add(ques.toQuestionDto());
		}
		return questionsDto;
		
	}
	
	
	
	public RankDto addAnswerSheet(Map<Long,Integer> answers)
	{
		Long studentId=this.getAuthenticatedStudentId();
		int marks=this.MarksObtained(answers);
		
		Long userId=this.getAuthenticatedStudentId();
		String userName=userRepo.findById(userId).get().getName();
		String quizName=this.getCurrentQuiz().getName();
		
		AnswerSheet answerSheet=answerSheetRepo.getAnswerSheet(userId, this.currentQuizId);
		if(answerSheet==null)
		{
			AnswerSheet newEntry= new AnswerSheet(studentId,this.currentQuizId, marks,2L );
			
			AnswerSheet result= answerSheetRepo.save(newEntry);
			
			int rank=this.getRank(marks,this.getCurrentQuizId());
			
			this.setCurrentQuizId(null);
			return result.toRankDto(userName, quizName, rank);
		}
		else
		{
			Long attempt=answerSheet.getNumberOfAttemptsLeft();
			answerSheet.setMarksObtained(marks);
			answerSheet.setNumberOfAttemptsLeft(attempt-1);
			
			AnswerSheet result= answerSheetRepo.save(answerSheet);
			System.out.println(result);
			int rank=this.getRank(marks,this.getCurrentQuizId());
			System.out.println("rank is"+rank);
			this.setCurrentQuizId(null);
			return result.toRankDto(userName, quizName, rank);
		}
	}
	
	public int MarksObtained(Map<Long,Integer> answers)
	{
		int marks=0;
		
		
		for(Map.Entry<Long, Integer> entry : answers.entrySet())
		{
			
			Long id=entry.getKey();
			if(id==0)
			{
				continue;
			}
			Question question=questionRepo.findById(id).get();
			
			if(question.getCorrectOption()==entry.getValue())
			{
				marks++;
			}
		}
		
		
		return marks;
		
	}
	
	private Long getAuthenticatedStudentId()
	{
		org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication.getPrincipal() instanceof UserDetails)
		{
			UserDetails userDetails =(UserDetails) authentication.getPrincipal();
			String userName= userDetails.getUsername();
			String passWord = userDetails.getPassword();
			User user = userRepo.findByEmailAndPassword(userName,passWord);
			return user.getId();		
	}
		return null;
 
   }
	
	public int getRank(int marks,Long quizId)
	{
	
		List<AnswerSheet> findAllByQuizId = answerSheetRepo.findAllByQuizId(quizId);
		
		List<Integer> allMarks=new ArrayList<>();
		for(AnswerSheet ans : findAllByQuizId)
		{
			allMarks.add(ans.getMarksObtained());
		}
		Collections.sort(allMarks, Collections.reverseOrder()); 
		
		int rank=1;
		Map<Integer,Integer> rankMap=new HashMap<>();
		for(Integer key : allMarks)
		{
			if(rankMap.containsKey(key))
			{
				continue;
			}
			rankMap.put(key, rank);
			rank++;
		}
		return rankMap.get(marks);
	}
	
	@Override
	public List<AnswerSheet> getLeaderboard(Long id) {
		
	
		
		return answerSheetRepo.findAllTop3MarksObtainedByQuizId(id);
	}
	

}
