
package com.psl.assessment.service;

import java.util.List;




import com.psl.assessment.dto.QuizDto;

import com.psl.assessment.model.Quiz;
import com.psl.assessment.model.Topic;


public interface QuizService {

	
	public Quiz addQuiz(Quiz quiz);
	
	public List<Topic> getListofTopics();
	
	//public List<QuizDto> getQuizzes();
	
	public Quiz getQuizById(Long id);
	
	//List<QuizDto> getQuizzesByTopic(String topic);
	List<QuizDto> getQuizzesByTopic(String topic,int offset,int pageSize);

	public List<QuizDto> getAllQuizzes(int offset,int pageSize);

	List<QuizDto> getAllQuizzesByTrainer(int offset, int pageSize);
	
	public Long getAuthenticatedTrainerId();
	
	public List<QuizDto> getQuizzesByStatus(String status);

	public List<QuizDto> getActiveQuizzesByTopic(String topic, int offset, int pageSize);
}

