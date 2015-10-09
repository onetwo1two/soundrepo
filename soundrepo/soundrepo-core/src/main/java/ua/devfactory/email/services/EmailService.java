package ua.devfactory.email.services;

import java.util.Map;

public interface EmailService {

	public void sendEmail(String email, String messageKey, Map<String, Object> messageModel);
}
