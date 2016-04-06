package com.github.aureliano.edocs.secure.hash;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.aureliano.edocs.secure.model.Algorithm;

public class HashGeneratorTest {

	@Test
	public void testGenerateHash() {
		String hash = HashGenerator.generateHash("mypass", Algorithm.SHA_1.getLabel());
		assertEquals("5yfRRkrhJDbomacm2lsvEdg4GyY=", hash);
	}
}