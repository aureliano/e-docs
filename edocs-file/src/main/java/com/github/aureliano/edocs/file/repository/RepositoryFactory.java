package com.github.aureliano.edocs.file.repository;

import com.github.aureliano.edocs.common.exception.FileRepositoryException;

public final class RepositoryFactory {

	private RepositoryFactory() {}
	
	public static final IRepository createRepository(RepositoryType type) {
		switch (type) {
		case FILE_SYSTEM :
			return new FileSystemRepository();
		default :
			throw new FileRepositoryException("Unsupported repository type: " + type);
		}
	}
}