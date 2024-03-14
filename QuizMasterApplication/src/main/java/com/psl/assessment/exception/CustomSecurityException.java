package com.psl.assessment.exception;
//
//import java.nio.file.AccessDeniedException;
//
//import org.springframework.beans.factory.parsing.Problem;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ProblemDetail;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class CustomSecurityException {
//	@ExceptionHandler(Exception.class)
// public ResponseEntity<ProblemDetail> handleSecurityException(Exception ex) {
//		ProblemDetail errorDetail = null;
//	 if(ex instanceof BadCredentialsException) {
//		 errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401),ex.getLocalizedMessage());
//		 errorDetail.setProperty("access denied reason", "Authentication Faliure");
//	 }
//	 if(ex instanceof AccessDeniedException) {
//		 errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),ex.getLocalizedMessage());
//		 errorDetail.setProperty("access denied reason", "Not Authorized");
//	 }
//	 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetail);
// }
//}
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
 
@Component
//@RestControllerAdvice
public class CustomSecurityException implements AuthenticationEntryPoint, AccessDeniedHandler {
 
    private final ObjectMapper objectMapper = new ObjectMapper();
 
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "Unauthorized");
        errorDetails.put("message","Authentication Required, Please provide Valid Credentials");
        System.out.println("entrypoint");
        OutputStream out = response.getOutputStream();
        objectMapper.writeValue(out, errorDetails);
        out.flush();
    }
 
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("error", "Forbidden");
        errorDetails.put("message","You do not Have Permission to access this resource");
        System.out.println("aceessdenied");
        OutputStream out = response.getOutputStream();
        objectMapper.writeValue(out, errorDetails);
        out.flush();
    }
}
