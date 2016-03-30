package com.github.aureliano.edocs.domain.entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

public class AttachmentTest {

	@Test
	public void testEquals() {
		Attachment a1 = new Attachment();
		assertTrue(a1.equals(a1));
		
		Attachment a2 = new Attachment();
		assertTrue(a1.equals(a2));
		
		a1.withId(127);
		assertFalse(a1.equals(a2));
		
		a2.withId(127);
		assertTrue(a1.equals(a2));
		
		a1.withName("test");
		assertFalse(a1.equals(a2));
		
		a2.withName("test");
		assertTrue(a1.equals(a2));
		
		Date dt = new Date();
		a1.withUploadTime(dt);
		assertFalse(a1.equals(a2));
		
		a2.withUploadTime(dt);
		assertTrue(a1.equals(a2));
	}
}