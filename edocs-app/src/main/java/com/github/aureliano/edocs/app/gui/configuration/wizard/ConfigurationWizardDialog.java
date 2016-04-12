package com.github.aureliano.edocs.app.gui.configuration.wizard;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.JDialog;

import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class ConfigurationWizardDialog extends JDialog {

	private static final long serialVersionUID = 7596141609375888669L;

	private EdocsLocale locale;
	
	public ConfigurationWizardDialog(Frame parent) {
		super(parent);
		
		this.locale = EdocsLocale.instance();
		this.buildGui(parent);
	}
	
	private void buildGui(Component parent) {
		ConfigurationWizardPanel panel = new ConfigurationWizardPanel();
		
		super.setContentPane(panel);
		super.setSize(new Dimension(450, 170));
		super.setResizable(false);

		super.setLocationRelativeTo(parent);
		super.setTitle(this.locale.getMessage("gui.frame.configuration.wizard.title"));
		super.setModal(true);
		
		super.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	}
}