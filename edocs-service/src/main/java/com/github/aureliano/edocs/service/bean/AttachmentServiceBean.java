package com.github.aureliano.edocs.service.bean;

import java.util.Date;
import java.util.List;

import com.github.aureliano.edocs.common.exception.ServiceException;
import com.github.aureliano.edocs.common.persistence.DataPagination;
import com.github.aureliano.edocs.common.persistence.IPersistenceManager;
import com.github.aureliano.edocs.common.persistence.PersistenceService;
import com.github.aureliano.edocs.domain.entity.Attachment;
import com.github.aureliano.edocs.domain.entity.Document;
import com.github.aureliano.edocs.service.helper.ServiceHelper;

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
		
		return ServiceHelper.executeActionInsideTransaction(attachment, true);
	}
	
	public Attachment saveAttachment(Attachment attachment) {
		if ((attachment.getDocument() == null) || (attachment.getDocument().getId() == null)) {
			throw new ServiceException("Attachment must have a relation with a document.");
		}
		
		attachment.withTemp(false);
		
		return ServiceHelper.executeActionInsideTransaction(attachment, true);
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