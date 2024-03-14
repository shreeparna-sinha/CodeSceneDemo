package com.psl.assessment.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.psl.assessment.model.Question;

/**
* Repository interface for Question entities.
*/
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>{

}
