package ua.devfactory.users.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ua.devfactory.users.dto.User;
import ua.devfactory.users.services.UsersService;

@Controller
public class UsersController {
	private UsersService usersService;
	
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}
	
	@RequestMapping("login")
	public ModelAndView login(@RequestParam(value = "email", required = false) String email, 
							  @RequestParam(value = "password", required = false) String password,
							  HttpSession session){
		if (email == null || password == null) {
			return new ModelAndView("login");
		}
		try {
			User user = usersService.login(email, password);
			//TODO820
			System.out.println("User " + user + " logged in");
			session.setAttribute("user", user);
			return new ModelAndView("redirect:explore.do");
		} catch (Exception e) {
			return new ModelAndView("login", "errors", Arrays.asList(e.getMessage()));
		}
	}
	
	@RequestMapping("logout")
	public ModelAndView logout(HttpSession session) {
		session.setAttribute("user", null);
		return new ModelAndView("redirect:login.do");
	}
	
	@RequestMapping("register")
	public ModelAndView register(@RequestParam(value = "nick", required = false) String nick,
			@RequestParam(value = "email", required = false) String email,
			HttpSession session) {
		if (nick == null || email == null) {
			return new ModelAndView("register");
		}
		//TODO spring validation
		try {
			List<String> errors = new ArrayList<>();
			if ("admin".equals(nick)) {
				errors.add("Name cannot be used: " + nick);
			}
			if (email.length() == 0) {
				errors.add("Email should not be empty");
			}
			if (email.length() > 20){
				errors.add("Your address should have between 3 and 20 characters");
			}
			if (nick.length() == 0) {
				errors.add("Nick should not be emtpy");
			}
			if (nick.length() > 20) {
				errors.add("Your nick shouldn't have more than 20 characters");
			}
			User user = usersService.loadUserByEmail(email);
			if (user != null) {
				errors.add("Email is already in use. Please enter another email or use this one on login page.");
			}
			user = usersService.loadUserByNick(nick);
			if (user != null){
				errors.add("Nick is already in use.");
			}
			if (errors.size() != 0) {
				return new ModelAndView("register", "errors", errors);
			}

			usersService.registerUser(email, nick);
			return new ModelAndView("register_ok");
		} catch (Exception e) {
			return new ModelAndView("register", "errors", Arrays.asList(e.getMessage()));
		}
	}
	
	@RequestMapping("reset_password")
	public ModelAndView resetPassword(@RequestParam(value = "email", required = false) String email) {
		if (email == null) {
			return new ModelAndView("reset_password");
		}
		try {
			List<String> errors = new ArrayList<>();
			if (email.length() == 0) {
				errors.add("Please enter non-empty email");
			} else {
				User user = usersService.loadUserByEmail(email);
				if (user == null) {
					errors.add("We cannot find your email. Please re-enter or try to register as new user.");
				}
			}
			if (errors.size() != 0) {
				return new ModelAndView("reset_password", "errors", errors);
			}
			System.out.println("Password was reset for " + email);
			usersService.resetPassword(email);
			return new ModelAndView("reset_password_ok");
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView("reset_password", "errors", Arrays.asList(e.getMessage()));
		}
	}
}
