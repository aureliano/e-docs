package com.github.aureliano.edocs.domain.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.aureliano.edocs.common.exception.EDocsException;
import com.github.aureliano.edocs.common.persistence.DataPagination;
import com.github.aureliano.edocs.domain.entity.User;

public class UserDao extends AbstractDao<User> {

	private static final Logger logger = Logger.getLogger(UserDao.class.getName());
	
	public UserDao() {}

	@Override
	public User save(User entity) {
		return super.saveEntity(entity);
	}

	@Override
	public void delete(User entity) {
		this.delete(entity.getId());
	}

	@Override
	public void delete(Integer id) {
		String sql = "delete from users where id = ?";
		super.delete(sql, id);
	}

	@Override
	public User find(Integer id) {
		String sql = "select * from users where id = ?";
		return super.findEntity(sql, id);
	}

	@Override
	public List<User> search(DataPagination<User> dataPagination) {
		StringBuilder sql = new StringBuilder("select * from users where");
		User entity = dataPagination.getEntity();
		
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
		return super.searchEntities(query);
	}
	
	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	protected PreparedStatement createPreparedStatement(User user) {
		String sql = this.getSaveQuery(user);
		logger.fine("Save user SQL: " + sql);
		
		try {
			PreparedStatement ps = super.connection.prepareStatement(sql, new String[] {"ID"});
			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());
			ps.setObject(3, user.getDbUser());
			if (user.getId() != null) {
				ps.setInt(4, user.getId());
			}
			
			return ps;
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, ex.getMessage(), ex);
			throw new EDocsException(ex);
		}
	}

	@Override
	protected User fillEntity(ResultSet rs) throws SQLException {
		List<User> data = this.fillEntities(rs);
		return (data.isEmpty()) ? null : data.get(0);
	}

	@Override
	protected List<User> fillEntities(ResultSet rs) throws SQLException {
		List<User> data = new ArrayList<>();
		
		while (rs.next()) {
			User e = new User()
				.withId(rs.getInt("id"))
				.withName(rs.getString("name"))
				.withPassword(rs.getString("password"))
				.withDbUser(rs.getBoolean("db_user"));
			data.add(e);
		}
		
		logger.fine("Found " + data.size() + " entities.");
		return data;
	}
	
	private String getSaveQuery(User user) {
		if (user.getId() == null) {
			return "insert into users(name, password, db_user) values(?, ?, ?)";
		} else {
			return "update users set name = ?, password = ?, db_user = ? where id = ?";
		}
	}
}