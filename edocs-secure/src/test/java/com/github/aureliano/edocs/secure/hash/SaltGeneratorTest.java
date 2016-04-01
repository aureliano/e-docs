package com.github.aureliano.edocs.secure.hash;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SaltGeneratorTest {

	@Test
	public void testGenerate() {
		assertTrue(SaltGenerator.generate(1).matches("[\\d\\w]{1}"));
		assertTrue(SaltGenerator.generate(2).matches("[\\d\\w]{2}"));
		assertTrue(SaltGenerator.generate(5).matches("[\\d\\w]{5}"));
		assertTrue(SaltGenerator.generate(100).matches("[\\d\\w]{100}"));
	}
}