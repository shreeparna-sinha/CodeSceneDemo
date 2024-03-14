package com.psl.assessment.service;

import java.util.List;
import java.util.Map;

import com.psl.assessment.dto.QuestionDto;
import com.psl.assessment.dto.RankDto;
import com.psl.assessment.model.AnswerSheet;

import com.psl.assessment.model.Quiz;

public interface AnswerSheetService {
	
	
	public Long getCurrentQuizId();
	public void setCurrentQuizId(Long currentQuizId);
	public int getRank(int marks,Long quizId);
	public Quiz getCurrentQuiz();
	public List<QuestionDto> showNextQuestion();
	public RankDto addAnswerSheet(Map<Long,Integer> answers);
	List<AnswerSheet> getLeaderboard(Long id);
}
