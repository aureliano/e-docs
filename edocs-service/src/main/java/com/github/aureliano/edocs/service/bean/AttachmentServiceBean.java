package com.github.aureliano.edocs.service.bean;

import java.util.Date;

import com.github.aureliano.edocs.common.exception.ServiceException;
import com.github.aureliano.edocs.common.persistence.IPersistenceManager;
import com.github.aureliano.edocs.common.persistence.PersistenceService;
import com.github.aureliano.edocs.domain.entity.Attachment;
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
}