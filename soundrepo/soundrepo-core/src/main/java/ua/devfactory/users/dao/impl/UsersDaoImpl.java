package ua.devfactory.users.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import ua.devfactory.users.dao.UsersDao;
import ua.devfactory.users.dto.User;

public class UsersDaoImpl implements UsersDao {
	
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public User loadUserByEmail(String email) {
		String sql = "select id, nick, email, password_hash from users where email=?";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setNick(rs.getString("nick"));
				user.setPasswordHash(rs.getString("password_hash"));
				System.out.println("User was loaded " + user);
				return user;
			}
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public List<User> loadAllUsers() {
		List<User> res = new ArrayList<>();
		String sql = "select id, nick, email, password_hash from users";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setNick(rs.getString("nick"));
				user.setPasswordHash(rs.getString("password_hash"));
				res.add(user);
			}
			System.out.println("All users were loaded");
			return res;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void saveUser(User user) {
		User existingUser = loadUserByEmail(user.getEmail());
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			
			if (existingUser != null) {
				PreparedStatement ps = conn.prepareStatement("update users set nick=?, password_hash=? where email=?");
				ps.setString(1, user.getNick());
				ps.setString(2, user.getPasswordHash());
				ps.setString(3, user.getEmail());
				ps.executeUpdate();

				System.out.println("User was updated " + user);
			} else {
				PreparedStatement ps = conn.prepareStatement("insert into users (nick, email, password_hash) values (?,?,?)");
				ps.setString(1, user.getNick());
				ps.setString(2, user.getEmail());
				ps.setString(3, user.getPasswordHash());
				ps.executeUpdate();
				System.out.println("User was created " + user);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public User loadUserByNick(String nick) {
		String sql = "select id, nick, email, password_hash from users where nick=?";
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, nick);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setNick(rs.getString("nick"));
				user.setPasswordHash(rs.getString("password_hash"));
				System.out.println("User was loaded " + user);
				return user;
			}
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
