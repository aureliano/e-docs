package com.github.aureliano.edocs.secure.hash;

import java.security.SecureRandom;

public final class SaltGenerator {

	private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
	private SaltGenerator() {}
	
	public static String generate(int length) {
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder(length);
		
		for(short i = 0; i < length; i++) {
			sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
		}
		
		return sb.toString();
	}
}