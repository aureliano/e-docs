package com.github.aureliano.edocs.common.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.github.aureliano.edocs.common.exception.EDocsException;
import com.github.aureliano.edocs.common.helper.ReflectionHelper;

public final class PersistenceService {

	private static final Logger logger = Logger.getLogger(PersistenceService.class.getName());
	private static PersistenceService instance;
	
	private IPersistenceManager persistenceManager;
	private Map<Class<? extends IEntity>, Class<? extends IDao<?>>> entityMap;
	
	private PersistenceService() {
		this.entityMap = new HashMap<>();
	}
	
	public static final PersistenceService instance() {
		if (instance == null) {
			instance = new PersistenceService();
		}
		
		return instance;
	}
	
	public void registerPersistenceManager(IPersistenceManager pm) {
		this.persistenceManager = pm;
	}
	
	public IPersistenceManager getPersistenceManager() {
		return this.persistenceManager;
	}
	
	public PersistenceService mapEntity(Class<? extends IEntity> entity, Class<? extends IDao<?>> dao) {
		this.entityMap.put(entity, dao);
		logger.info("Mapped entity " + entity.getName() + " and with DAO " + dao.getName());
		
		return this;
	}
	
	public IDao<?> createDao(Class<? extends IEntity> entity) {
		Class<? extends IDao<?>> dao = this.entityMap.get(entity);
		if (dao == null) {
			throw new EDocsException("Entity " + entity.getName() + " is not mapped.");
		}
		
		return (IDao<?>) ReflectionHelper.newInstance(dao);
	}
}