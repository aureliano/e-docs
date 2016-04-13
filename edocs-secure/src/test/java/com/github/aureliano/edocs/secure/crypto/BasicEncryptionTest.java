package com.github.aureliano.edocs.secure.crypto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.aureliano.edocs.common.config.AppConfiguration;
import com.github.aureliano.edocs.common.config.ConfigurationSingleton;
import com.github.aureliano.edocs.common.config.SecureConfiguration;

public class BasicEncryptionTest {

	public BasicEncryptionTest() {
		AppConfiguration configuration = new AppConfiguration()
			.withSecureConfiguration(new SecureConfiguration()
				.withAlgorithm("MD5")
				.withSalt("salt123")
				.withHashIterations(14));
		
		ConfigurationSingleton.instance().setAppConfiguration(configuration);
	}
	
	@Test
	public void testEncrypt() {
		String actual = BasicEncryption.encrypt("malabibala");
		assertEquals("c2FsdDEyM21hbGFiaWJhbGFzYWx0MTIz", actual);
	}
	
	@Test
	public void testDecrypt() {
		String actual = BasicEncryption.decrypt("c2FsdDEyM21hbGFiaWJhbGFzYWx0MTIz");
		assertEquals("malabibala", actual);
	}
}