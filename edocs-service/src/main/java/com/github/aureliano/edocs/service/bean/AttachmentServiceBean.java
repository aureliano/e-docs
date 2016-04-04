package com.github.aureliano.edocs.service.bean;

import java.sql.SQLException;
import java.util.Date;

import com.github.aureliano.edocs.common.exception.EDocsException;
import com.github.aureliano.edocs.common.exception.ServiceException;
import com.github.aureliano.edocs.common.persistence.IPersistenceManager;
import com.github.aureliano.edocs.common.persistence.PersistenceService;
import com.github.aureliano.edocs.domain.entity.Attachment;

public class AttachmentServiceBean implements IServiceBean {

	private IPersistenceManager pm;
	
	public AttachmentServiceBean() {
		this.pm = PersistenceService.instance().getPersistenceManager();
	}
	
	public Attachment createTemporaryAttachment(String name) {
		Attachment attachment = new Attachment()
			.withName(name)
			.withTemp(true)
			.withUploadTime(new Date());
		
		return this.executeInsideTransaction(attachment, true);
	}

	private Attachment executeInsideTransaction(Attachment attachment, boolean saveAction) {
		Attachment entity = null;
		
		try {
			this.pm.getConnection().setAutoCommit(false);
			if (saveAction) {
				entity = this.pm.save(attachment);
			} else {
				this.pm.delete(attachment);
			}
			this.pm.getConnection().commit();
		} catch (EDocsException ex) {
			try {
				this.pm.getConnection().rollback();
			} catch (SQLException ex2) {
				throw new ServiceException(ex2);
			}
		} catch (SQLException ex) {
			throw new ServiceException(ex);
		}
		
		return entity;
	}
}