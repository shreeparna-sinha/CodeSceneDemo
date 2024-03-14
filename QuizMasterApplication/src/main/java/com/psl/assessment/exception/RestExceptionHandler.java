package com.psl.assessment.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
@ExceptionHandler(UserAddException.class)
public ResponseEntity<ApiError> handleUserAddException(UserAddException e) {
	ApiError error = new ApiError(400,"No User Found",new Date());
	//response.setContentType("application/json");
	return new ResponseEntity<ApiError>(error,HttpStatus.BAD_REQUEST);
}
//public Map<String,String>handleInvalidArgument(MethodArgumentNotValidException ex){
//	Map<String ,String>errorMap = new HashMap<>();
//	ex.getBindingResult().getFieldErrors().forEach(error->{
//		errorMap.put(error.getField(),error.getDefaultMessage());
//	});
//	return errorMap;
//}
@ExceptionHandler(AutoSubmitException.class)
public ResponseEntity<ApiError> handleAutoSubmitException(AutoSubmitException e) {
	ApiError error = new ApiError(400,e.getMessage(),new Date());
	//response.setContentType("application/json");
	return new ResponseEntity<ApiError>(error,HttpStatus.BAD_REQUEST);
}
@ExceptionHandler(QuizInactiveException.class)
public ResponseEntity<ApiError> handleQuizInactiveException(QuizInactiveException e) {
	ApiError error = new ApiError(400,e.getMessage(),new Date());
	//response.setContentType("application/json");
	return new ResponseEntity<ApiError>(error,HttpStatus.BAD_REQUEST);
}
@ExceptionHandler(QuizAddException.class)
public ResponseEntity<ApiError> handleQuizAddException(QuizAddException e) {
	ApiError error = new ApiError(400,e.getMessage(),new Date());
	//response.setContentType("application/json");
	return new ResponseEntity<ApiError>(error,HttpStatus.BAD_REQUEST);
}
@ExceptionHandler(DuplicateQuestionException.class)
public ResponseEntity<ApiError> handleDuplicateQuestionException(DuplicateQuestionException e) {
	ApiError error = new ApiError(409,e.getMessage(),new Date());
	//response.setContentType("application/json");
	return new ResponseEntity<ApiError>(error,HttpStatus.BAD_REQUEST);
}
@ExceptionHandler(QuizNotSelectedException.class)
public ResponseEntity<ApiError> handleQuizNotSelectedException(QuizNotSelectedException e) {
	ApiError error = new ApiError(400,e.getMessage(),new Date());
	//response.setContentType("application/json");
	return new ResponseEntity<ApiError>(error,HttpStatus.BAD_REQUEST);
}
@ExceptionHandler(TimerUpException.class)
public ResponseEntity<ApiError> handleTimerUpException(TimerUpException e) {
	ApiError error = new ApiError(400,e.getMessage(),new Date());
	//response.setContentType("application/json");
	return new ResponseEntity<ApiError>(error,HttpStatus.BAD_REQUEST);
}
@ExceptionHandler(QuestionCountException.class)
public ResponseEntity<ApiError> handleQuestionCountException(QuestionCountException e) {
	ApiError error = new ApiError(400,e.getMessage(),new Date());
	//response.setContentType("application/json");
	return new ResponseEntity<ApiError>(error,HttpStatus.BAD_REQUEST);
}
@ExceptionHandler(MethodArgumentNotValidException.class)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public ResponseEntity<Map<String, String>> handleInvalidArgument(MethodArgumentNotValidException ex) {
    Map<String, String> errorMap = new HashMap<>();
    System.out.println("invalid argumnets");

    ex.getBindingResult().getFieldErrors().forEach(error -> {
        errorMap.put(error.getField(), error.getDefaultMessage());
    });

    return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
}
//	@ExceptionHandler(UserAddException.class)
//	public String handleUserAddException(UserAddException e) {
//		ApiError error = new ApiError(400,"No User Found",new Date());
//		//response.setContentType("application/json");
//		return e.getMessage();
////		return new ResponseEntity<ApiError>(HttpStatus.BAD_REQUEST);
//	}

}
