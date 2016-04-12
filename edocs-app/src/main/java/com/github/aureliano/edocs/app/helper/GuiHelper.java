package com.github.aureliano.edocs.app.helper;

import java.awt.Component;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.github.aureliano.edocs.app.gui.configuration.wizard.DatabasePanel;
import com.github.aureliano.edocs.app.gui.configuration.wizard.RepositoryPanel;
import com.github.aureliano.edocs.app.gui.configuration.wizard.SecurePanel;
import com.github.aureliano.edocs.common.config.AppConfiguration;
import com.github.aureliano.edocs.common.config.DatabaseConfiguration;
import com.github.aureliano.edocs.common.config.FileRepositoryConfiguration;
import com.github.aureliano.edocs.common.config.SecureConfiguration;
import com.github.aureliano.edocs.common.exception.EDocsException;

public final class GuiHelper {

	private GuiHelper() {}
	
	public static Icon createIcon(String resource) {
		URL url = ClassLoader.getSystemResource(resource);
		return new ImageIcon(url);
	}
	
	public static AppConfiguration buildConfigurationFromWizard(Component[] components) {
		SecureConfiguration secure = new SecureConfiguration();
		DatabaseConfiguration database = new DatabaseConfiguration();
		FileRepositoryConfiguration repo = new FileRepositoryConfiguration();
		
		AppConfiguration configuration = new AppConfiguration()
			.withSecureConfiguration(secure)
			.withDatabaseConfiguration(database)
			.withFileRepositoryConfiguration(repo);
		
		for (Component c : components) {
			if (c instanceof SecurePanel) {
				SecurePanel panel = (SecurePanel) c;
				secure
					.withAlgorithm(panel.getAlgorithm().name())
					.withSalt(panel.getSalt())
					.withHashIterations(panel.getHashIterations());
			} else if (c instanceof DatabasePanel) {
				DatabasePanel panel = (DatabasePanel) c;
				database
					.withUser(panel.getUserName())
					.withPassword(panel.getPassword());
			} else if (c instanceof RepositoryPanel) {
				RepositoryPanel panel = (RepositoryPanel) c;
				repo
					.withRepositoryType(panel.getRepositoryType().name())
					.withRootPath(panel.getRepositoryFile().getAbsolutePath())
					.withLimboPath(panel.getLimboFile().getAbsolutePath());
			} else {
				throw new EDocsException("Unknown panel card type: " + c.getClass().getName());
			}
		}
		
		return configuration;
	}
}