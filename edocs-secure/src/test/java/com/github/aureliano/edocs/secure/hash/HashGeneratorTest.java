package com.github.aureliano.edocs.secure.hash;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.aureliano.edocs.secure.model.Algorithm;

public class HashGeneratorTest {

	@Test
	public void testGenerateHash() {
		String hash = HashGenerator.generateHash("mypass", Algorithm.SHA_1.getLabel());
		assertEquals("E727D1464AE12436E899A726DA5B2F11D8381B26", hash);
	}
	
	@Test
	public void testMd5() {
		String hash = HashGenerator.generateHash("mypass", Algorithm.MD5.getLabel());
		assertEquals("A029D0DF84EB5549C641E04A9EF389E5", hash);
	}
}