package com.github.aureliano.edocs.common.persistence;

import java.sql.Connection;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.github.aureliano.edocs.common.message.ContextMessage;

public class CommonPersistenceManager implements IPersistenceManager {

	@Override
	public <T> T save(T entity) {
		return null;
	}

	@Override
	public <T> void delete(T entity) {}

	@Override
	public void delete(Class<? extends IEntity> type, Integer id) {}

	@Override
	public <T> T find(Class<T> type, Integer id) {
		return null;
	}

	@Override
	public <T> List<T> search(T entity) {
		return null;
	}

	@Override
	public <T> List<T> search(Class<T> type, String query) {
		return null;
	}

	@Override
	public Connection getConnection() {
		return null;
	}

	@Override
	public IPersistenceManager addContextMessage(ContextMessage message) {
		return null;
	}

	@Override
	public void setContextMessages(Collection<ContextMessage> messages) {}

	@Override
	public Set<ContextMessage> getContextMessages() {
		return null;
	}

	@Override
	public void clearContextMessages() {}
}