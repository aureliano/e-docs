package com.github.aureliano.edocs.file.repository;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RepositoryFactoryTest {

	@Test
	public void testCreateRepository() {
		IRepository repository = RepositoryFactory.createRepository(RepositoryType.FILE_SYSTEM);
		assertTrue(repository instanceof FileSystemRepository);
	}
}