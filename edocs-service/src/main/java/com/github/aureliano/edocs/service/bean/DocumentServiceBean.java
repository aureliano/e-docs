package com.github.aureliano.edocs.service.bean;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import com.github.aureliano.edocs.common.exception.EDocsException;
import com.github.aureliano.edocs.common.exception.ServiceException;
import com.github.aureliano.edocs.common.persistence.DataPagination;
import com.github.aureliano.edocs.common.persistence.IPersistenceManager;
import com.github.aureliano.edocs.common.persistence.PersistenceService;
import com.github.aureliano.edocs.domain.dao.AttachmentDao;
import com.github.aureliano.edocs.domain.dao.DocumentDao;
import com.github.aureliano.edocs.domain.entity.Attachment;
import com.github.aureliano.edocs.domain.entity.Document;
import com.github.aureliano.edocs.domain.entity.User;
import com.github.aureliano.edocs.service.helper.ServiceHelper;

public class DocumentServiceBean implements IServiceBean {

	private static final Logger logger = Logger.getLogger(DocumentServiceBean.class.getName());
	
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
	
	public void deleteLogically(Document document) {
		Document entity = this.findDocumentById(document.getId());
		if (entity.getDeleted()) {
			logger.warning("Ignoring logical deletion of document " + entity.getId() + " because it is already logically deleted.");
			return;
		}
		
		entity.withDeleted(true);
		ServiceHelper.executeActionInsideTransaction(entity, true);
	}
	
	public void deletePhysically(Document document) {
		Document entity = this.findDocumentById(document.getId());
		if (!entity.getDeleted()) {
			throw new ServiceException("You cannot delete a document before a logical deletion.");
		}
		
		try {
			this.pm.getConnection().setAutoCommit(false);
			
			List<Attachment> attachments = new AttachmentServiceBean().findAttachmentsByDocument(entity);
			AttachmentDao attachmentDao = new AttachmentDao();
			for (Attachment attachment : attachments) {
				attachmentDao.delete(attachment);
			}
			
			new DocumentDao().delete(entity);
			this.pm.getConnection().commit();
		}  catch (EDocsException ex) {
			try {
				pm.getConnection().rollback();
			} catch (SQLException ex2) {
				throw new ServiceException(ex2);
			}
			
			logger.severe(ex.getMessage());
			logger.warning("Transaction rolled back!");
		} catch (SQLException ex) {
			throw new ServiceException(ex);
		}
	}
}