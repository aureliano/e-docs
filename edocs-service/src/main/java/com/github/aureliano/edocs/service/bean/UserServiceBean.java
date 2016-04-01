package com.github.aureliano.edocs.service.bean;

import com.github.aureliano.edocs.common.exception.ServiceException;
import com.github.aureliano.edocs.domain.entity.User;

public class UserServiceBean implements IServiceBean {

	public UserServiceBean() {}
	
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
		throw new ServiceException("Not implemented yet.");
	}
	
	public User findUserById(Integer id) {
		throw new ServiceException("Not implemented yet.");
	}
}