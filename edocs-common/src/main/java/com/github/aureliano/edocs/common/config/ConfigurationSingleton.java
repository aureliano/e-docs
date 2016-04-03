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
	
	public AppConfiguration getAppConfiguration(boolean loadDefault) {
		if ((this.configuration == null) && (loadDefault)) {
			this.configuration = this.loadDefaultAppConfiguration();
		}
		
		return this.configuration;
	}
	
	public AppConfiguration getAppConfiguration() {
		return this.getAppConfiguration(false);
	}
	
	public AppConfiguration loadDefaultAppConfiguration() {
		return ConfigurationHelper.parseConfiguration();
	}
}