package com.github.aureliano.edocs.service.bean;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import com.github.aureliano.edocs.file.repository.IRepository;
import com.github.aureliano.edocs.service.helper.ServiceHelper;

public class DocumentServiceBean implements IServiceBean {

	private static final Logger logger = Logger.getLogger(DocumentServiceBean.class.getName());
	
	private IPersistenceManager pm;
	private IRepository repository;
	
	public DocumentServiceBean() {
		this.pm = PersistenceService.instance().getPersistenceManager();
		this.repository = ServiceHelper.createRepository();
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
				this.repository.deleteFile(attachment);
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
	
	public Document createDocument(Document document) {
		Document entity = null;
		this.validateDocumentCreateAction(document);
		
		try {
			this.pm.getConnection().setAutoCommit(false);
			
			entity = new DocumentDao().save(document.withDeleted(false));
			
			AttachmentDao attachmentDao = new AttachmentDao();
			for (Attachment attachment : document.getAttachments()) {
				attachmentDao.save(attachment.withDocument(entity).withTemp(false));
			}
			
			this.pm.getConnection().commit();
		} catch (EDocsException ex) {
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
		
		return entity;
	}
	
	public Document saveDocument(Document document, List<Attachment> inserted, List<Attachment> deleted) {
		Document entity = null;
		this.validateDocumentSaveAction(document, inserted.size(), deleted.size());
		
		try {
			this.pm.getConnection().setAutoCommit(false);
			
			AttachmentDao attachmentDao = new AttachmentDao();
			for (Attachment attachment : deleted) {
				attachmentDao.delete(attachment);
			}
			
			for (Attachment attachment : inserted) {
				attachmentDao.save(attachment.withDocument(document).withTemp(false));
			}
			
			entity = new DocumentDao().save(document);
			this.pm.getConnection().commit();
		} catch (EDocsException ex) {
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
		
		return entity;
	}
	
	public void undeleteLogically(Document document) {
		Document entity = this.findDocumentById(document.getId());
		if (!entity.getDeleted()) {
			logger.warning("Ignoring logical undeletion of document " + entity.getId() + " because it is not delete.");
			return;
		}
		
		entity.withDeleted(false);
		ServiceHelper.executeActionInsideTransaction(entity, true);
	}
	
	private void validateDocumentCreateAction(Document document) {
		if (document.getId() != null) {
			throw new ServiceException("Document id must be null.");
		} else if ((document.getAttachments() == null) || (document.getAttachments().isEmpty())) {
			throw new ServiceException("Document must have at least one attachment.");
		}
	}
	
	private void validateDocumentSaveAction(Document document, int insertions, int deletions) {
		if (document.getId() == null) {
			throw new ServiceException("Document id must not be null.");
		} else if (document.getDeleted()) {
			throw new ServiceException("Cannot save a deleted document.");
		}
		
		int totalAttachments = this.getAttachmentsCount(document.getId());
		
		if ((totalAttachments + insertions) <= deletions) {
			throw new ServiceException("Document must have at least one attachment.");
		}
	}
	
	private int getAttachmentsCount(Integer documentId) {
		String sql = "select count(id) from attachments where document_fk = ?";
		try {
			PreparedStatement ps = pm.getConnection().prepareStatement(sql);
			ps.setInt(1, documentId);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			return rs.getInt(1);
		} catch (SQLException ex) {
			logger.severe("Validation failed when counting document's attachments. " + ex.getMessage());
			throw new ServiceException(ex);
		}
	}
}