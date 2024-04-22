package com.springers.SERVICES;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class Service_EmailSender {
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendemail(String toEamil,String subject,String body) {
		SimpleMailMessage message=new SimpleMailMessage();
		
		message.setFrom("ayoub.zerdoum4@gmail.com");
		message.setTo(toEamil);
		message.setText(body);
		message.setSubject(subject);
		
		
		mailSender.send(message);
	}
}
