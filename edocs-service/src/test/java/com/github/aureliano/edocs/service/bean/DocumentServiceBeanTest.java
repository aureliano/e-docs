package com.github.aureliano.edocs.service.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.aureliano.edocs.common.config.AppConfiguration;
import com.github.aureliano.edocs.common.config.ConfigurationSingleton;
import com.github.aureliano.edocs.common.helper.ConfigurationHelper;
import com.github.aureliano.edocs.common.persistence.DataPagination;
import com.github.aureliano.edocs.common.persistence.PersistenceService;
import com.github.aureliano.edocs.domain.dao.DocumentDao;
import com.github.aureliano.edocs.domain.dao.UserDao;
import com.github.aureliano.edocs.domain.entity.Attachment;
import com.github.aureliano.edocs.domain.entity.Category;
import com.github.aureliano.edocs.domain.entity.Document;
import com.github.aureliano.edocs.domain.entity.User;
import com.github.aureliano.edocs.domain.helper.PersistenceHelper;
import com.github.aureliano.edocs.service.EdocsServicePersistenceManager;

public class DocumentServiceBeanTest {

	private DocumentServiceBean bean;
	
	public DocumentServiceBeanTest() {
		PersistenceHelper.instance().prepareDatabase();
		PersistenceService ps = PersistenceService.instance();
		
		if (ps.getPersistenceManager() == null) {
			EdocsServicePersistenceManager pm = new EdocsServicePersistenceManager();
			pm.setConnection(PersistenceHelper.instance().getConnection());
			ps.registerPersistenceManager(pm);
		}
		
		if (ConfigurationSingleton.instance().getAppConfiguration() == null) {
			String path = "src/test/resources/conf/service-app-configuration.properties";
			AppConfiguration configuration = ConfigurationHelper.parseConfiguration(path);
			ConfigurationSingleton.instance().setAppConfiguration(configuration);
		}
		
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
	public void testLogicalDeletion() throws SQLException {
		Document document = this.createDocumentSample();
		assertFalse(document.getDeleted());
		
		Integer id = document.getId();
		document = this.bean.findDocumentById(id);
		assertFalse(document.getDeleted());
		
		this.bean.logicalDeletion(document);
		document = this.bean.findDocumentById(id);
		assertTrue(document.getDeleted());
	}
	
	private Document createDocumentSample() {
		return this.createDocumentSample(this.createUserSample("mariae"));
	}
	
	private Document createDocumentSample(User owner) {
		Document d = new Document()
			.withCategory(Category.AGREEMENT)
			.withDescription("description")
			.withOwner(owner)
			.withDeleted(false)
			.withAttachments(Arrays.asList(new Attachment()));
		
		Document document = new DocumentDao().save(d);
		try {
			PersistenceService.instance().getPersistenceManager().getConnection().commit(); // TODO: Use service method.
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
}