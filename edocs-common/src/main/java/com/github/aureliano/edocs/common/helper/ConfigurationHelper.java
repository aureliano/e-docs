package com.github.aureliano.edocs.common.helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.github.aureliano.edocs.common.config.AppConfiguration;
import com.github.aureliano.edocs.common.config.SecureConfiguration;
import com.github.aureliano.edocs.common.exception.EDocsException;

public final class ConfigurationHelper {

	private static final String DEFAULT_CONFIGURATION_PATH = "conf/app-configuration.properties";

	private ConfigurationHelper() {}
	
	public static AppConfiguration parseConfiguration() {
		return parseConfiguration(DEFAULT_CONFIGURATION_PATH);
	}
	
	public static AppConfiguration parseConfiguration(String path) {
		Properties properties = loadProperties(path);
		return new AppConfiguration()
			.withSecureConfiguration(buildSecureModel(properties));
	}
	
	private static SecureConfiguration buildSecureModel(Properties properties) {
		SecureConfiguration conf = new SecureConfiguration()
			.withAlgorithm(properties.getProperty("app.secure.algorithm"))
			.withSalt(properties.getProperty("app.secure.salt"));
		
		if (StringHelper.isEmpty(properties.getProperty("app.secure.hash.iterations"))) {
			conf.withHashIterations(null);
		} else {
			conf.withHashIterations(Integer.parseInt(properties.getProperty("app.secure.hash.iterations")));
		}
		
		return conf;
	}
	
	private static Properties loadProperties(String path) {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(path));
		} catch (IOException ex) {
			throw new EDocsException(ex);
		}

		return properties;
	}
}