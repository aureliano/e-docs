package com.github.aureliano.edocs.common.locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EdocsLocaleTest {

	@Test
	public void testInstance() {
		EdocsLocale l1 = EdocsLocale.instance();
		assertNotNull(l1);
		
		EdocsLocale l2 = EdocsLocale.instance();
		assertNotNull(l2);
		
		assertTrue(l1 == l2);
	}
	
	@Test
	public void testGetMessage() {
		String locale = EdocsLocale.instance().getMessage("locale.display");
		assertEquals("en_US", locale);
	}
}