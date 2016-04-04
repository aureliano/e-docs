package com.github.aureliano.edocs.service.bean;

import java.sql.SQLException;
import java.util.List;

import com.github.aureliano.edocs.common.exception.EDocsException;
import com.github.aureliano.edocs.common.exception.ServiceException;
import com.github.aureliano.edocs.common.persistence.DataPagination;
import com.github.aureliano.edocs.common.persistence.IPersistenceManager;
import com.github.aureliano.edocs.common.persistence.PersistenceService;
import com.github.aureliano.edocs.domain.entity.User;
import com.github.aureliano.edocs.secure.hash.PasswordHashGenerator;

public class UserServiceBean implements IServiceBean {

	private IPersistenceManager pm;
	
	public UserServiceBean() {
		this.pm = PersistenceService.instance().getPersistenceManager();
	}
	
	public User createDatabaseUser(String name, String password) {
		User user = new User()
			.withDbUser(true)
			.withName(name)
			.withPassword(password);
		
		return this.saveUser(user);
	}
	
	public User saveUser(User user) {
		String hash = PasswordHashGenerator.generateFromAppConfiguration(user.getPassword());
		user.withPassword(hash);
		User entity = this.executeInsideTransaction(user, true);
		
		return entity;
	}
	
	public boolean isValidCredential(String name, String password) {
		List<User> res = this.pm.search(
			new DataPagination<User>()
				.withEntity(new User().withName(name)));
		
		if (res.isEmpty()) {
			return false;
		}
		
		User user = res.get(0);
		String hash = PasswordHashGenerator.generateFromAppConfiguration(password);
		
		return user.getPassword().equals(hash);
	}
	
	public void deleteUser(User user) {
		this.executeInsideTransaction(user, false);
	}
	
	public User findUserById(Integer id) {
		return this.pm.find(User.class, id);
	}
	
	private User executeInsideTransaction(User user, boolean saveAction) {
		User entity = null;
		
		try {
			this.pm.getConnection().setAutoCommit(false);
			if (saveAction) {
				entity = this.pm.save(user);
			} else {
				this.pm.delete(user);
			}
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
		
		return entity;
	}
}