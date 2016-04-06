package com.github.aureliano.edocs.file.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

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
	public void testWriteToLimbo() {
		IEntity entity = new FileEntity().withId(7);
		String limboPath = this.configuration.getLimboPath();
		File sourceFile = FileHelper.buildFile("src", "test", "resources", entity.getId().toString());
		
		this.repository.writeToLimbo(sourceFile, entity);
		
		File limboFile = FileHelper.buildFile(limboPath, entity.getId().toString());
		
		assertTrue(limboFile.exists());
		assertFalse(limboFile.isDirectory());
		assertFalse(limboFile.isHidden());
		assertEquals(entity.getId().toString(), limboFile.getName());
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
	
	@Test
	public void testDeleteFile() {
		IEntity entity = new FileEntity().withId(7);
		
		File sourceFile = FileHelper.buildFile("src", "test", "resources", entity.getId().toString());
		String dir = this.repository.createDir(FileHelper.buildPath("8F", "14"));
		File destFile = FileHelper.buildFile(dir, entity.getId().toString());
		FileHelper.copyFile(sourceFile, destFile);
		
		assertTrue(new File(destFile.getAbsolutePath()).exists());
		
		this.repository.deleteFile(entity);
		File deletedFile = new File(destFile.getAbsolutePath());
		
		assertFalse(deletedFile.exists());
	}
	
	@Test
	public void testClearLimbo() {
		int totalFiles = 12;
		File sourceFile = FileHelper.buildFile("src", "test", "resources", "7");
		
		for (byte i = 0; i < totalFiles; i++) {
			File file = FileHelper.buildFile(this.configuration.getLimboPath(), String.valueOf(i));
			FileHelper.copyFile(sourceFile, file);
		}
		
		File limbo = new File(this.configuration.getLimboPath());
		assertEquals(totalFiles, limbo.list().length);
		
		this.repository.clearLimbo();
		int expected = 0;
		limbo = new File(this.configuration.getLimboPath());
		assertEquals(expected, limbo.list().length);
	}
	
	@Test
	public void testListFiles() {
		int totalFiles = 8;
		File sourceFile = FileHelper.buildFile("src", "test", "resources", "7");
		String dir = this.repository.createDir(FileHelper.buildPath("8F", "14"));
		
		for (byte i = 0; i < totalFiles; i++) {
			File file = FileHelper.buildFile(dir, String.valueOf(i));
			FileHelper.copyFile(sourceFile, file);
		}
		
		List<String> files = this.repository.listFiles(FileHelper.buildPath("8F", "14"));
		assertEquals(8, files.size());
	}
	
	@Test
	public void testGetFile() {
		IEntity entity = new FileEntity().withId(7);
		
		File sourceFile = FileHelper.buildFile("src", "test", "resources", entity.getId().toString());
		String dir = this.repository.createDir(FileHelper.buildPath("8F", "14"));
		File destFile = FileHelper.buildFile(dir, entity.getId().toString());
		FileHelper.copyFile(sourceFile, destFile);
		
		assertTrue(new File(destFile.getAbsolutePath()).exists());
		
		File file = this.repository.getFile(entity);
		assertTrue(file.exists());
		assertFalse(file.isDirectory());
		assertFalse(file.isHidden());
		
		assertEquals(entity.getId().toString(), file.getName());
		assertEquals(destFile, file);
	}
	
	@Test
	public void testGetFileStream() throws IOException {
		IEntity entity = new FileEntity().withId(7);
		
		File sourceFile = FileHelper.buildFile("src", "test", "resources", entity.getId().toString());
		String dir = this.repository.createDir(FileHelper.buildPath("8F", "14"));
		File destFile = FileHelper.buildFile(dir, entity.getId().toString());
		FileHelper.copyFile(sourceFile, destFile);
		
		assertTrue(new File(destFile.getAbsolutePath()).exists());
		
		FileInputStream stream = (FileInputStream) this.repository.getFileStream(entity);
		assertNotNull(stream);
		
		assertTrue(stream.getFD().valid());
		stream.close();
	}
}