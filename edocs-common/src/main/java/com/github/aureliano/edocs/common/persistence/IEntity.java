package com.github.aureliano.edocs.common.persistence;

public interface IEntity {

	public abstract Integer getId();
	
	public abstract IEntity withId(Integer id);
}