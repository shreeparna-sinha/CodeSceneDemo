package com.psl.assessment.model;

import java.util.ArrayList;
import java.util.List;

import com.psl.assessment.dto.QuestionDto;
import com.psl.assessment.dto.QuestionPaperDto;
import com.psl.assessment.dto.QuizDto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Quiz {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private Integer durationPerQuestion;
	private Integer questionCount;
	private Long trainerId;
	@Enumerated(EnumType.STRING)
	private Topic topic;
	@Enumerated(EnumType.STRING)
	private Status status = Status.ACTIVE;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "quiz_id")
	private List<Question> questionsList;

	
	
	
	
	
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

	public List<Question> getQuestionsList() {
		return questionsList;
	}

	public void setQuestionsList(List<Question> questionsList) {
		this.questionsList = questionsList;
	}

	public void setTopic(String topic) {
		this.topic = Topic.parseFromString(topic);
	}

	public void setStatus(String status) {
		this.status = Status.parseFromString(status);
	}
	
	

	public Long getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(Long trainerId) {
		this.trainerId = trainerId;
	}

	public Quiz() {
		super();
	}

	public Quiz(Long id, String name, Integer durationPerQuestion, Integer questionCount,Long trainerId,Status status, Topic topic, 
			List<Question> questionsList) {
		super();
		this.id = id;
		this.name = name;
		this.durationPerQuestion = durationPerQuestion;
		this.questionCount = questionCount;
		this.trainerId=trainerId;
		this.topic = topic;
		this.status = status;
		this.questionsList = questionsList;
	}

	

	

	@Override
	public String toString() {
		return "Quiz [id=" + id + ", name=" + name + ", durationPerQuestion=" + durationPerQuestion + ", questionCount="
				+ questionCount + ", topic=" + topic + ", status=" + status + ", questionsList=" + questionsList + "]";
	}
	
	public QuizDto convertToDto()
	{
		QuizDto dto=new QuizDto(this.getId(),this.getName(),this.getDurationPerQuestion(),this.getQuestionCount(),this.getTrainerId(),this.getTopic(),this.getStatus());
		return dto;
	}
	
	public QuestionPaperDto toQuestionPaper()
	{
		List<QuestionDto> questionsList=new ArrayList<>();
		for(Question ques : this.getQuestionsList())
		{
			questionsList.add(ques.toQuestionDto());
		}
		return new QuestionPaperDto(this.getId(), this.getName(), this.getDurationPerQuestion(), this.getQuestionCount(), this.getTrainerId(), this.getTopic(),questionsList);
	}
	
	
}
