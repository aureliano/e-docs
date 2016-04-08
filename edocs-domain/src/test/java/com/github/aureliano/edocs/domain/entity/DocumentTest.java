package com.github.aureliano.edocs.domain.entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

public class DocumentTest {

	@Test
	public void testEquals() {
		Document d1 = new Document();
		assertTrue(d1.equals(d1));
		
		Document d2 = new Document();
		assertTrue(d1.equals(d2));
		
		d1.withId(127);
		assertFalse(d1.equals(d2));
		
		d2.withId(127);
		assertTrue(d1.equals(d2));
		
		d1.withName("test");
		assertFalse(d1.equals(d2));
		
		d2.withName("test");
		assertTrue(d1.equals(d2));
		
		d1.withCategory(Category.AGREEMENT);
		assertFalse(d1.equals(d2));
		
		d2.withCategory(Category.AGREEMENT);
		assertTrue(d1.equals(d2));
		
		Date dt = new Date();
		d1.withDueDate(dt);
		assertFalse(d1.equals(d2));
		
		d2.withDueDate(dt);
		assertTrue(d1.equals(d2));
	}
}