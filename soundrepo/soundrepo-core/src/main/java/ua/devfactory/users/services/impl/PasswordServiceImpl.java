package ua.devfactory.users.services.impl;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import ua.devfactory.users.services.PasswordService;


public class PasswordServiceImpl implements PasswordService {

    private static final int PASSWORD_LENGTH = 8;
    private static final String SALT = "salt";

    @Override
    public String generatePassword() {

	SecureRandom rnd = new SecureRandom();

	String password = "";
	char next = 0;
	int range = 10;

	for (int i = 0; i < PASSWORD_LENGTH; i++) {
	    switch (rnd.nextInt(3)) {
	    case 0: {next = '0'; range = 10;} break;
	    case 1: {next = 'a'; range = 26;} break;
	    case 2: {next = 'A'; range = 26;} break;
	    }
	    password += (char) ((rnd.nextInt(range)) + next);
	}
	// TODO 820
	System.out.println(password);
	return password;
    }

    @Override
    public String calculatePasswordHash(String rawPassword) {
	try {
	    String passwordAndSalt = SALT + rawPassword;

	    MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update(passwordAndSalt.getBytes(), 0, passwordAndSalt.length());
	    String md5Password = new BigInteger(1, md.digest()).toString(16);
	    
	 // TODO remove outputs of hash
	    System.out.println("MD5 hash: " + md5Password);
	    System.out.println("Java hash: " + passwordAndSalt.hashCode() + "");
	    
	    return md5Password;

	} catch (NoSuchAlgorithmException e) {
	    e.printStackTrace();
	    return null;
	}
    }

}
