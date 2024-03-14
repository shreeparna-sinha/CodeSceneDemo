package com.psl.assessment.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.psl.assessment.model.Quiz;


/**
* Repository interface for Question entities.
*/
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long>{
	
	/**
	* Retrieve quizzes by topic with pagination.
	*
	* @param topic The topic of the quizzes to retrieve.
	* @param pageable Page request details for pagination.
	* @return Page of quizzes matching the specified topic.
	*/
	@Query(value="select * from Quiz q where q.topic =:topic",nativeQuery=true)
	Page<Quiz> getQuizzesByTopic(@Param("topic") String topic, PageRequest pageable);
	
	/**
	* Retrieve active quizzes by topic with pagination.
	*
	* @param topic The topic of the quizzes to retrieve.
	* @param status The status of the quizzes to retrieve.
	* @param pageable Page request details for pagination.
	* @return Page of active quizzes matching the specified topic and status.
	*/
	@Query(value="select * from Quiz q where q.topic =:topic AND q.status= :status",nativeQuery=true)
	Page<Quiz> getActiveQuizzesByTopic(@Param("topic") String topic, @Param("status") String status,PageRequest pageable);

	/**
	* Retrieve all quizzes with pagination.
	*
	* @param pageable Page request details for pagination.
	* @return Page of all quizzes.
	*/
	@Query(value="select * from Quiz",nativeQuery=true)
	Page<Quiz> findAll(PageRequest pageable);
	//public List<QuizDto> getAllQuizzesByTrainer(int offset,int pageSize);
	
	/**
	* Retrieve quizzes by status.
	*
	* @param status The status of the quizzes to retrieve.
	* @return List of quizzes matching the specified status.
	*/
	@Query(value="select * from Quiz q where q.status=:status",nativeQuery=true)
	List<Quiz> findAllByStatus(String status);

	
	/**
	* Retrieve all quizzes by trainer ID with pagination.
	*
	* @param authenticatedTrainerId The ID of the trainer.
	* @param pageable Page request details for pagination.
	* @return Page of quizzes for the specified trainer ID.
	*/
	Page<Quiz> findAllByTrainerId(Long authenticatedTrainerId, PageRequest pageable);
	
}
