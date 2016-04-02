package com.github.aureliano.edocs.common.persistence;

import java.sql.Connection;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.github.aureliano.edocs.common.message.ContextMessage;

public interface IPersistenceManager {

	public abstract Connection getConnection();
	
	public abstract <T> T save(T entity);
	
	public abstract <T> void delete(T entity);
	
	public abstract void delete(Class<? extends IEntity> type, Integer id);
	
	public abstract <T> T find(Class<T> type, Integer id);
	
	public abstract <T> List<T> search(DataPagination<T> dataPagination);
	
	public abstract <T> List<T> search(Class<T> type, String query);
	
	public abstract IPersistenceManager addContextMessage(ContextMessage message);
	
	public abstract void setContextMessages(Collection<ContextMessage> messages);
	
	public abstract Set<ContextMessage> getContextMessages();
	
	public abstract void clearContextMessages();
}