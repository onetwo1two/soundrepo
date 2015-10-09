package ua.devfactory.users.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import ua.devfactory.email.services.EmailService;
import ua.devfactory.users.dao.UsersDao;
import ua.devfactory.users.dto.User;
import ua.devfactory.users.services.PasswordService;
import ua.devfactory.users.services.UsersService;

public class UsersServiceImpl implements UsersService{
	
	private Logger log = Logger.getLogger(getClass());
	private UsersDao usersDao;
	private PasswordService passwordService;
	private EmailService emailService;
	private String url;
	
	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}
	
	public void setPasswordService(PasswordService passwordService) {
		this.passwordService = passwordService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public User login(String email, String password) throws Exception {
		User user = usersDao.loadUserByEmail(email);
		if (user == null || !user.getPasswordHash().equals(passwordService.calculatePasswordHash(password))){
			//TODO сделать нормальные исключения
			throw new RuntimeException("Incorrect login or password");
		}
		return user;
	}

	@Override
	public User loadUserByEmail(String email) {
		//TODO 820
		log.info("Loading user by email " + email);
		System.out.println("Loading user by email " + email);
		return usersDao.loadUserByEmail(email);
	}

	@Override
	public User loadUserByNick(String nick) {
		//TODO 820
		log.info("Loading user by nickname " + nick);
		//System.out.println("Loading user by nickname " + nick);
		return usersDao.loadUserByNick(nick);
	}
	
	@Override
	public void resetPassword(String email) {
		//TODO generate password
		String newPassword = passwordService.generatePassword();
		User user = usersDao.loadUserByEmail(email);
		if (user != null){
			user.setPasswordHash(passwordService.calculatePasswordHash(newPassword));
			usersDao.saveUser(user);

			Map<String, Object> messageModel = new HashMap<>();
			messageModel.put("nick", user.getNick());
			messageModel.put("password", newPassword);
			messageModel.put("email", email);
			messageModel.put("url", url);

			emailService.sendEmail(email, "resetPassword", messageModel);
			//TODO 820
			System.out.println("Password was reseted for user " + user);
		}
	}

	@Override
	public void registerUser(String email, String nick) throws Exception{
		User user = usersDao.loadUserByEmail(email);
		if (user != null){
			throw new Exception("User already exists");
		}
		String password = passwordService.generatePassword();
		user = new User();
		user.setEmail(email);
		user.setNick(nick);
		user.setPasswordHash(passwordService.calculatePasswordHash(password));
		usersDao.saveUser(user);
		
		//TODO send email
		Map<String, Object> messageModel = new HashMap<>();
		messageModel.put("nick", user.getNick());
		messageModel.put("password", password);
		messageModel.put("email", email);
		messageModel.put("url", url);
		
		emailService.sendEmail(email, "registration", messageModel);
	}

	public void initUsers() {
		List<User> allUsers = usersDao.loadAllUsers();
		boolean adminFound = false;
		for (User user : allUsers) {
			if ("admin@admin".equals(user.getEmail())) {
				adminFound = true;
				break;
			}
		}
		if (!adminFound) {
			User admin = new User();
			admin.setEmail("admin@admin");
			admin.setNick("admin");
			admin.setPasswordHash(passwordService.calculatePasswordHash("admin"));
			usersDao.saveUser(admin);
			log.debug("Admin user was created with default settings");
		}
		log.debug("Users Service initialized");
	}

}
