package com.github.aureliano.edocs.secure.hash;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import com.github.aureliano.edocs.common.exception.SecureException;
import com.github.aureliano.edocs.secure.model.Algorithm;

public final class HashGenerator {

	private HashGenerator() {}
	
	public static String md5(String text) {
		return generateHash(text, Algorithm.MD5.getLabel());
	}
	
	public static String sha1(String text) {
		return generateHash(text, Algorithm.SHA_1.getLabel());
	}
	
	public static String sha2(String text) {
		return generateHash(text, Algorithm.SHA_2.getLabel());
	}
	
	public static String generateHash(String text, String algorithm) {
		MessageDigest md = null;
		
		try {
			md = MessageDigest.getInstance(algorithm);
			md.update(text.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
			throw new SecureException(ex);
		}
		
		byte bytes[] = md.digest();
		String hash = DatatypeConverter.printHexBinary(bytes);
		
		return hash;
	}
}