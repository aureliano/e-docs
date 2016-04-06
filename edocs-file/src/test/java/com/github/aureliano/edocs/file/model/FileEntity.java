package com.github.aureliano.edocs.file.model;

import com.github.aureliano.edocs.common.persistence.IEntity;

public class FileEntity implements IEntity {

	private Integer id;
	
	@Override
	public Integer getId() {
		return this.id;
	}

	@Override
	public FileEntity withId(Integer id) {
		this.id = id;
		return this;
	}
}
