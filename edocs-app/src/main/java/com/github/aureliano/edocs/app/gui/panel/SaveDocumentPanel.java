package com.github.aureliano.edocs.app.gui.panel;

import java.awt.GridLayout;

import javax.swing.JPanel;

import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class SaveDocumentPanel extends JPanel {

	private static final long serialVersionUID = 2728249172386578330L;

	private EdocsLocale locale;
	
	public SaveDocumentPanel() {
		this.locale = EdocsLocale.instance();
		this.buildGui();
	}

	private void buildGui() {
		super.setLayout(new GridLayout(3, 2));
	}
}