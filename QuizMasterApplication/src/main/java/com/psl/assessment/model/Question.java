package com.psl.assessment.model;

import com.psl.assessment.dto.QuestionDto;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Question")
//@Table(name = "Question", uniqueConstraints = @UniqueConstraint(columnNames = {"statement"}))
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String statement;
	private String option1;
	private String option2;
	private String option3;
	private String option4;
	private Integer correctOption;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Question(Long id, String statement, String option1, String option2, String option3, String option4,
			Integer correctOption) {
		super();
		this.id = id;
		this.statement = statement;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.option4 = option4;
		this.correctOption = correctOption;
	}
	public String getStatement() {
		return statement;
	}
	public void setStatement(String statement) {
		this.statement = statement;
	}
	public String getOption1() {
		return option1;
	}
	public void setOption1(String option1) {
		this.option1 = option1;
	}
	public String getOption2() {
		return option2;
	}
	public void setOption2(String option2) {
		this.option2 = option2;
	}
	public String getOption3() {
		return option3;
	}
	public void setOption3(String option3) {
		this.option3 = option3;
	}
	public String getOption4() {
		return option4;
	}
	public void setOption4(String option4) {
		this.option4 = option4;
	}
	public Integer getCorrectOption() {
		return correctOption;
	}
	public void setCorrectOption(Integer correctOption) {
		this.correctOption = correctOption;
	}
	@Override
	public String toString() {
		return "Question [id=" + id + ", statement=" + statement + ", option1=" + option1 + ", option2=" + option2
				+ ", option3=" + option3 + ", option4=" + option4 + ", correctOption=" + correctOption + "]";
	}

	public QuestionDto toQuestionDto()
	{
		return new QuestionDto(this.getId(), this.getStatement(), this.getOption1(), this.getOption2(), this.getOption3(), this.getOption4());
	}
	
}
