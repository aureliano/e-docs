package com.github.aureliano.edocs.common.config;

public final class AppConfiguration {

	private SecureConfiguration secureConfiguration;
	
	public AppConfiguration() {}

	public SecureConfiguration getSecureConfiguration() {
		return secureConfiguration;
	}

	public AppConfiguration withSecureConfiguration(SecureConfiguration secureConfiguration) {
		this.secureConfiguration = secureConfiguration;
		return this;
	}
}