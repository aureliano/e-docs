package com.github.aureliano.edocs.service;

import com.github.aureliano.edocs.common.exception.ServiceException;
import com.github.aureliano.edocs.common.helper.StringHelper;
import com.github.aureliano.edocs.common.persistence.IEntity;
import com.github.aureliano.edocs.domain.entity.Attachment;
import com.github.aureliano.edocs.domain.entity.Document;
import com.github.aureliano.edocs.domain.entity.User;
import com.github.aureliano.edocs.service.bean.AttachmentServiceBean;
import com.github.aureliano.edocs.service.bean.DocumentServiceBean;
import com.github.aureliano.edocs.service.bean.IServiceBean;
import com.github.aureliano.edocs.service.bean.UserServiceBean;

public final class ServiceLookup {

	private ServiceLookup() {}
	
	public static <T extends IEntity> IServiceBean getService(Class<T> type) {
		if (User.class.equals(type)) {
			return new UserServiceBean();
		} else if (Document.class.equals(type)) {
			return new DocumentServiceBean();
		} else if (Attachment.class.equals(type)) {
			return new AttachmentServiceBean();
		} else {
			throw new ServiceException(
				"Service lookup could not find any service to entity " + StringHelper.toString(type));
		}
	}
}