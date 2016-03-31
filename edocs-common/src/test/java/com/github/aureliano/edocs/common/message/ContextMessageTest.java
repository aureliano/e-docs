package com.github.aureliano.edocs.common.message;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ContextMessageTest {

	@Test
	public void testEquals() {
		ContextMessage c1 = new ContextMessage();
		assertTrue(c1.equals(c1));
		
		ContextMessage c2 = new ContextMessage();
		assertTrue(c1.equals(c2));
		
		c1.withSeverityLevel(SeverityLevel.INFO);
		assertFalse(c1.equals(c2));
		
		c2.withSeverityLevel(SeverityLevel.INFO);
		assertTrue(c1.equals(c2));
		
		c1.withMessage("something");
		assertFalse(c1.equals(c2));
		
		c2.withMessage("something");
		assertTrue(c1.equals(c2));
	}
}