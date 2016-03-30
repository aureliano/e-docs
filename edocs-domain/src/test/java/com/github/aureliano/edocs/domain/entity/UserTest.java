package com.github.aureliano.edocs.domain.entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class UserTest {

	@Test
	public void testEquals() {
		User u1 = new User();
		assertTrue(u1.equals(u1));
		
		User u2 = new User();
		assertTrue(u1.equals(u2));
		
		u1.withId(127);
		assertFalse(u1.equals(u2));
		
		u2.withId(127);
		assertTrue(u1.equals(u2));
		
		u1.withName("test");
		assertFalse(u1.equals(u2));
		
		u2.withName("test");
		assertTrue(u1.equals(u2));
		
		u1.withPassword("password");
		assertFalse(u1.equals(u2));
		
		u2.withPassword("password");
		assertTrue(u1.equals(u2));
	}
}