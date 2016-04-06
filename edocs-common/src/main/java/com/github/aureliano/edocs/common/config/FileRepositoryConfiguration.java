package com.github.aureliano.edocs.common.config;

public class FileRepositoryConfiguration {

	private String rootPath;
	
	public FileRepositoryConfiguration() {}
	
	public String getRootPath() {
		return rootPath;
	}
	
	public FileRepositoryConfiguration withRootPath(String rootPath) {
		this.rootPath = rootPath;
		return this;
	}
}