package com.github.aureliano.edocs.common.config;

public final class AppConfiguration {

	private SecureConfiguration secureConfiguration;
	private FileRepositoryConfiguration fileRepositoryConfiguration;
	private DatabaseConfiguration databaseConfiguration;
	
	public AppConfiguration() {}

	public SecureConfiguration getSecureConfiguration() {
		return secureConfiguration;
	}

	public AppConfiguration withSecureConfiguration(SecureConfiguration secureConfiguration) {
		this.secureConfiguration = secureConfiguration;
		return this;
	}

	public FileRepositoryConfiguration getFileRepositoryConfiguration() {
		return fileRepositoryConfiguration;
	}

	public AppConfiguration withFileRepositoryConfiguration(FileRepositoryConfiguration fileRepositoryConfiguration) {
		this.fileRepositoryConfiguration = fileRepositoryConfiguration;
		return this;
	}
	
	public DatabaseConfiguration getDatabaseConfiguration() {
		return databaseConfiguration;
	}
	
	public AppConfiguration withDatabaseConfiguration(DatabaseConfiguration databaseConfiguration) {
		this.databaseConfiguration = databaseConfiguration;
		return this;
	}
}