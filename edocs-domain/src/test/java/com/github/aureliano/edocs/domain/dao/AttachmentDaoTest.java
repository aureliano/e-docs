package com.github.aureliano.edocs.domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.aureliano.edocs.common.exception.ValidationException;
import com.github.aureliano.edocs.common.message.ContextMessage;
import com.github.aureliano.edocs.common.message.SeverityLevel;
import com.github.aureliano.edocs.common.persistence.IDao;
import com.github.aureliano.edocs.common.persistence.IPersistenceManager;
import com.github.aureliano.edocs.common.persistence.PersistenceService;
import com.github.aureliano.edocs.domain.entity.Attachment;
import com.github.aureliano.edocs.domain.entity.Category;
import com.github.aureliano.edocs.domain.entity.Document;
import com.github.aureliano.edocs.domain.helper.DomainPersistenceManager;
import com.github.aureliano.edocs.domain.helper.PersistenceHelper;

public class AttachmentDaoTest {

	private IDao<Attachment> dao;
	
	public AttachmentDaoTest() {
		PersistenceHelper.instance().prepareDatabase();
		
		PersistenceService ps = PersistenceService.instance();
		if (ps.getPersistenceManager() == null) {
			DomainPersistenceManager pm = new DomainPersistenceManager();
			pm.setConnection(PersistenceHelper.instance().getConnection());
			PersistenceService.instance().registerPersistenceManager(pm);
		}
		
		
		this.dao = new AttachmentDao();
	}

	@Before
	public void beforeTest() throws SQLException {
		PersistenceHelper.instance().deleteAllRecords();;
	}
	
	@Test
	public void testValidateSaveAction() {
		this.checkInvalidName();
		this.checkInvalidUploadTime();
		this.checkInvalidDocument();
	}
	
	@Test
	public void testSave() {
		Attachment a = new Attachment()
			.withDocument(this.getValidDocument())
			.withName(this.getValidName())
			.withUploadTime(this.getToday());
		
		Attachment attachment = this.dao.save(a);
		assertNotNull(attachment.getId());
		assertEquals(a.getName(), attachment.getName());
		assertEquals(a.getDocument().getId(), attachment.getDocument().getId());
		assertEquals(a.getUploadTime(), attachment.getUploadTime());
	}
	
	@Test
	public void testDeleteByEntity() throws SQLException {
		String name = "delete-by-entity";
		Attachment a = new Attachment()
				.withDocument(this.getValidDocument())
				.withName(name)
				.withUploadTime(this.getToday());
		
		Attachment attachment = this.dao.save(a);
		
		ResultSet rs = PersistenceHelper.instance().executeQuery("select count(id) from attachments where name = '" + name + "'");
		rs.next();
		assertEquals(1, rs.getInt(1));
		rs.close();
		
		this.dao.delete(attachment);
		
		rs = PersistenceHelper.instance().executeQuery("select count(id) from attachments where name = '" + name + "'");
		rs.next();
		assertEquals(0, rs.getInt(1));
		rs.close();
	}
	
	@Test
	public void testDeleteById() throws SQLException {
		String name = "delete-by-id";
		Attachment a = new Attachment()
				.withDocument(this.getValidDocument())
				.withName(name)
				.withUploadTime(this.getToday());
		
		Attachment attachment = this.dao.save(a);
		
		ResultSet rs = PersistenceHelper.instance().executeQuery("select count(id) from attachments where name = '" + name + "'");
		rs.next();
		assertEquals(1, rs.getInt(1));
		rs.close();
		
		this.dao.delete(attachment.getId());
		
		rs = PersistenceHelper.instance().executeQuery("select count(id) from attachments where name = '" + name + "'");
		rs.next();
		assertEquals(0, rs.getInt(1));
		rs.close();
	}
	
	@Test
	public void testFind() {
		Attachment a = new Attachment()
			.withDocument(this.getValidDocument())
			.withName(this.getValidName())
			.withUploadTime(this.getToday());
		
		Attachment attachment1 = this.dao.save(a);
		Attachment attachment2 = this.dao.find(attachment1.getId());
		
		assertEquals(attachment1, attachment2);
	}
	
	@Test
	public void testSearchByEntity() {
		Attachment a = new Attachment()
			.withDocument(this.getValidDocument())
			.withName(this.getValidName())
			.withUploadTime(this.getToday());
		
		Attachment attachment1 = this.dao.save(a);
		this.dao.save(new Attachment()
			.withDocument(this.getValidDocument())
			.withName("different-one")
			.withUploadTime(this.getToday()));
		
		List<Attachment> data = this.dao.search(attachment1);
		
		assertEquals(1, data.size());
		
		Attachment attachment2 = data.get(0);		
		assertEquals(attachment1, attachment2);
	}
	
	@Test
	public void testSearchByQuery() {
		Attachment a = new Attachment()
			.withDocument(this.getValidDocument())
			.withName(this.getValidName())
			.withUploadTime(this.getToday());
		
		Attachment attachment1 = this.dao.save(a);
		this.dao.save(new Attachment()
			.withDocument(this.getValidDocument())
			.withName("different-one")
			.withUploadTime(this.getToday()));
		
		List<Attachment> data = this.dao.search("select * from attachments where name = '" + this.getValidName() + "'");
		
		assertEquals(1, data.size());
		
		Attachment attachment2 = data.get(0);		
		assertEquals(attachment1, attachment2);
	}

	private void checkInvalidName() {
		Attachment a = new Attachment()
			.withDocument(new Document())
			.withUploadTime(new Date());
		this.validateContextMessage(a, "Expected to find a not empty text for field name.");
		
		a.withName("1");
		this.validateContextMessage(a, "Expected field name to have size between 5 and 250 but got 1.");
		
		a.withName(this.getValidName() + "1");
		this.validateContextMessage(a, "Expected field name to have size between 5 and 250 but got 251.");
	}

	private void checkInvalidUploadTime() {
		Attachment a = new Attachment()
			.withName(this.getValidName())
			.withDocument(new Document());
		this.validateContextMessage(a, "Expected to find a not null value for field uploadTime.");
	}

	private void checkInvalidDocument() {
		Attachment a = new Attachment()
			.withName(this.getValidName())
			.withUploadTime(new Date());
		this.validateContextMessage(a, "Expected to find a not null value for field document.");
	}

	private void validateContextMessage(Attachment attachment, String message) {
		IPersistenceManager pm = PersistenceService.instance().getPersistenceManager();
		pm.clearContextMessages();
		
		try {
			this.dao.save(attachment);
		} catch (ValidationException ex) {
			assertEquals(1, pm.getContextMessages().size());
			
			ContextMessage m = pm.getContextMessages().iterator().next();
			assertEquals(SeverityLevel.ERROR, m.getSeverityLevel());
			assertEquals(message, m.getMessage());
		}
	}
	
	private Document getValidDocument() {
		Document d = new Document()
			.withCategory(Category.AGREEMENT)
			.withDescription("description")
			.withAttachments(Arrays.asList(new Attachment()));
		return new DocumentDao().save(d);
	}

	private String getValidName() {
		StringBuilder d = new StringBuilder();
		for (short i = 0; i < 250; i++) {
			d.append(".");
		}
		
		return d.toString();
	}

	private Date getToday() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		return c.getTime();
	}
}