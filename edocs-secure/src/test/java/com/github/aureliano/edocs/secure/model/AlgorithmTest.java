package com.github.aureliano.edocs.secure.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AlgorithmTest {

	@Test
	public void testGetLabel() {
		assertEquals("MD5", Algorithm.MD5.getLabel());
		assertEquals("SHA", Algorithm.SHA.getLabel());
		assertEquals("SHA-1", Algorithm.SHA_1.getLabel());
	}
}