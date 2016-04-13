package com.github.aureliano.edocs.common.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import com.github.aureliano.edocs.common.config.AppConfiguration;
import com.github.aureliano.edocs.common.config.FileRepositoryConfiguration;
import com.github.aureliano.edocs.common.config.SecureConfiguration;
import com.github.aureliano.edocs.common.exception.EDocsException;

public final class ConfigurationHelper {

	private static final String DEFAULT_CONFIGURATION_PATH = "conf/app-configuration.properties";
	private static final Logger logger = Logger.getLogger(ConfigurationHelper.class.getName());

	private ConfigurationHelper() {}
	
	public static AppConfiguration parseConfiguration() {
		return parseConfiguration(DEFAULT_CONFIGURATION_PATH);
	}
	
	public static AppConfiguration parseConfiguration(String path) {
		logger.info("Parse configuration file: " + path);
		Properties properties = loadProperties(path);
		return new AppConfiguration()
			.withSecureConfiguration(buildSecureModel(properties))
			.withFileRepositoryConfiguration(buildFileRepositoryModel(properties));
	}
	
	public static void saveConfigurationFile(AppConfiguration configuration, String comments) {
		Properties properties = new Properties();
		SecureConfiguration secure = configuration.getSecureConfiguration();
		FileRepositoryConfiguration repo = configuration.getFileRepositoryConfiguration();
		
		properties.setProperty("app.secure.algorithm", secure.getAlgorithm());
		properties.setProperty("app.secure.salt", secure.getSalt());
		properties.setProperty("app.secure.hash.iterations", secure.getHashIterations().toString());
		
		properties.setProperty("app.repository.type", repo.getRepositoryType());
		properties.setProperty("app.repository.file.path", repo.getRootPath());
		properties.setProperty("app.repository.limbo.path", repo.getLimboPath());
		
		File confDir = new File("conf");
		if (!confDir.exists()) {
			FileHelper.createDirectory(confDir, false);
		}
		
		try(FileOutputStream fos = new FileOutputStream(DEFAULT_CONFIGURATION_PATH)) {
			properties.store(fos, comments);
		} catch (IOException ex) {
			throw new EDocsException(ex);
		}
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
	
	private static FileRepositoryConfiguration buildFileRepositoryModel(Properties properties) {
		FileRepositoryConfiguration conf = new FileRepositoryConfiguration()
			.withRepositoryType(properties.getProperty("app.repository.type"))
			.withRootPath(properties.getProperty("app.repository.file.path"))
			.withLimboPath(properties.getProperty("app.repository.limbo.path"));
		
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