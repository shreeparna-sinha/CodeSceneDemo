//package com.persistent.assessment.service;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import com.persistent.assessment.dao.QuizRepository;
//import com.persistent.assessment.dao.UserRepository;
//import com.persistent.assessment.dto.QuizDto;
//import com.persistent.assessment.model.Quiz;
//import com.persistent.assessment.model.Status;
//import com.persistent.assessment.model.Topic;
//import com.persistent.assessment.model.User;
//
//@Service
//public class QuizServiceImpl implements QuizService {
//
//	@Autowired
//	private QuizRepository quizRepo;
//	
//	@Autowired
//	private UserRepository userRepo;
//
//	@Override
//	public Quiz addQuiz(Quiz quiz) {
//		Long trainerId = getAuthenticatedTrainerId();
//		if(trainerId!=null) {
//			quiz.setTrainerId(trainerId);	}
//	else {
//		quiz.setTrainerId(null);}
//		
//		return quizRepo.save(quiz);
//	}
//
//	@Override
//	public List<Topic> getListofTopics() {
//		return Arrays.asList(Topic.values());
//	}
//	
//	@Override
//	public List<QuizDto> getAllQuizzes()
//	{
//		List<QuizDto> quizDto=new ArrayList<QuizDto>();
//		List<Quiz> quizzes= quizRepo.findAllByTrainerId(getAuthenticatedTrainerId());
//		for(Quiz quiz : quizzes)
//		{
//			quizDto.add(quiz.convertToDto());
//		}
//		return quizDto;
//	}
//
//	@Override
//	public Quiz getQuizById(Long id) {
//		return quizRepo.findById(id).get();
//	}
//	
//	public List<Quiz> getQuizzesByTopic(String topic){
//		return quizRepo.getQuizzesByTopic(topic);
//	}
//	
//	private Long getAuthenticatedTrainerId()
//	{
//		org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if(authentication.getPrincipal() instanceof UserDetails)
//		{
//			UserDetails userDetails =(UserDetails) authentication.getPrincipal();
//			String userName= userDetails.getUsername();
//			String passWord = userDetails.getPassword();
//			User user = userRepo.findByEmailAndPassword(userName,passWord);
//			return user.getId();		
//	}
//		return null;
// 
//}
//}
package com.psl.assessment.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.psl.assessment.dao.QuizRepository;
import com.psl.assessment.dao.UserRepository;
import com.psl.assessment.dto.QuizDto;
import com.psl.assessment.exception.DuplicateQuestionException;
import com.psl.assessment.exception.QuestionCountException;
import com.psl.assessment.exception.QuizAddException;

import com.psl.assessment.model.Question;
import com.psl.assessment.model.Quiz;
import com.psl.assessment.model.Status;
import com.psl.assessment.model.Topic;
import com.psl.assessment.model.User;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizRepository quizRepo;

	@Autowired
	private UserRepository userRepo;

	@Override
	public Quiz addQuiz(Quiz quiz) {
		Long trainerId = getAuthenticatedTrainerId();

//		if(trainerId!=null) {
		if (isValidQuiz(quiz)) {
			quiz.setTrainerId(trainerId);
		}

		// }
		else {
			// quiz.setTrainerId(null);
			throw new QuizAddException("Quiz Cannot Be Added");
		}

		return quizRepo.save(quiz);
	}

	@Override
	public List<Topic> getListofTopics() {
		return Arrays.asList(Topic.values());
	}

	@Override
	public List<QuizDto> getAllQuizzesByTrainer(int offset, int pageSize) {
		List<QuizDto> quizDto = new ArrayList<QuizDto>();
//		List<Quiz> quizzes= quizRepo.findAllByTrainerId(getAuthenticatedTrainerId());
		PageRequest pageable = PageRequest.of(offset, pageSize);
		Page<Quiz> quizzes = quizRepo.findAllByTrainerId(getAuthenticatedTrainerId(), pageable);
		for (Quiz quiz : quizzes) {
			quizDto.add(quiz.convertToDto());
		}
		return quizDto;
	}

	@Override
	public List<QuizDto> getAllQuizzes(int offset, int pageSize) {
		List<QuizDto> quizDto = new ArrayList<QuizDto>();
//		List<Quiz> quizzes= quizRepo.findAllByTrainerId(getAuthenticatedTrainerId());
		PageRequest pageable = PageRequest.of(offset, pageSize);
		Page<Quiz> quizzes = quizRepo.findAll(pageable);
		for (Quiz quiz : quizzes) {
			quizDto.add(quiz.convertToDto());
		}
		return quizDto;
	}

	@Override
	public Quiz getQuizById(Long id) {
		return quizRepo.findById(id).get();
	}

