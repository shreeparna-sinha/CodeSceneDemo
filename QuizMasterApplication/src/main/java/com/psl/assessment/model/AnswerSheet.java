package com.psl.assessment.model;

import com.psl.assessment.dto.RankDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AnswerSheet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long answerSheetId;
	private Long userId;
	private Long quizId;
	private int marksObtained;
	private Long NumberOfAttemptsLeft;
	public AnswerSheet(Long answerSheetId, Long userId, Long quizId, int marksObtained, Long numberOfAttemptsLeft) {
		super();
		this.answerSheetId = answerSheetId;
		this.userId = userId;
		this.quizId = quizId;
		this.marksObtained = marksObtained;
		this.NumberOfAttemptsLeft = numberOfAttemptsLeft;
	}
	
	
	public AnswerSheet(Long userId, Long quizId, int marksObtained, Long numberOfAttemptsLeft) {
		super();
		this.userId = userId;
		this.quizId = quizId;
		this.marksObtained = marksObtained;
		this.NumberOfAttemptsLeft = numberOfAttemptsLeft;
	}

	public AnswerSheet()
	{
		
	}

	public Long getAnswerSheetId() {
		return answerSheetId;
	}
	public void setAnswerSheetId(Long answerSheetId) {
		this.answerSheetId = answerSheetId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getQuizId() {
		return quizId;
	}
	public void setQuizId(Long quizId) {
		this.quizId = quizId;
	}
	public int getMarksObtained() {
		return marksObtained;
	}
	public void setMarksObtained(int marksObtained) {
		this.marksObtained = marksObtained;
	}
	public Long getNumberOfAttemptsLeft() {
		return NumberOfAttemptsLeft;
	}
	public void setNumberOfAttemptsLeft(Long numberOfAttemptsLeft) {
		this.NumberOfAttemptsLeft = numberOfAttemptsLeft;
	}
	@Override
	public String toString() {
		return "AnswerSheet [answerSheetId=" + answerSheetId + ", userId=" + userId + ", quizId=" + quizId
				+ ", marksObtained=" + marksObtained + ", NumberOfAttemptsLeft=" + NumberOfAttemptsLeft + "]";
	}
	
	public RankDto toRankDto(String studentName,String quizName,int rank)
	{
		return new RankDto(this.getUserId(), studentName, this.getQuizId(), quizName, this.getMarksObtained(), rank);
	}
	
}
