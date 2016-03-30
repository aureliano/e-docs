package com.github.aureliano.edocs.domain.dao;

import java.util.List;

public interface IDao<T> {

	public abstract T save(T entity);
	
	public abstract void delete(T entity);
	
	public abstract void delete(Integer id);
	
	public abstract T find(Integer id);
	
	public abstract List<T> search(T entity);
	
	public abstract List<T> search(String query);
}