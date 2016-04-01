package com.github.aureliano.edocs.service;

import com.github.aureliano.edocs.common.exception.EDocsException;
import com.github.aureliano.edocs.common.persistence.IEntity;
import com.github.aureliano.edocs.service.bean.IServiceBean;

public final class ServiceLookup {

	private ServiceLookup() {}
	
	public static <T extends IEntity> IServiceBean getService(Class<T> type) {
		throw new EDocsException("Not implemented yet");
	}
}