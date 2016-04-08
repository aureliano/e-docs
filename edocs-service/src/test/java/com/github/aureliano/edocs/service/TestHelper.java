package com.github.aureliano.edocs.service;

import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Ignore;

import com.github.aureliano.edocs.common.config.AppConfiguration;
import com.github.aureliano.edocs.common.config.ConfigurationSingleton;
import com.github.aureliano.edocs.common.helper.ConfigurationHelper;
import com.github.aureliano.edocs.common.persistence.PersistenceService;
import com.github.aureliano.edocs.domain.dao.DocumentDao;
import com.github.aureliano.edocs.domain.dao.UserDao;
import com.github.aureliano.edocs.domain.entity.Attachment;
import com.github.aureliano.edocs.domain.entity.Category;
import com.github.aureliano.edocs.domain.entity.Document;
import com.github.aureliano.edocs.domain.entity.User;
import com.github.aureliano.edocs.domain.helper.PersistenceHelper;
import com.github.aureliano.edocs.service.bean.AttachmentServiceBean;

@Ignore
public class TestHelper {

	public static void initiliazeTestEnvironment() {
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
	}
	
	public static void checkExceptionThrown(IEmbeddedExecutor executor, Class<? extends Exception> et, String message) {
		try {
			executor.execute();
			Assert.fail("Expected an exception of type " + et.getName() + " but nothing was caught.");
		} catch (Exception ex) {
			Assert.assertEquals(ex.getClass(), et);
			Assert.assertEquals(message, ex.getMessage());
		}
	}
	
	public static Document prepareDocumentToSave(int totalAttachments) {
		Document document = new Document()
			.withName("document")
			.withCategory(Category.AGREEMENT)
			.withDescription("description")
			.withOwner(createUserSample())
			.withDeleted(false);
		
		AttachmentServiceBean attachmentServiceBean = new AttachmentServiceBean();
		File file = getSampleFile();
		for (byte i = 0; i < totalAttachments; i++) {
			document.attach(attachmentServiceBean.createTemporaryAttachment(file));
		}
		
		return document;
	}
	
	public static User createUserSample() {
		return createUserSample("maria");
	}
	
	public static User createUserSample(String name) {
		User user = new User()
			.withDbUser(false)
			.withName(name)
			.withPassword("test123");
		return new UserDao().save(user);
	}
	
	public static Document createDocumentSample() {
		return createDocumentSample(createUserSample("mariae"));
	}
	
	public static Document createDocumentSample(boolean deleted) {
		return createDocumentSample(createUserSample("mariae"), deleted);
	}
	
	public static Document createDocumentSample(User owner) {
		return createDocumentSample(owner, false);
	}
	
	public static Document createDocumentSample(User owner, boolean deleted) {
		Document d = new Document()
			.withName("document name")
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
	
	public static Attachment createAttachment(Document document) {
		File file = getSampleFile();
		
		Attachment attachment = new Attachment()
			.withName(file.getName())
			.withTemp(false)
			.withUploadTime(new Date());
		
		AttachmentServiceBean bean = new AttachmentServiceBean();
		attachment = bean.createTemporaryAttachment(file).withDocument(document);
		
		return new AttachmentServiceBean().saveAttachment(attachment);
	}

	public static Date getToday() {
		Calendar c = Calendar.getInstance();
		return removeTime(c.getTime());
	}
	
	public static Date removeTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		return c.getTime();
	}
	
	public static File getSampleFile() {
		return new File("src/test/resources/sample-file");
	}
}