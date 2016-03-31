package com.github.aureliano.edocs.domain.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.aureliano.edocs.common.exception.EDocsException;
import com.github.aureliano.edocs.common.persistence.PersistenceService;
import com.github.aureliano.edocs.domain.entity.User;

public class UserDao extends AbstractDao<User> {

	private static final Logger logger = Logger.getLogger(UserDao.class.getName());
	
	private Connection connection;
	
	public UserDao() {
		this.connection = PersistenceService.instance().getPersistenceManager().getConnection();
	}

	@Override
	public User save(User entity) {
		super.validateConfiguration(entity);
		User user = null;
		
		try(PreparedStatement ps = this.createPreparedStatement(entity)) {
			int res = ps.executeUpdate();
			logger.fine("Number of records affected by this action " + res);
			
			ResultSet rs = ps.getGeneratedKeys();
			if ((rs != null) && (rs.next())) {
				user = this.find(rs.getInt(1));
			}
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, ex.getMessage(), ex);
			throw new EDocsException(ex);
		}
		
		return user;
	}

	@Override
	public void delete(User entity) {
		this.delete(entity.getId());
	}

	@Override
	public void delete(Integer id) {
		String sql = "delete from users where id = ?";
		logger.fine("Delete user SQL: " + sql);
		
		try(PreparedStatement ps = this.connection.prepareStatement(sql)) {
			ps.setInt(1, id);
			
			int res = ps.executeUpdate();
			logger.fine("Number of records affected by this action " + res);
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, ex.getMessage(), ex);
			throw new EDocsException(ex);
		}
	}

	@Override
	public User find(Integer id) {
		String sql = "select * from users where id = ?";
		logger.fine("Find user with id " + id);
		logger.fine("Find user SQL: " + sql);
		User user = null;
		
		try(PreparedStatement ps = this.connection.prepareStatement(sql)) {
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			user = this.fillUser(rs);
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, ex.getMessage(), ex);
			throw new EDocsException(ex);
		}
		
		return user;
	}

	@Override
	public List<User> search(User entity) {
		StringBuilder sql = new StringBuilder("select * from users where");
		
		if (entity.getId() != null) {
			sql.append(" id = " + entity.getId());
			return this.search(sql.toString());
		}
		
		if (entity.getName() != null) {
			sql.append(" name = " + entity.getName());
		}
		
		return this.search(sql.toString());
	}

	@Override
	public List<User> search(String query) {
		logger.fine("Find user SQL: " + query);
		
		try(PreparedStatement ps = this.connection.prepareStatement(query)) {
			ResultSet rs = ps.executeQuery();
			
			return this.fillUsers(rs);
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, ex.getMessage(), ex);
			throw new EDocsException(ex);
		}
	}
	
	private PreparedStatement createPreparedStatement(User user) {
		String sql = this.getSaveQuery(user);
		logger.fine("Save user SQL: " + sql);
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(sql, new String[] {"ID"});
			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());
			if (user.getId() != null) {
				ps.setInt(3, user.getId());
			}
			
			return ps;
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, ex.getMessage(), ex);
			throw new EDocsException(ex);
		}
	}
	
	private String getSaveQuery(User user) {
		if (user.getId() == null) {
			return "insert into users(name, password) values(?, ?)";
		} else {
			return "update users set name = ?, password = ? where id = ?";
		}
	}
	
	private User fillUser(ResultSet rs) throws SQLException {
		List<User> data = this.fillUsers(rs);
		return (data.isEmpty()) ? null : data.get(0);
	}
	
	private List<User> fillUsers(ResultSet rs) throws SQLException {
		List<User> data = new ArrayList<>();
		
		while (rs.next()) {
			User e = new User()
				.withId(rs.getInt("id"))
				.withName(rs.getString("name"))
				.withPassword(rs.getString("password"));
			data.add(e);
		}
		
		logger.fine("Found " + data.size() + " entities.");
		return data;
	}
}