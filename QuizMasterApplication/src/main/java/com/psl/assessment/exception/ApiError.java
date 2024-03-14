package com.psl.assessment.exception;

import java.util.Date;

public class ApiError {
private Integer erorCode;
private String errorData;
private Date date;
public Integer getErorCode() {
	return erorCode;
}
public void setErorCode(Integer erorCode) {
	this.erorCode = erorCode;
}
public String getErrorData() {
	return errorData;
}
public void setErrorDate(String errorData) {
	this.errorData = errorData;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
public ApiError() {
	super();
}
public ApiError(Integer erorCode, String errorData, Date date) {
	super();
	this.erorCode = erorCode;
	this.errorData = errorData;
	this.date = date;
}

}
