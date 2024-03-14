package com.psl.assessment.dto;


import com.psl.assessment.model.Status;
import com.psl.assessment.model.Topic;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class QuizDto {

	private Long id;
	private String name;
	private Integer durationPerQuestion;
	private Integer questionCount;
	private Long trainerId;
	@Enumerated(EnumType.STRING)
	private Topic topic;
	@Enumerated(EnumType.STRING)
	private Status status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getDurationPerQuestion() {
		return durationPerQuestion;
	}
	public void setDurationPerQuestion(Integer durationPerQuestion) {
		this.durationPerQuestion = durationPerQuestion;
	}
	public Integer getQuestionCount() {
		return questionCount;
	}
	public void setQuestionCount(Integer questionCount) {
		this.questionCount = questionCount;
	}
	public Long getTrainerId() {
		return trainerId;
	}
	public void setTrainerId(Long trainerId) {
		this.trainerId = trainerId;
	}
	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	
	
	
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public QuizDto(Long id, String name, Integer durationPerQuestion, Integer questionCount, Long trainerId,
			Topic topic,Status status) {
		super();
		this.id = id;
		this.name = name;
		this.durationPerQuestion = durationPerQuestion;
		this.questionCount = questionCount;
		this.trainerId = trainerId;
		this.topic = topic;
		this.status=status;
		
	}

	
	
}
