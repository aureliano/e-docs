package com.github.aureliano.edocs.common.config;

public class FileRepositoryConfiguration {

	private String repositoryType;
	private String rootPath;
	private String limboPath;
	
	public FileRepositoryConfiguration() {}

	public String getRepositoryType() {
		return repositoryType;
	}

	public FileRepositoryConfiguration withRepositoryType(String repositoryType) {
		this.repositoryType = repositoryType;
		return this;
	}
	
	public String getRootPath() {
		return rootPath;
	}
	
	public FileRepositoryConfiguration withRootPath(String rootPath) {
		this.rootPath = rootPath;
		return this;
	}

	public String getLimboPath() {
		return limboPath;
	}

	public FileRepositoryConfiguration withLimboPath(String limboPath) {
		this.limboPath = limboPath;
		return this;
	}
}