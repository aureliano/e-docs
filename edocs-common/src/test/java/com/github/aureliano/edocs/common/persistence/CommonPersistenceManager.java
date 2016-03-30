package com.github.aureliano.edocs.common.persistence;

import java.util.List;

public class CommonPersistenceManager implements IPersistenceManager {

	@Override
	public <T> T save(T entity) {
		return null;
	}

	@Override
	public <T> void delete(T entity) {}

	@Override
	public void delete(Integer id) {}

	@Override
	public <T> T find(Integer id) {
		return null;
	}

	@Override
	public <T> List<T> search(T entity) {
		return null;
	}

	@Override
	public <T> List<T> search(String query) {
		return null;
	}
}