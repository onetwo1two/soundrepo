package ua.devfactory.email.services.impl;

import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;



import org.springframework.ui.velocity.VelocityEngineUtils;

import ua.devfactory.email.services.EmailService;

public class EmailServiceImpl implements EmailService {
	
	private MailSender mailSender;
	private VelocityEngine velocityEngine;
	
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}


	@Override
	public void sendEmail(String email, String messageKey, Map<String, Object> messageModel) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("info@soundrepo.ua");
		message.setTo(email);
		String text = VelocityEngineUtils.mergeTemplateIntoString(
				velocityEngine, "templates/" + messageKey + ".text.txt", "UTF-8", messageModel);
		message.setText(text);
		String subject = VelocityEngineUtils.mergeTemplateIntoString(
				velocityEngine, "templates/" + messageKey + ".subject.txt", "UTF-8", messageModel);
		message.setSubject(subject);
		
		try {
			mailSender.send(message);
		} catch (MailException ex) {
			ex.printStackTrace();
			//TODO 820 + think about catch exception in userService
			System.out.println("Cannot send email message");
		}
		
	}
	
}
