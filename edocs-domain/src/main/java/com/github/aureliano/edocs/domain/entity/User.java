package com.github.aureliano.edocs.domain.entity;

import com.github.aureliano.edocs.annotation.validation.NotEmpty;
import com.github.aureliano.edocs.annotation.validation.Size;
import com.github.aureliano.edocs.common.persistence.IEntity;

public class User implements IEntity {

	private Integer id;
	private String name;
	private String password;
	private Boolean dbUser;
	
	public User() {}

	public Integer getId() {
		return this.id;
	}
	
	public User withId(Integer id) {
		this.id = id;
		return this;
	}
	
	@NotEmpty
	@Size(min = 3, max = 25)
	public String getName() {
		return name;
	}

	public User withName(String name) {
		this.name = name;
		return this;
	}

	@NotEmpty
	@Size(min = 3, max = 25)
	public String getPassword() {
		return password;
	}

	public User withPassword(String password) {
		this.password = password;
		return this;
	}
	
	public Boolean getDbUser() {
		return dbUser;
	}
	
	public User withDbUser(Boolean dbUser) {
		this.dbUser = dbUser;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
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