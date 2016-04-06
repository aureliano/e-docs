package com.github.aureliano.edocs.secure.hash;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.aureliano.edocs.secure.model.Algorithm;

public class HashGeneratorTest {

	@Test
	public void testGenerateHash() {
		String hash = HashGenerator.generateHash("mypass", Algorithm.MD5.getLabel());
		assertEquals("A029D0DF84EB5549C641E04A9EF389E5", hash);
		
		hash = HashGenerator.generateHash("mypass", Algorithm.SHA_1.getLabel());
		assertEquals("E727D1464AE12436E899A726DA5B2F11D8381B26", hash);
		
		hash = HashGenerator.generateHash("mypass", Algorithm.SHA_2.getLabel());
		assertEquals("EA71C25A7A602246B4C39824B855678894A96F43BB9B71319C39700A1E045222", hash);
	}
	
	@Test
	public void testMd5() {
		String hash = HashGenerator.md5("mypass");
		assertEquals("A029D0DF84EB5549C641E04A9EF389E5", hash);
	}
	
	@Test
	public void testSha1() {
		String hash = HashGenerator.sha1("mypass");
		assertEquals("E727D1464AE12436E899A726DA5B2F11D8381B26", hash);
	}
	
	@Test
	public void testSha2() {
		String hash = HashGenerator.sha2("mypass");
		assertEquals("EA71C25A7A602246B4C39824B855678894A96F43BB9B71319C39700A1E045222", hash);
	}
}