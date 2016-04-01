package com.github.aureliano.edocs.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.github.aureliano.edocs.common.exception.ServiceException;
import com.github.aureliano.edocs.domain.entity.Attachment;
import com.github.aureliano.edocs.domain.entity.Document;
import com.github.aureliano.edocs.domain.entity.User;
import com.github.aureliano.edocs.service.bean.AttachmentServiceBean;
import com.github.aureliano.edocs.service.bean.DocumentServiceBean;
import com.github.aureliano.edocs.service.bean.IServiceBean;
import com.github.aureliano.edocs.service.bean.UserServiceBean;

public class ServiceLookupTest {

	@Test(expected = ServiceException.class)
	public void testGetServiceNull() {
		ServiceLookup.getService(null);
	}
	
	@Test
	public void testGetService() {
		IServiceBean bean = null;
		
		bean = ServiceLookup.getService(User.class);
		assertTrue(bean instanceof UserServiceBean);
		
		bean = ServiceLookup.getService(Document.class);
		assertTrue(bean instanceof DocumentServiceBean);
		
		bean = ServiceLookup.getService(Attachment.class);
		assertTrue(bean instanceof AttachmentServiceBean);
	}
}