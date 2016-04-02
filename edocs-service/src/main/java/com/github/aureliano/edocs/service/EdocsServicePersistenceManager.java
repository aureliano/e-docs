package com.github.aureliano.edocs.service;

import java.sql.Connection;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.aureliano.edocs.common.message.ContextMessage;
import com.github.aureliano.edocs.common.persistence.DataPagination;
import com.github.aureliano.edocs.common.persistence.IDao;
import com.github.aureliano.edocs.common.persistence.IEntity;
import com.github.aureliano.edocs.common.persistence.IPersistenceManager;
import com.github.aureliano.edocs.common.persistence.PersistenceService;

public final class EdocsServicePersistenceManager implements IPersistenceManager {

	private Connection connection;
	private Set<ContextMessage> messages;
	
	public EdocsServicePersistenceManager() {
		this.messages = new HashSet<>();
	}
	
	@Override
	public Connection getConnection() {
		return this.connection;
	}
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public <T> T save(T entity) {
		return this.getDao(entity).save(entity);
	}

	@Override
	public <T> void delete(T entity) {
		this.getDao(entity).delete(entity);
	}

	@Override
	public void delete(Class<? extends IEntity> type, Integer id) {
		this.getDao(type).delete(id);
	}

	@Override
	public <T> T find(Class<T> type, Integer id) {
		return this.getDao(type).find(id);
	}

	@Override
	public <T> List<T> search(DataPagination<T> dataPagination) {
		return this.getDao(dataPagination.getEntity()).search(dataPagination);
	}

	@Override
	public <T> List<T> search(Class<T> type, String query) {
		return this.getDao(type).search(query);
	}

	@Override
	public IPersistenceManager addContextMessage(ContextMessage message) {
		this.messages.add(message);
		return this;
	}

	@Override
	public void setContextMessages(Collection<ContextMessage> messages) {
		this.messages = new HashSet<>(messages);
	}

	@Override
	public Set<ContextMessage> getContextMessages() {
		return this.messages;
	}

	@Override
	public void clearContextMessages() {
		this.messages.clear();
	}
	
	@SuppressWarnings("unchecked")
	private <T> IDao<T> getDao(T entity) {
		return (IDao<T>) this.getDao(entity.getClass());
	}
	
	@SuppressWarnings("unchecked")
	private <T> IDao<T> getDao(Class<T> ctype) {
		return (IDao<T>) PersistenceService.instance().createDao((Class<? extends IEntity>) ctype);
	}
}