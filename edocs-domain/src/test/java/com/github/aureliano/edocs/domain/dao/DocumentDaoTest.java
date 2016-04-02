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
import com.github.aureliano.edocs.common.persistence.DataPagination;
import com.github.aureliano.edocs.common.persistence.IDao;
import com.github.aureliano.edocs.common.persistence.IPersistenceManager;
import com.github.aureliano.edocs.common.persistence.PersistenceService;
import com.github.aureliano.edocs.domain.entity.Attachment;
import com.github.aureliano.edocs.domain.entity.Category;
import com.github.aureliano.edocs.domain.entity.Document;
import com.github.aureliano.edocs.domain.entity.User;
import com.github.aureliano.edocs.domain.helper.DomainPersistenceManager;
import com.github.aureliano.edocs.domain.helper.PersistenceHelper;

public class DocumentDaoTest {

	private IDao<Document> dao;
	
	public DocumentDaoTest() {
		PersistenceHelper.instance().prepareDatabase();
		
		PersistenceService ps = PersistenceService.instance();
		if (ps.getPersistenceManager() == null) {
			DomainPersistenceManager pm = new DomainPersistenceManager();
			pm.setConnection(PersistenceHelper.instance().getConnection());
			PersistenceService.instance().registerPersistenceManager(pm);
		}
		
		
		this.dao = new DocumentDao();
	}

	@Before
	public void beforeTest() throws SQLException {
		PersistenceHelper.instance().deleteAllRecords();;
	}
	
	@Test
	public void testValidateSaveAction() {
		this.checkInvalidCategory();
		this.checkInvalidDescription();
		this.checkInvalidAttachments();
	}
	
	@Test
	public void testSave() {
		Document d = new Document()
			.withCategory(Category.AGREEMENT)
			.withDescription(this.getValidDescription())
			.withAttachments(Arrays.asList(new Attachment()))
			.withDueDate(this.getToday())
			.withOwner(this.getValidUser());
		
		Document document = this.dao.save(d);
		assertNotNull(document.getId());
		assertEquals(d.getCategory(), document.getCategory());
		assertEquals(d.getDescription(), document.getDescription());
		assertEquals(d.getDueDate(), document.getDueDate());
	}
	
	private User getValidUser() {
		return new User()
			.withName("agustine")
			.withPassword("test123");
	}
	
	@Test
	public void testDeleteByEntity() throws SQLException {
		Category category = Category.INVOICE;
		Document d = new Document()
			.withCategory(category)
			.withDescription(this.getValidDescription())
			.withAttachments(Arrays.asList(new Attachment()));
		
		Document document = this.dao.save(d);
		
		ResultSet rs = PersistenceHelper.instance().executeQuery("select count(id) from documents where category = '" + category + "'");
		rs.next();
		assertEquals(1, rs.getInt(1));
		rs.close();
		
		this.dao.delete(document);
		
		rs = PersistenceHelper.instance().executeQuery("select count(id) from users where name = '" + category + "'");
		rs.next();
		assertEquals(0, rs.getInt(1));
		rs.close();
	}
	
	@Test
	public void testDeleteById() throws SQLException {
		Category category = Category.INVOICE;
		Document d = new Document()
			.withCategory(category)
			.withDescription(this.getValidDescription())
			.withAttachments(Arrays.asList(new Attachment()));
		
		Document document = this.dao.save(d);
		
		ResultSet rs = PersistenceHelper.instance().executeQuery("select count(id) from documents where category = '" + category + "'");
		rs.next();
		assertEquals(1, rs.getInt(1));
		rs.close();
		
		this.dao.delete(document.getId());
		
		rs = PersistenceHelper.instance().executeQuery("select count(id) from users where name = '" + category + "'");
		rs.next();
		assertEquals(0, rs.getInt(1));
		rs.close();
	}
	
	@Test
	public void testFind() {
		Document d = new Document()
			.withCategory(Category.INVOICE)
			.withDescription(this.getValidDescription())
			.withAttachments(Arrays.asList(new Attachment()));
		
		Document doc1 = this.dao.save(d);
		Document doc2 = this.dao.find(doc1.getId());
		
		assertEquals(doc1, doc2);
	}
	
	@Test
	public void testSearchByEntity() {
		Document d = new Document()
			.withCategory(Category.AGREEMENT)
			.withDescription(this.getValidDescription())
			.withAttachments(Arrays.asList(new Attachment()));
		
		Document doc1 = this.dao.save(d);
		this.dao.save(new Document()
			.withCategory(Category.CHECK)
			.withDescription(this.getValidDescription())
			.withAttachments(Arrays.asList(new Attachment())));
		
		List<Document> data = this.dao.search(new DataPagination<Document>().withEntity(d));
		
		assertEquals(1, data.size());
		
		Document doc2 = data.get(0);		
		assertEquals(doc1, doc2);
	}
	
	@Test
	public void testSearchByQuery() {
		Document d = new Document()
			.withCategory(Category.AGREEMENT)
			.withDescription(this.getValidDescription())
			.withAttachments(Arrays.asList(new Attachment()));
		
		Document doc1 = this.dao.save(d);
		this.dao.save(new Document()
			.withCategory(Category.CHECK)
			.withDescription(this.getValidDescription())
			.withAttachments(Arrays.asList(new Attachment())));
		
		List<Document> data = this.dao.search("select * from documents where category = 'AGREEMENT'");
		
		assertEquals(1, data.size());
		
		Document doc2 = data.get(0);		
		assertEquals(doc1, doc2);
	}

	private void checkInvalidCategory() {
		Document d = new Document()
			.withDescription(this.getValidDescription())
			.withAttachments(Arrays.asList(new Attachment()));
		this.validateContextMessage(d, "Expected to find a not null value for field category.");
	}

	private void checkInvalidDescription() {
		Document d = new Document()
			.withCategory(Category.AGREEMENT)
			.withAttachments(Arrays.asList(new Attachment()));
		this.validateContextMessage(d, "Expected to find a not empty text for field description.");
		
		d.withDescription("1");
		this.validateContextMessage(d, "Expected field description to have size between 5 and 1000 but got 1.");
		
		d.withDescription(this.getValidDescription() + "1");
		this.validateContextMessage(d, "Expected field description to have size between 5 and 1000 but got 1001.");
	}
	
	private void checkInvalidAttachments() {
		Document d = new Document()
			.withCategory(Category.AGREEMENT)
			.withDescription(this.getValidDescription());
		this.validateContextMessage(d, "Expected field hasAttachment to be true but got false.");
	}

	private String getValidDescription() {
		StringBuilder d = new StringBuilder();
		for (short i = 0; i < 1000; i++) {
			d.append(".");
		}
		
		return d.toString();
	}
	
	private void validateContextMessage(Document document, String message) {
		IPersistenceManager pm = PersistenceService.instance().getPersistenceManager();
		pm.clearContextMessages();
		
		try {
			this.dao.save(document);
		} catch (ValidationException ex) {
			assertEquals(1, pm.getContextMessages().size());
			
			ContextMessage m = pm.getContextMessages().iterator().next();
			assertEquals(SeverityLevel.ERROR, m.getSeverityLevel());
			assertEquals(message, m.getMessage());
		}
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