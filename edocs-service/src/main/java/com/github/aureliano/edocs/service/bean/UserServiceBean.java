package com.github.aureliano.edocs.service.bean;

import java.sql.SQLException;

import com.github.aureliano.edocs.common.exception.EDocsException;
import com.github.aureliano.edocs.common.exception.ServiceException;
import com.github.aureliano.edocs.common.persistence.IPersistenceManager;
import com.github.aureliano.edocs.common.persistence.PersistenceService;
import com.github.aureliano.edocs.domain.entity.User;

public class UserServiceBean implements IServiceBean {

	private IPersistenceManager pm;
	
	public UserServiceBean() {
		this.pm = PersistenceService.instance().getPersistenceManager();
	}
	
	public User createDatabaseUser(String name, String password) {
		throw new ServiceException("Not implemented yet.");
	}
	
	public User saveUser(User user) {
		throw new ServiceException("Not implemented yet.");
	}
	
	public boolean isValidCredential(String name, String passwor) {
		throw new ServiceException("Not implemented yet.");
	}
	
	public void deleteUser(User user) {
		try {
			this.pm.getConnection().setAutoCommit(false);
			this.pm.delete(user);
			this.pm.getConnection().commit();
		} catch (EDocsException ex) {
			try {
				this.pm.getConnection().rollback();
			} catch (SQLException ex2) {
				throw new ServiceException(ex2);
			}
		} catch (SQLException ex) {
			throw new ServiceException(ex);
		}
	}
	
	public User findUserById(Integer id) {
		return this.pm.find(User.class, id);
	}
}