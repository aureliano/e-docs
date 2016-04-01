package com.github.aureliano.edocs.secure.hash;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import com.github.aureliano.edocs.secure.model.PasswordEncryptionModel;

public final class PasswordHashGenerator {

	private PasswordHashGenerator() {}
	
	public static final String generate(PasswordEncryptionModel pwd) {
		String text = pwd.getPassword() + pwd.getSalt();
		
		try {
			for (short i = 0; i < pwd.getHashIterations(); i++) {
				text = hash(text, pwd.getAlgorithm().getLabel());
			}
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
			throw new SecurityException(ex);
		}
		
		return text;
	}
	
	private static String hash(String text, String algorithm) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance(algorithm);
		md.update(text.getBytes("UTF-8"));
		
		byte bytes[] = md.digest();
		String hash = DatatypeConverter.printBase64Binary(bytes);
		
		return hash;
	}
}