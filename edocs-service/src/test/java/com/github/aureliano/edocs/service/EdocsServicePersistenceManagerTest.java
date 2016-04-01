package com.github.aureliano.edocs.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.github.aureliano.edocs.common.message.ContextMessage;
import com.github.aureliano.edocs.common.message.SeverityLevel;

public class EdocsServicePersistenceManagerTest {

	@Test
	public void testAddContextMessage() {
		EdocsServicePersistenceManager pm = new EdocsServicePersistenceManager();
		ContextMessage m = new ContextMessage()
			.withSeverityLevel(SeverityLevel.INFO)
			.withMessage("test ok?");
		pm.addContextMessage(m);
		
		assertEquals(1, pm.getContextMessages().size());
		assertEquals(m, pm.getContextMessages().iterator().next());
		
		pm.addContextMessage(new ContextMessage());
		assertEquals(2, pm.getContextMessages().size());
		
		pm.addContextMessage(new ContextMessage()); // Should ignore existing messages.
		assertEquals(2, pm.getContextMessages().size());
	}
	
	@Test
	public void testSetContextMessages() {
		Set<ContextMessage> set = new HashSet<>();
		EdocsServicePersistenceManager pm = new EdocsServicePersistenceManager();
		
		pm.setContextMessages(set);
		assertTrue(pm.getContextMessages().isEmpty());
		
		set.add(new ContextMessage());
		pm.setContextMessages(set);
		assertEquals(1, pm.getContextMessages().size());
		
		set.add(new ContextMessage().withMessage("ok"));
		pm.setContextMessages(set);
		assertEquals(2, pm.getContextMessages().size());
	}
	
	@Test
	public void testGetContextMessages() {
		EdocsServicePersistenceManager pm = new EdocsServicePersistenceManager();
		
		pm.addContextMessage(new ContextMessage().withSeverityLevel(SeverityLevel.INFO));
		pm.addContextMessage(new ContextMessage().withSeverityLevel(SeverityLevel.WARN));
		pm.addContextMessage(new ContextMessage().withSeverityLevel(SeverityLevel.ERROR));
		
		assertEquals(3, pm.getContextMessages().size());
		
		pm.clearContextMessages();
		assertTrue(pm.getContextMessages().isEmpty());
	}
}