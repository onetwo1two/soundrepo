package ua.devfactory.users.dao;

import java.util.List;

import ua.devfactory.users.dto.User;

public interface UsersDao {
	
	public void saveUser(User user);
	
	public User loadUserByEmail(String email);
	
	public User loadUserByNick(String nick);
	
	public List<User> loadAllUsers();
}