//	public List<Quiz> getQuizzesByTopic(String topic){
//		return quizRepo.getQuizzesByTopic(topic);
//	}
	public List<QuizDto> getQuizzesByTopic(String topic, int offset, int pageSize) {
		// return quizRepo.getQuizzesByTopic(topic);
		List<QuizDto> quizDto = new ArrayList<QuizDto>();
//		List<Quiz> quizzes= quizRepo.findAllByTrainerId(getAuthenticatedTrainerId());
		PageRequest pageable = PageRequest.of(offset, pageSize);
		Page<Quiz> quizzes = quizRepo.getQuizzesByTopic(topic, pageable);
		for (Quiz quiz : quizzes) {
			quizDto.add(quiz.convertToDto());
		}
		return quizDto;
	}

	public Long getAuthenticatedTrainerId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails ) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String userName = userDetails.getUsername();
			String passWord = userDetails.getPassword();
			User user = userRepo.findByEmailAndPassword(userName, passWord);
			return user.getId();
		}
		return null;

	}
//	private Boolean getQuizStatus() {
//		
//	}

	@Override
	public List<QuizDto> getActiveQuizzesByTopic(String topic, int offset, int pageSize) {
		// return quizRepo.getQuizzesByTopic(topic);
		List<QuizDto> quizDto = new ArrayList<QuizDto>();
//		List<Quiz> quizzes= quizRepo.findAllByTrainerId(getAuthenticatedTrainerId());
		PageRequest pageable = PageRequest.of(offset, pageSize);
		Page<Quiz> quizzes = quizRepo.getActiveQuizzesByTopic(topic, Status.ACTIVE.toString(), pageable);
		System.out.println(quizzes);
		for (Quiz quiz : quizzes) {
			quizDto.add(quiz.convertToDto());
		}
		return quizDto;
	}

	boolean isValidQuiz(Quiz quiz) {

		List<Question> allQuestions = quiz.getQuestionsList();

		int numberOfQuestions = quiz.getQuestionCount();

		int questionCounter = allQuestions.size();

		if (questionCounter == numberOfQuestions) {

			Set<String> duplicateCheck = new HashSet<>();

			for (Question question : allQuestions) {

				duplicateCheck.add(question.getStatement());

			}

			if (duplicateCheck.size() == allQuestions.size()) {

				return true;

			} else {

				System.out.println("Duplicate questions found! Please re-check the quiz.");
				throw new DuplicateQuestionException("Duplicate questions found! Please re-check the quiz.");

			}

		} else if (questionCounter > numberOfQuestions) {

			System.out.println("You have added " + (questionCounter - numberOfQuestions)
					+ " extra questions. Please re-check the quiz.");
			throw new QuestionCountException("You have added " + (questionCounter - numberOfQuestions)
					+ " extra questions. Please re-check the quiz.");
		} else {

			System.out.println("You have added " + (numberOfQuestions - questionCounter)
					+ " less questions. Please re-check the quiz.");
			throw new QuestionCountException("You have added " + (numberOfQuestions - questionCounter)
					+ " less questions. Please re-check the quiz.");

		}

		

	}

	@Override
	public List<QuizDto> getQuizzesByStatus(String status) {
		List<Quiz> quizzesByStatus=quizRepo.findAllByStatus(status);
		List<QuizDto> dtoQuizzes=new ArrayList<>();
		for(Quiz quiz : quizzesByStatus)
		{
			dtoQuizzes.add(quiz.convertToDto());
		}
		return dtoQuizzes;
	}
	
	
//	@Override
//	public List<QuizDto> getQuizzesByTopic(String topic) {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
