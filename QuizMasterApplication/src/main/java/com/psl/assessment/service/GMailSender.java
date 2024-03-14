package com.psl.assessment.service;

import java.util.Properties;


import org.springframework.stereotype.Service;

import com.psl.assessment.model.Role;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.*;

@Service
public class GMailSender {
	
	public void sendEmail(String reciever,Role role,String password)
	{
		Properties properties = System.getProperties();
		String host = "smtp.gmail.com";
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");
	        
	        Session session = Session.getInstance(properties, new Authenticator() {
	            @Override
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication("shreeparnasinha1981855@gmail.com", "mgnb obkp xvqk jrsn");
	            }
	        });
	        
	        session.setDebug(true);
	        try {

	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress("shreeparnasinha1981855@gmail.com","Shreeparna Sinha"));
	            message.setRecipient(Message.RecipientType.TO, new InternetAddress(reciever));
	            message.setSubject("You Are A QuizMaster Now!!");
	            message.setText("You are registered as "+role.toString()+" with QuizMaster.\nUserName: "+reciever+"\nPassword: "+password);
	            Transport.send(message);
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}

}
