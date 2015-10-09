package ua.devfactory.users.services;

public interface PasswordService {

	public String generatePassword();
	
	public String calculatePasswordHash(String password);
	
}