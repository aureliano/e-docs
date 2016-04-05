package com.github.aureliano.edocs.service.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.aureliano.edocs.common.exception.ServiceException;
import com.github.aureliano.edocs.common.persistence.DataPagination;
import com.github.aureliano.edocs.common.persistence.PersistenceService;
import com.github.aureliano.edocs.domain.dao.DocumentDao;
import com.github.aureliano.edocs.domain.dao.UserDao;
import com.github.aureliano.edocs.domain.entity.Attachment;
import com.github.aureliano.edocs.domain.entity.Category;
import com.github.aureliano.edocs.domain.entity.Document;
import com.github.aureliano.edocs.domain.entity.User;
import com.github.aureliano.edocs.domain.helper.PersistenceHelper;
import com.github.aureliano.edocs.service.TestHelper;

public class DocumentServiceBeanTest {

	private DocumentServiceBean bean;
	
	public DocumentServiceBeanTest() {
		TestHelper.initiliazeTestEnvironment();
		this.bean = new DocumentServiceBean();
	}

	@Before
	public void beforeTest() throws SQLException {
		PersistenceHelper.instance().deleteAllRecords();;
	}
	
	@Test
	public void testFindDocumentById() {
		Document document1 = this.createDocumentSample();
		Document document2 = this.bean.findDocumentById(document1.getId());
		
		assertEquals(document1, document2);
	}
	
	@Test
	public void testFindAttachmentsByDocument() {
		User owner = this.createUserSample();
		int totalDocuments = 4;
		
		for (byte i = 0; i < totalDocuments; i++) {
			this.createDocumentSample(owner);
		}
		
		int totalOtherDocuments = 2;
		for (byte i = 0; i < totalOtherDocuments; i++) {
			this.createDocumentSample(null);
		}
		
		List<Document> res = this.bean.findDocumentsByOwner(owner);
		assertEquals(totalDocuments, res.size());
		
		res = new DocumentDao().search(
				new DataPagination<Document>().withEntity(new Document()));
		assertEquals((totalDocuments + totalOtherDocuments), res.size());
	}
	
	@Test
	public void testDeleteLogically() throws SQLException {
		Document document = this.createDocumentSample();
		assertFalse(document.getDeleted());
		
		Integer id = document.getId();
		document = this.bean.findDocumentById(id);
		assertFalse(document.getDeleted());
		
		this.bean.deleteLogically(document);
		document = this.bean.findDocumentById(id);
		assertTrue(document.getDeleted());
	}
	
	@Test(expected = ServiceException.class)
	public void testPhysicalDeletionError() {
		Document document = this.createDocumentSample(false);
		this.bean.deletePhysically(document);
	}
	
	@Test
	public void testDeletePhysically() throws SQLException {
		Document document = this.createDocumentSample(true);
		int totalAttachments = 5;
		
		for (byte i = 0; i < totalAttachments; i++) {
			this.createAttachment(document);
		}
		
		ResultSet rs = PersistenceHelper.instance().executeQuery("select count(id) from documents");
		rs.next();
		assertEquals(1, rs.getInt(1));
		
		rs = PersistenceHelper.instance().executeQuery("select count(id) from attachments");
		rs.next();
		assertEquals(totalAttachments, rs.getInt(1));
		
		this.bean.deletePhysically(document);
		rs = PersistenceHelper.instance().executeQuery("select count(id) from documents");
		rs.next();
		assertEquals(0, rs.getInt(1));
		
		rs = PersistenceHelper.instance().executeQuery("select count(id) from attachments");
		rs.next();
		assertEquals(0, rs.getInt(1));
	}
	
	@Test(expected = ServiceException.class)
	public void testCreateDocumentErrorIdNotNull() {
		Document document = new Document()
			.withId(1)
			.withAttachments(Arrays.asList(new Attachment()));
		this.bean.createDocument(document);
	}
	
	@Test(expected = ServiceException.class)
	public void testCreateDocumentErrorAttachmentsNull() {
		Document document = new Document()
			.withId(null)
			.withAttachments(null);
		this.bean.createDocument(document);
	}
	
	@Test(expected = ServiceException.class)
	public void testCreateDocumentErrorAttachmentsEmpty() {
		Document document = new Document()
			.withId(null);
		this.bean.createDocument(document);
	}
	
	@Test
	public void testCreateDocument() throws SQLException {
		Document document = new Document()
			.withCategory(Category.AGREEMENT)
			.withDescription("description")
			.withOwner(this.createUserSample())
			.withDeleted(false);
		
		int totalAttachments = 5;
		AttachmentServiceBean attachmentServiceBean = new AttachmentServiceBean();
		for (byte i = 0; i < totalAttachments; i++) {
			document.attach(attachmentServiceBean.createTemporaryAttachment("test-" + (i + 1)));
		}
		
		document = this.bean.createDocument(document);
		assertEquals(document, this.bean.findDocumentById(document.getId()));
		assertFalse(document.getDeleted());
		
		ResultSet rs = PersistenceHelper.instance().executeQuery("select count(id) from attachments");
		rs.next();
		assertEquals(totalAttachments, rs.getInt(1));
		
		List<Attachment> attachments = attachmentServiceBean.findAttachmentsByDocument(document);
		for (Attachment attachment : attachments) {
			assertFalse(attachment.getTemp());
		}
	}
	
	private Document createDocumentSample() {
		return this.createDocumentSample(this.createUserSample("mariae"));
	}
	
	private Document createDocumentSample(boolean deleted) {
		return this.createDocumentSample(this.createUserSample("mariae"), deleted);
	}
	
	private Document createDocumentSample(User owner) {
		return this.createDocumentSample(owner, false);
	}
	
	private Document createDocumentSample(User owner, boolean deleted) {
		Document d = new Document()
			.withCategory(Category.AGREEMENT)
			.withDescription("description")
			.withOwner(owner)
			.withDeleted(deleted)
			.withAttachments(Arrays.asList(new Attachment()));
		
		Document document = new DocumentDao().save(d);
		try {
			PersistenceService.instance().getPersistenceManager().getConnection().commit();
		} catch (SQLException ex) {
			Assert.fail(ex.getMessage());
		}
		return document;
	}
	
	private User createUserSample() {
		return this.createUserSample("maria");
	}
	
	private User createUserSample(String name) {
		User user = new User()
			.withDbUser(false)
			.withName("maria")
			.withPassword("test123");
		return new UserDao().save(user);
	}
	
	private Attachment createAttachment(Document document) {
		Attachment attachment = new Attachment()
			.withName("attachment-dummy")
			.withTemp(false)
			.withUploadTime(new Date())
			.withDocument(document);
		
		return new AttachmentServiceBean().saveAttachment(attachment);
	}
}