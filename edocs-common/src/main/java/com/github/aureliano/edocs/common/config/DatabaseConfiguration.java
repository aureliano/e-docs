package com.github.aureliano.edocs.common.config;

public class DatabaseConfiguration {

	private String user;
	private String password;
	
	public DatabaseConfiguration() {}

	public String getUser() {
		return user;
	}

	public DatabaseConfiguration withUser(String user) {
		this.user = user;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public DatabaseConfiguration withPassword(String password) {
		this.password = password;
		return this;
	}
}