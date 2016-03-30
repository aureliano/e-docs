package com.github.aureliano.edocs.common.persistence;

public interface IEntity<T> {

	public abstract Integer getId();
	
	public abstract T withId(Integer id);
}