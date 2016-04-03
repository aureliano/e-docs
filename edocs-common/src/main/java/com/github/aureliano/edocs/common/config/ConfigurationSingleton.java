package com.github.aureliano.edocs.common.config;

import com.github.aureliano.edocs.common.helper.ConfigurationHelper;

public final class ConfigurationSingleton {

	private static ConfigurationSingleton instance;
	
	private AppConfiguration configuration;
	
	private ConfigurationSingleton() {}
	
	public static final ConfigurationSingleton instance() {
		if (instance == null) {
			instance = new ConfigurationSingleton();
		}
		
		return instance;
	}
	
	public void setAppConfiguration(AppConfiguration configuration) {
		this.configuration = configuration;
	}
	
	public AppConfiguration getAppConfiguration() {
		if (this.configuration == null) {
			this.loadDefaultAppConfiguration();
		}
		
		return this.configuration;
	}
	
	public void loadDefaultAppConfiguration() {
		this.configuration = ConfigurationHelper.parseConfiguration();
	}
}