package com.psl.assessment.dto;

public class RankDto {
	
	public Long studentId;
	public String studentName;
	public Long quizId;
	public String quizName;
	public int marks;
	public int rank;
	
	
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public Long getQuizId() {
		return quizId;
	}
	public void setQuizId(Long quizId) {
		this.quizId = quizId;
	}
	public String getQuizName() {
		return quizName;
	}
	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}
	public int getMarks() {
		return marks;
	}
	public void setMarks(int marks) {
		this.marks = marks;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public RankDto(Long studentId, String studentName, Long quizId, String quizName, int marks, int rank) {
		super();
		this.studentId = studentId;
		this.studentName = studentName;
		this.quizId = quizId;
		this.quizName = quizName;
		this.marks = marks;
		this.rank = rank;
	}
	
	

}
