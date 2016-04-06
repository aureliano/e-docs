package com.github.aureliano.edocs.service.bean;

import java.io.File;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.github.aureliano.edocs.common.config.AppConfiguration;
import com.github.aureliano.edocs.common.config.ConfigurationSingleton;
import com.github.aureliano.edocs.common.exception.EDocsException;
import com.github.aureliano.edocs.common.exception.ServiceException;
import com.github.aureliano.edocs.common.persistence.DataPagination;
import com.github.aureliano.edocs.common.persistence.IPersistenceManager;
import com.github.aureliano.edocs.common.persistence.PersistenceService;
import com.github.aureliano.edocs.domain.entity.Attachment;
import com.github.aureliano.edocs.domain.entity.Document;
import com.github.aureliano.edocs.file.repository.IRepository;
import com.github.aureliano.edocs.file.repository.RepositoryFactory;
import com.github.aureliano.edocs.file.repository.RepositoryType;
import com.github.aureliano.edocs.service.helper.ServiceHelper;

public class AttachmentServiceBean implements IServiceBean {

	private static final Logger logger = Logger.getLogger(AttachmentServiceBean.class.getName());
	
	private IPersistenceManager pm;
	private IRepository repository;
	
	public AttachmentServiceBean() {
		this.pm = PersistenceService.instance().getPersistenceManager();
		
		AppConfiguration configuration = ConfigurationSingleton.instance().getAppConfiguration();
		String repositoryType = configuration.getFileRepositoryConfiguration().getRepositoryType();
		this.repository = RepositoryFactory.createRepository(RepositoryType.valueOf(repositoryType));
	}
	
	public Attachment createTemporaryAttachment(File file) {
		Attachment attachment = new Attachment()
			.withName(file.getName())
			.withTemp(true)
			.withUploadTime(new Date());
		
		Attachment entity = null;
		
		try {
			this.pm.getConnection().setAutoCommit(false);
			
			entity = pm.save(attachment);
			this.repository.writeToLimbo(file, entity);
			
			pm.getConnection().commit();
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
	
	public Attachment saveAttachment(Attachment attachment) {
		if ((attachment.getDocument() == null) || (attachment.getDocument().getId() == null)) {
			throw new ServiceException("Attachment must have a relation with a document.");
		}
		
		attachment.withTemp(false);
		Attachment entity = null;
		
		try {
			this.pm.getConnection().setAutoCommit(false);
			
			entity = pm.save(attachment);
			this.repository.saveFile(entity);
			
			pm.getConnection().commit();
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
	
	public void deleteTempAttachment(Attachment attachment) {
		if (!Boolean.TRUE.equals(attachment.getTemp())) {
			throw new ServiceException("You cannot delete a non temporary file.");
		}
		ServiceHelper.executeActionInsideTransaction(attachment, false);
	}
	
	public Attachment findAttachmentById(Integer id) {
		return this.pm.find(Attachment.class, id);
	}
	
	public List<Attachment> findAttachmentsByDocument(Document document) {
		Attachment attachment = new Attachment().withDocument(document);
		return this.pm.search(new DataPagination<Attachment>().withEntity(attachment));
	}
}