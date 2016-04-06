package com.github.aureliano.edocs.file.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.github.aureliano.edocs.common.config.AppConfiguration;
import com.github.aureliano.edocs.common.config.ConfigurationSingleton;
import com.github.aureliano.edocs.common.config.FileRepositoryConfiguration;
import com.github.aureliano.edocs.common.helper.ConfigurationHelper;
import com.github.aureliano.edocs.common.helper.FileHelper;
import com.github.aureliano.edocs.common.persistence.IEntity;
import com.github.aureliano.edocs.file.model.FileEntity;

public class FileSystemRepositoryTest {

	private IRepository repository;
	private FileRepositoryConfiguration configuration;
	
	public FileSystemRepositoryTest() {
		if (ConfigurationSingleton.instance().getAppConfiguration() == null) {
			String path = "src/test/resources/conf/file-app-configuration.properties";
			AppConfiguration configuration = ConfigurationHelper.parseConfiguration(path);
			ConfigurationSingleton.instance().setAppConfiguration(configuration);
		}
		
		this.repository = new FileSystemRepository();
		this.configuration = ConfigurationSingleton.instance()
				.getAppConfiguration().getFileRepositoryConfiguration();
	}
	
	@Before
	public void beforeTest() {
		File repo = new File(this.configuration.getRootPath());
		if (repo.exists()) {
			FileHelper.delete(repo, true);
		}
		
		File limbo = new File(this.configuration.getLimboPath());
		if (limbo.exists()) {
			FileHelper.delete(limbo, true);
		}
		
		FileHelper.createDirectory(repo, false);
		FileHelper.createDirectory(limbo, false);
	}
	
	@Test
	public void testCreateDir() {
		String path = this.repository.createDir(FileHelper.buildPath("A1", "B2"));
		assertTrue(new File(path).isDirectory());
	}
	
	@Test
	public void testGetFreeDiskSpace() {
		Long size = this.repository.getFreeDiskSapce();
		assertNotNull(size);
		assertTrue(size > 0);
	}
	
	@Test
	public void testGetDiskSize() {
		Long size = this.repository.getDiskSize();
		assertNotNull(size);
		assertTrue(size > 0);
	}
	
	@Test
	public void testSaveFile() {
		IEntity entity = new FileEntity().withId(7);
		String limboPath = this.configuration.getLimboPath();
		
		File sourceFile = FileHelper.buildFile("src", "test", "resources", entity.getId().toString());
		File destFile = FileHelper.buildFile(limboPath, entity.getId().toString());
		FileHelper.copyFile(sourceFile, destFile);
		
		String path = this.repository.saveFile(entity);
		File file = new File(path);
		
		assertTrue(file.exists());
		assertFalse(file.isDirectory());
		assertFalse(file.isHidden());
		
		String endPath = FileHelper.buildPath("8F", "14", entity.getId().toString());
		assertTrue(file.getAbsolutePath().endsWith(endPath));
	}
}