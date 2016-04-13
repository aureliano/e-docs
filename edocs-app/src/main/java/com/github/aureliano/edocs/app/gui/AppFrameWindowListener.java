package com.github.aureliano.edocs.app.gui;

import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.logging.Logger;

import com.github.aureliano.edocs.app.gui.configuration.wizard.ConfigurationWizardDialog;
import com.github.aureliano.edocs.common.config.AppConfiguration;
import com.github.aureliano.edocs.common.config.ConfigurationSingleton;

public class AppFrameWindowListener implements WindowListener {

	private static final Logger logger = Logger.getLogger(AppFrameWindowListener.class.getName());
	
	@Override
	public void windowOpened(WindowEvent evt) {
		if (!this.isApplicationEnvironmentConfigured()) {
			new ConfigurationWizardDialog((Frame) evt.getComponent()).setVisible(true);
		}
		
		this.loadDefaultConfiguration();
	}

	@Override
	public void windowClosing(WindowEvent evt) {}

	@Override
	public void windowClosed(WindowEvent evt) {
		logger.info(" >>> Shut down!");
	}

	@Override
	public void windowIconified(WindowEvent evt) {}

	@Override
	public void windowDeiconified(WindowEvent evt) {}

	@Override
	public void windowActivated(WindowEvent evt) {}

	@Override
	public void windowDeactivated(WindowEvent evt) {}
	
	private boolean isApplicationEnvironmentConfigured() {
		File confDir = new File("conf");
		File configurationFile = new File(confDir, "app-configuration.properties");
		File databaseDir = new File("db");
		
		return confDir.isDirectory() && configurationFile.isFile() && databaseDir.isDirectory();
	}
	
	private void loadDefaultConfiguration() {
		logger.info("Load default application configuration.");
		AppConfiguration configuration = ConfigurationSingleton.instance().loadDefaultAppConfiguration();
		ConfigurationSingleton.instance().setAppConfiguration(configuration);
	}
}