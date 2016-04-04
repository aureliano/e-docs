package com.github.aureliano.edocs.service.bean;

import java.util.List;

import com.github.aureliano.edocs.common.persistence.DataPagination;
import com.github.aureliano.edocs.common.persistence.IPersistenceManager;
import com.github.aureliano.edocs.common.persistence.PersistenceService;
import com.github.aureliano.edocs.domain.entity.Document;
import com.github.aureliano.edocs.domain.entity.User;

public class DocumentServiceBean implements IServiceBean {

	private IPersistenceManager pm;
	
	public DocumentServiceBean() {
		this.pm = PersistenceService.instance().getPersistenceManager();
	}

	public Document findDocumentById(Integer id) {
		return this.pm.find(Document.class, id);
	}
	
	public List<Document> findDocumentsByOwner(User owner) {
		Document document = new Document().withOwner(owner);
		return this.pm.search(new DataPagination<Document>().withEntity(document));
	}
}