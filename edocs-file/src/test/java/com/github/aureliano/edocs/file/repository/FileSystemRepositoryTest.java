package com.github.aureliano.edocs.file.repository;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import com.github.aureliano.edocs.common.config.AppConfiguration;
import com.github.aureliano.edocs.common.config.ConfigurationSingleton;
import com.github.aureliano.edocs.common.helper.ConfigurationHelper;
import com.github.aureliano.edocs.common.helper.FileHelper;

public class FileSystemRepositoryTest {

	private IRepository repository;
	
	public FileSystemRepositoryTest() {
		if (ConfigurationSingleton.instance().getAppConfiguration() == null) {
			String path = "src/test/resources/conf/file-app-configuration.properties";
			AppConfiguration configuration = ConfigurationHelper.parseConfiguration(path);
			ConfigurationSingleton.instance().setAppConfiguration(configuration);
		}
		
		this.repository = new FileSystemRepository();
	}
	
	@Test
	public void testCreateDir() {
		String path = this.repository.createDir(FileHelper.buildPath("A1", "B2"));
		assertTrue(new File(path).isDirectory());
	}
}