package com.github.aureliano.edocs.domain.entity;

public class User {

	private String name;
	private String password;
	
	public User() {}

	public String getName() {
		return name;
	}

	public User withName(String name) {
		this.name = name;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public User withPassword(String password) {
		this.password = password;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}
}