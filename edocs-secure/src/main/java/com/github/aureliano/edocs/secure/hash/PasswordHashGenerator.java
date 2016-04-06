package com.github.aureliano.edocs.secure.hash;

import com.github.aureliano.edocs.common.config.AppConfiguration;
import com.github.aureliano.edocs.common.config.ConfigurationSingleton;
import com.github.aureliano.edocs.common.config.SecureConfiguration;
import com.github.aureliano.edocs.secure.model.Algorithm;
import com.github.aureliano.edocs.secure.model.PasswordEncryptionModel;

public final class PasswordHashGenerator {

	private PasswordHashGenerator() {}
	
	public static final String generate(PasswordEncryptionModel pwd) {
		String text = pwd.getPassword() + pwd.getSalt();
		
		for (short i = 0; i < pwd.getHashIterations(); i++) {
			text = HashGenerator.generateHash(text, pwd.getAlgorithm().getLabel());
		}
		
		return text;
	}
	
	public static final String generateFromAppConfiguration(String password) {
		AppConfiguration appConfiguration = ConfigurationSingleton.instance().getAppConfiguration();
		SecureConfiguration configuration = appConfiguration.getSecureConfiguration();
		
		return PasswordHashGenerator.generate(new PasswordEncryptionModel()
			.withAlgorithm(Algorithm.valueOf(configuration.getAlgorithm()))
			.withHashIterations(configuration.getHashIterations())
			.withSalt(configuration.getSalt())
			.withPassword(password));
	}
}