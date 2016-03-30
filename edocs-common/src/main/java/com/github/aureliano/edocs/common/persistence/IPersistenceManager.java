package com.github.aureliano.edocs.common.persistence;

import java.util.List;

public interface IPersistenceManager {

	public abstract <T> T save(T entity);
	
	public abstract <T> void delete(T entity);
	
	public abstract void delete(Integer id);
	
	public abstract <T> T find(Integer id);
	
	public abstract <T> List<T> search(T entity);
	
	public abstract <T> List<T> search(String query);
}