package com.github.aureliano.edocs.common.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PersistenceServiceTest {

	@Test
	public void testInstance() {
		PersistenceService ps = PersistenceService.instance();
		assertNotNull(ps);
		
		assertTrue(ps == PersistenceService.instance());
	}
	
	@Test
	public void testRegisterPersistenceManager() {
		PersistenceService ps = PersistenceService.instance();
		CommonPersistenceManager pm = new CommonPersistenceManager();
		
		ps.registerPersistenceManager(pm);
		assertEquals(ps.getPersistenceManager(), pm);
	}
	
	@Test
	public void testMappingEntity() {
		PersistenceService ps = PersistenceService.instance();
		assertTrue(ps == ps.mapEntity(CommonEntity.class, CommonDao.class));
		
		assertTrue(ps.createDao(CommonEntity.class) instanceof CommonDao);
	}
}