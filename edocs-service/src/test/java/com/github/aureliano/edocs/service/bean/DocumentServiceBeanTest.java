package com.github.aureliano.edocs.service.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.aureliano.edocs.common.exception.ServiceException;
import com.github.aureliano.edocs.common.persistence.DataPagination;
import com.github.aureliano.edocs.domain.dao.DocumentDao;
import com.github.aureliano.edocs.domain.entity.Attachment;
import com.github.aureliano.edocs.domain.entity.Category;
import com.github.aureliano.edocs.domain.entity.Document;
import com.github.aureliano.edocs.domain.entity.User;
import com.github.aureliano.edocs.domain.helper.PersistenceHelper;
import com.github.aureliano.edocs.service.IEmbeddedExecutor;
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
		Document document1 = TestHelper.createDocumentSample();
		Document document2 = this.bean.findDocumentById(document1.getId());
		
		assertEquals(document1, document2);
	}
	
	@Test
	public void testFindAttachmentsByDocument() {
		User owner = TestHelper.createUserSample();
		int totalDocuments = 4;
		
		for (byte i = 0; i < totalDocuments; i++) {
			TestHelper.createDocumentSample(owner);
		}
		
		int totalOtherDocuments = 2;
		for (byte i = 0; i < totalOtherDocuments; i++) {
			TestHelper.createDocumentSample(null);
		}
		
		List<Document> res = this.bean.findDocumentsByOwner(owner);
		assertEquals(totalDocuments, res.size());
		
		res = new DocumentDao().search(
				new DataPagination<Document>().withEntity(new Document()));
		assertEquals((totalDocuments + totalOtherDocuments), res.size());
	}
	
	@Test
	public void testDeleteLogically() throws SQLException {
		Document document = TestHelper.createDocumentSample();
		assertFalse(document.getDeleted());
		
		Integer id = document.getId();
		document = this.bean.findDocumentById(id);
		assertFalse(document.getDeleted());
		
		this.bean.deleteLogically(document);
		document = this.bean.findDocumentById(id);
		assertTrue(document.getDeleted());
	}
	
	@Test
	public void testPhysicalDeletionError() {
		IEmbeddedExecutor executor = new IEmbeddedExecutor() {
			public void execute() {
				Document document = TestHelper.createDocumentSample(false);
				bean.deletePhysically(document);
			}
		};
		
		TestHelper.checkExceptionThrown(executor, ServiceException.class, "You cannot delete a document before a logical deletion.");
	}
	
	@Test
	public void testDeletePhysically() throws SQLException {
		Document document = TestHelper.createDocumentSample(true);
		int totalAttachments = 5;
		
		for (byte i = 0; i < totalAttachments; i++) {
			TestHelper.createAttachment(document);
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
	
	@Test
	public void testCreateDocumentErrorIdNotNull() {
		IEmbeddedExecutor executor = new IEmbeddedExecutor() {
			public void execute() {
				Document document = new Document()
					.withId(1)
					.withAttachments(Arrays.asList(new Attachment()));
				bean.createDocument(document);
			}
		};
		
		TestHelper.checkExceptionThrown(executor, ServiceException.class, "Document id must be null.");
	}
	
	@Test
	public void testCreateDocumentErrorAttachmentsNull() {
		IEmbeddedExecutor executor = new IEmbeddedExecutor() {
			public void execute() {
				Document document = new Document()
					.withId(null)
					.withAttachments(null);
				bean.createDocument(document);
			}
		};
		
		TestHelper.checkExceptionThrown(executor, ServiceException.class, "Document must have at least one attachment.");
	}
	
	@Test
	public void testCreateDocumentErrorAttachmentsEmpty() {
		IEmbeddedExecutor executor = new IEmbeddedExecutor() {
			public void execute() {
				Document document = new Document()
					.withId(null);
				bean.createDocument(document);
			}
		};
		
		TestHelper.checkExceptionThrown(executor, ServiceException.class, "Document must have at least one attachment.");
	}
	
	@Test
	public void testCreateDocument() throws SQLException {
		Document document = new Document()
			.withCategory(Category.AGREEMENT)
			.withDescription("description")
			.withOwner(TestHelper.createUserSample())
			.withDeleted(false);
		
		int totalAttachments = 5;
		AttachmentServiceBean attachmentServiceBean = new AttachmentServiceBean();
		File file = TestHelper.getSampleFile();
		for (byte i = 0; i < totalAttachments; i++) {
			document.attach(attachmentServiceBean.createTemporaryAttachment(file));
		}
		
		document = this.bean.createDocument(document);
		assertEquals(document, this.bean.findDocumentById(document.getId()));
		assertFalse(document.getDeleted());
		
		ResultSet rs = PersistenceHelper.instance().executeQuery("select count(id) from attachments where document_fk = " + document.getId());
		rs.next();
		assertEquals(totalAttachments, rs.getInt(1));
		
		List<Attachment> attachments = attachmentServiceBean.findAttachmentsByDocument(document);
		for (Attachment attachment : attachments) {
			assertFalse(attachment.getTemp());
		}
	}
	
	@Test
	public void testSaveDocumentIdNull() {
		IEmbeddedExecutor executor = new IEmbeddedExecutor() {
			public void execute() {
				Document document = new Document()
					.withId(null);
				bean.saveDocument(document, Arrays.asList(new Attachment()), Arrays.asList(new Attachment()));
			}
		};
		
		TestHelper.checkExceptionThrown(executor, ServiceException.class, "Document id must not be null.");
	}
	
	@Test
	public void testSaveDocumentDeleted() {
		IEmbeddedExecutor executor = new IEmbeddedExecutor() {
			public void execute() {
				Document document = new Document()
					.withId(1)
					.withDeleted(true);
				bean.saveDocument(document, Arrays.asList(new Attachment()), Arrays.asList(new Attachment()));
			}
		};
		
		TestHelper.checkExceptionThrown(executor, ServiceException.class, "Cannot save a deleted document.");
	}
	
	public void testSaveDocumentAttachmentError() {
		IEmbeddedExecutor executor = new IEmbeddedExecutor() {
			public void execute() {
				Document document = TestHelper.prepareDocumentToSave(5);
				document = bean.createDocument(document);
				document.withAttachments(new AttachmentServiceBean().findAttachmentsByDocument(document));
				
				bean.saveDocument(document, new ArrayList<Attachment>(), document.getAttachments());
			}
		};
		
		TestHelper.checkExceptionThrown(executor, ServiceException.class, "Document must have at least one attachment.");
	}
	
	@Test
	public void testSaveDocument() {
		int totalAttachments = 5;
		Document document = TestHelper.prepareDocumentToSave(totalAttachments);
		document = this.bean.createDocument(document);
		
		AttachmentServiceBean attachmentServiceBean = new AttachmentServiceBean();
		document.withAttachments(attachmentServiceBean.findAttachmentsByDocument(document));
		
		int totalInserted = 2;
		List<Attachment> inserted = new ArrayList<>();
		File file = TestHelper.getSampleFile();
		for (byte i = 0; i < totalInserted; i++) {
			inserted.add(attachmentServiceBean.createTemporaryAttachment(file));
		}
		
		int totalDeleted = 4;
		List<Attachment> deleted = new ArrayList<>();
		for (byte i = 0; i < totalDeleted; i++) {
			deleted.add(document.getAttachments().get(i));
		}
		
		document.withCategory(Category.PAYCHECK).withDescription("Something else to test.");
		Document savedDocument = this.bean.saveDocument(document, inserted, deleted);
		
		assertEquals(document, savedDocument);
		
		savedDocument.withAttachments(attachmentServiceBean.findAttachmentsByDocument(savedDocument));
		int expectedSize = (totalAttachments + totalInserted - totalDeleted);
		assertEquals(expectedSize, savedDocument.getAttachments().size());
	}
	
	@Test
	public void testUndeleteLogically() throws SQLException {
		Document document = TestHelper.createDocumentSample(true);
		assertTrue(document.getDeleted());
		
		Integer id = document.getId();
		document = this.bean.findDocumentById(id);
		assertTrue(document.getDeleted());
		
		this.bean.undeleteLogically(document);
		document = this.bean.findDocumentById(id);
		assertFalse(document.getDeleted());
	}
}