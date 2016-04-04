package com.github.aureliano.edocs.service.helper;

import java.sql.SQLException;

import com.github.aureliano.edocs.common.exception.EDocsException;
import com.github.aureliano.edocs.common.exception.ServiceException;
import com.github.aureliano.edocs.common.persistence.IPersistenceManager;
import com.github.aureliano.edocs.common.persistence.PersistenceService;

public final class ServiceHelper {

	private ServiceHelper() {}
	
	public static <T> T executeActionInsideTransaction(T bean, boolean saveAction) {
		IPersistenceManager pm = PersistenceService.instance().getPersistenceManager();
		T entity = null;
		
		try {
			pm.getConnection().setAutoCommit(false);
			if (saveAction) {
				entity = pm.save(bean);
			} else {
				pm.delete(bean);
			}
			pm.getConnection().commit();
		} catch (EDocsException ex) {
			try {
				pm.getConnection().rollback();
			} catch (SQLException ex2) {
				throw new ServiceException(ex2);
			}
		} catch (SQLException ex) {
			throw new ServiceException(ex);
		}
		
		return entity;
	}
}