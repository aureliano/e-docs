package com.github.aureliano.edocs.service.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.github.aureliano.edocs.common.config.AppConfiguration;
import com.github.aureliano.edocs.common.config.ConfigurationSingleton;
import com.github.aureliano.edocs.common.exception.ServiceException;
import com.github.aureliano.edocs.common.helper.ConfigurationHelper;
import com.github.aureliano.edocs.common.persistence.PersistenceService;
import com.github.aureliano.edocs.domain.dao.DocumentDao;
import com.github.aureliano.edocs.domain.entity.Attachment;
import com.github.aureliano.edocs.domain.entity.Category;
import com.github.aureliano.edocs.domain.entity.Document;
import com.github.aureliano.edocs.domain.helper.PersistenceHelper;
import com.github.aureliano.edocs.service.EdocsServicePersistenceManager;

public class AttachmentServiceBeanTest {

	private AttachmentServiceBean bean;
	
	public AttachmentServiceBeanTest() {
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
		
		this.bean = new AttachmentServiceBean();
	}

	@Before
	public void beforeTest() throws SQLException {
		PersistenceHelper.instance().deleteAllRecords();;
	}
	
	@Test
	public void testCreateTemporaryAttachment() {
		Attachment attachment = this.bean.createTemporaryAttachment("new-temp-file");
		
		assertNotNull(attachment.getId());
		assertEquals("new-temp-file", attachment.getName());
		assertEquals(this.getToday(), this.removeTime(attachment.getUploadTime()));
		assertTrue(attachment.getTemp());
	}
	
	@Test
	public void testSaveAttachment() {
		Attachment attachment = new Attachment()
			.withName("save-file")
			.withTemp(false)
			.withUploadTime(new Date())
			.withDocument(this.getValidDocument());
		
		Attachment attachment1 = this.bean.saveAttachment(attachment);
		assertNotNull(attachment1.getId());
		assertEquals(attachment.getName(), attachment1.getName());
		assertEquals(attachment.getDocument().getId(), attachment1.getDocument().getId());
		assertEquals(this.removeTime(attachment.getUploadTime()), this.removeTime(attachment1.getUploadTime()));
	}
	
	@Test(expected = ServiceException.class)
	public void testDeleteNonTemporaryAttachment() {
		Attachment attachment = new Attachment()
			.withName("delete-non-temp-file")
			.withTemp(false)
			.withUploadTime(new Date())
			.withDocument(this.getValidDocument());
		
		Attachment attachment1 = this.bean.saveAttachment(attachment);
		this.bean.deleteTempAttachment(attachment1);
	}
	
	@Test
	public void testDeleteTempAttachment() throws SQLException {
		Attachment attachment = this.bean.createTemporaryAttachment("delete-temp-file");
		
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
	
	private Document getValidDocument() {
		Document d = new Document()
			.withCategory(Category.AGREEMENT)
			.withDescription("description")
			.withAttachments(Arrays.asList(new Attachment()));
		return new DocumentDao().save(d);
	}

	private Date getToday() {
		Calendar c = Calendar.getInstance();
		return this.removeTime(c.getTime());
	}
	
	private Date removeTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		return c.getTime();
	}
}