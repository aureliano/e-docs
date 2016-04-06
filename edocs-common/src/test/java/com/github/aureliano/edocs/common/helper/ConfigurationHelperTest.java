package com.github.aureliano.edocs.common.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.github.aureliano.edocs.common.config.AppConfiguration;
import com.github.aureliano.edocs.common.config.FileRepositoryConfiguration;
import com.github.aureliano.edocs.common.config.SecureConfiguration;
import com.github.aureliano.edocs.common.exception.EDocsException;

public class ConfigurationHelperTest {

	@Test(expected = EDocsException.class)
	public void testParseNonExistingConfiguration() {
		ConfigurationHelper.parseConfiguration("non/existing/path");
	}
	
	@Test
	public void testParseEmptyConfiguration() {
		AppConfiguration conf = ConfigurationHelper.parseConfiguration("src/test/resources/conf/empty-conf.properties");
		SecureConfiguration sc = conf.getSecureConfiguration();
		FileRepositoryConfiguration frc = conf.getFileRepositoryConfiguration();
		
		assertNull(sc.getAlgorithm());
		assertNull(sc.getHashIterations());
		assertNull(sc.getSalt());
		
		assertNull(frc.getRootPath());
	}
	
	@Test
	public void testParseConfiguration() {
		AppConfiguration conf = ConfigurationHelper.parseConfiguration("src/test/resources/conf/app-configuration.properties");
		SecureConfiguration sc = conf.getSecureConfiguration();
		FileRepositoryConfiguration frc = conf.getFileRepositoryConfiguration();
		
		assertEquals("MD5", sc.getAlgorithm());
		assertEquals(new Integer(15), sc.getHashIterations());
		assertEquals("test123", sc.getSalt());
		
		assertEquals("target/repository/file", frc.getRootPath());
	}
}