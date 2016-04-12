package com.github.aureliano.edocs.domain.helper;

import com.github.aureliano.edocs.common.persistence.PersistenceService;
import com.github.aureliano.edocs.domain.dao.AttachmentDao;
import com.github.aureliano.edocs.domain.dao.DocumentDao;
import com.github.aureliano.edocs.domain.dao.UserDao;
import com.github.aureliano.edocs.domain.entity.Attachment;
import com.github.aureliano.edocs.domain.entity.Document;
import com.github.aureliano.edocs.domain.entity.User;

public final class EntityHelper {

	private EntityHelper() {}
	
	public static void mapEntities() {
		PersistenceService.instance()
			.mapEntity(User.class, UserDao.class)
			.mapEntity(Document.class, DocumentDao.class)
			.mapEntity(Attachment.class, AttachmentDao.class);
	}
}