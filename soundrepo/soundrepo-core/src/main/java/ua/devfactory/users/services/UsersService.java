package ua.devfactory.users.services;

import ua.devfactory.users.dto.User;

public interface UsersService {

		public User login (String email, String password) throws Exception;
		
		public User loadUserByEmail(String email);
		
		public User loadUserByNick(String nick);

		public void resetPassword(String email);
		
		public void registerUser(String email, String nick) throws Exception;
		
	
}
