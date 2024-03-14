package com.psl.assessment.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.psl.assessment.model.AnswerSheet;

/**
* Repository interface for AnswerSheet entities.
*/
public interface AnswerSheetRepository extends CrudRepository<AnswerSheet,Long >{

	/**
	* Retrieves an AnswerSheet for a specific user and quiz.
	*
	* @param userId The ID of the user.
	* @param quizId The ID of the quiz.
	* @return The AnswerSheet for the specified user and quiz.
	*/
	@Query(value="select * from Answer_sheet a where a.user_id=:userId and a.quiz_id=:quizId",nativeQuery = true)
	AnswerSheet getAnswerSheet(@Param("userId")Long userId,@Param("quizId")Long quizId);
	/**
	* Retrieves the top 3 AnswerSheets based on Marks obtained for a specific quiz.
	*
	* @param quizId The ID of the quiz.
	* @return List of AnswerSheets representing the top 3 marks obtained.
	*/
	@Query(value = "select * from Answer_sheet s where s.quiz_id =:quizId and s.Marks_obtained>=(select distinct s.Marks_obtained from Answer_sheet s order by s.Marks_obtained desc limit 2,1)order by s.Marks_obtained desc", nativeQuery = true)
	List<AnswerSheet> findAllTop3MarksObtainedByQuizId(Long quizId);
	
	/**
	* Retrieves all AnswerSheets for a specific quiz.
	*
	* @param quizId The ID of the quiz.
	* @return List of AnswerSheets for the specified quiz.
	*/
	@Query(value="select * from Answer_sheet a where a.quiz_id=:quizId",nativeQuery = true)
	List<AnswerSheet> findAllByQuizId(Long quizId);
}
