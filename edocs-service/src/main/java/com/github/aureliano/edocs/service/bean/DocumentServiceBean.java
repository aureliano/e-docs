package com.github.aureliano.edocs.service.bean;

import com.github.aureliano.edocs.common.persistence.IPersistenceManager;
import com.github.aureliano.edocs.common.persistence.PersistenceService;
import com.github.aureliano.edocs.domain.entity.Document;

public class DocumentServiceBean implements IServiceBean {

	private IPersistenceManager pm;
	
	public DocumentServiceBean() {
		this.pm = PersistenceService.instance().getPersistenceManager();
	}

	public Document findDocumentById(Integer id) {
		return this.pm.find(Document.class, id);
	}
}