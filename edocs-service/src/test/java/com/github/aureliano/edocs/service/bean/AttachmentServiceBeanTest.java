package com.github.aureliano.edocs.service.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.aureliano.edocs.common.exception.ServiceException;
import com.github.aureliano.edocs.common.persistence.DataPagination;
import com.github.aureliano.edocs.domain.dao.AttachmentDao;
import com.github.aureliano.edocs.domain.entity.Attachment;
import com.github.aureliano.edocs.domain.entity.Document;
import com.github.aureliano.edocs.domain.entity.User;
import com.github.aureliano.edocs.domain.helper.PersistenceHelper;
import com.github.aureliano.edocs.service.IEmbeddedExecutor;
import com.github.aureliano.edocs.service.TestHelper;

public class AttachmentServiceBeanTest {

	private AttachmentServiceBean bean;
	
	public AttachmentServiceBeanTest() {
		TestHelper.initiliazeTestEnvironment();
		this.bean = new AttachmentServiceBean();
	}

	@Before
	public void beforeTest() throws SQLException {
		PersistenceHelper.instance().deleteAllRecords();;
	}
	
	@Test
	public void testCreateTemporaryAttachment() {
		File sampleFile = TestHelper.getSampleFile();
		Attachment attachment = this.bean.createTemporaryAttachment(sampleFile);
		
		assertNotNull(attachment.getId());
		assertEquals(sampleFile.getName(), attachment.getName());
		assertEquals(TestHelper.getToday(), TestHelper.removeTime(attachment.getUploadTime()));
		assertTrue(attachment.getTemp());
	}
	
	@Test
	public void testSaveAttachment() {
		Attachment attachment = new Attachment()
			.withName("save-file")
			.withTemp(false)
			.withUploadTime(new Date())
			.withDocument(TestHelper.createDocumentSample());
		
		Attachment attachment1 = this.bean.saveAttachment(attachment);
		assertNotNull(attachment1.getId());
		assertEquals(attachment.getName(), attachment1.getName());
		assertEquals(attachment.getDocument().getId(), attachment1.getDocument().getId());
		assertEquals(
			TestHelper.removeTime(attachment.getUploadTime()),
			TestHelper.removeTime(attachment1.getUploadTime())
		);
	}
	
	@Test
	public void testDeleteNonTemporaryAttachment() {
		IEmbeddedExecutor executor = new IEmbeddedExecutor() {
			public void execute() {
				Attachment attachment = new Attachment()
					.withName("delete-non-temp-file")
					.withTemp(false)
					.withUploadTime(new Date())
					.withDocument(TestHelper.createDocumentSample());
				
				Attachment attachment1 = bean.saveAttachment(attachment);
				bean.deleteTempAttachment(attachment1);
			}
		};
	
		TestHelper.checkExceptionThrown(executor, ServiceException.class, "You cannot delete a non temporary file.");
	}
	
	@Test
	public void testDeleteTempAttachment() throws SQLException {
		File sampleFile = TestHelper.getSampleFile();
		Attachment attachment = this.bean.createTemporaryAttachment(sampleFile);
		
		ResultSet rs = PersistenceHelper.instance().executeQuery("select count(id) from attachments where id = " + attachment.getId());
		rs.next();
		assertEquals(1, rs.getInt(1));
		rs.close();
		
		this.bean.deleteTempAttachment(attachment);
		
		rs = PersistenceHelper.instance().executeQuery("select count(id) from attachments where id = " + attachment.getId());
		rs.next();
		assertEquals(0, rs.getInt(1));
		rs.close();
	}
	
	@Test
	public void testFindAttachmentById() {
		File sampleFile = TestHelper.getSampleFile();
		Attachment attachment1 = this.bean.createTemporaryAttachment(sampleFile);
		Attachment attachment2 = this.bean.findAttachmentById(attachment1.getId());
		
		assertEquals(attachment1, attachment2);
	}
	
	@Test
	public void testFindAttachmentsByDocument() {
		Document document = TestHelper.createDocumentSample();
		int totalAttachments = 4;
		
		for (byte i = 0; i < totalAttachments; i++) {
			TestHelper.createAttachment(document);
		}
		
		int totalOtherAttachments = 2;
		User owner = TestHelper.createUserSample("user-test");
		for (byte i = 0; i < totalOtherAttachments; i++) {
			TestHelper.createAttachment(TestHelper.createDocumentSample(owner));
		}
		
		List<Attachment> res = this.bean.findAttachmentsByDocument(document);
		assertEquals(totalAttachments, res.size());
		
		res = new AttachmentDao().search(
				new DataPagination<Attachment>().withEntity(new Attachment()));
		assertEquals((totalAttachments + totalOtherAttachments), res.size());
	}
}