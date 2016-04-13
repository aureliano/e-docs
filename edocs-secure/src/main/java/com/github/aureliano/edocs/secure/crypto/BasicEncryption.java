package com.github.aureliano.edocs.secure.crypto;

import java.nio.charset.Charset;

import javax.xml.bind.DatatypeConverter;

import com.github.aureliano.edocs.common.config.ConfigurationSingleton;
import com.github.aureliano.edocs.common.config.SecureConfiguration;

public final class BasicEncryption {

	private static final SecureConfiguration CONFIGURATION = ConfigurationSingleton.instance()
			.getAppConfiguration().getSecureConfiguration();
	
	private BasicEncryption() {}
	
	public static String encrypt(String text) {
		String salt = CONFIGURATION.getSalt();
		String target = new StringBuilder(salt).append(text).append(salt).toString();
		byte[] bytes = target.getBytes(Charset.forName("UTF-8"));
		
		return DatatypeConverter.printBase64Binary(bytes);
	}
	
	public static String decrypt(String text) {
		String salt = CONFIGURATION.getSalt();
		byte[] bytes = DatatypeConverter.parseBase64Binary(text);
		String res = new String(bytes);
		
		return res
			.replaceFirst("^" + salt, "")
			.replaceFirst(salt + "$", "");
	}
}