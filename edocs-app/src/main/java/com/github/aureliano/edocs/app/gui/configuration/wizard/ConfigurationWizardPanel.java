package com.github.aureliano.edocs.app.gui.configuration.wizard;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class ConfigurationWizardPanel extends JPanel {

	private static final long serialVersionUID = -3217107444954533185L;
	
	public ConfigurationWizardPanel() {
		this.buildGui();
	}
	
	private void buildGui() {
		super.setLayout(new CardLayout());
		super.add(new SecurePanel(), SecurePanel.ID);
		super.add(new DatabasePanel(), DatabasePanel.ID);
	}
}