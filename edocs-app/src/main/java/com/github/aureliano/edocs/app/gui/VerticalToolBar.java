package com.github.aureliano.edocs.app.gui;

import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JToolBar;

import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class VerticalToolBar extends JToolBar {

	private static final long serialVersionUID = -176640015146742392L;

	private JButton buttonFindDocuments;
	private JButton buttonSaveDocument;
	
	public VerticalToolBar() {
		super.setFloatable(false);
		super.setOrientation(JToolBar.VERTICAL);
		super.setMargin(new Insets(10, 5, 5, 5));
		
		this.configureButtons();
		
		super.add(this.buttonFindDocuments);
		super.add(this.buttonSaveDocument);
	}

	private void configureButtons() {
		EdocsLocale locale = EdocsLocale.instance();
		
		this.buttonFindDocuments = new JButton();
		this.buttonFindDocuments.setToolTipText(locale.getMessage("gui.toolbar.vertical.button.find.document.tooltip"));
		
		this.buttonSaveDocument = new JButton();
		this.buttonSaveDocument.setToolTipText(locale.getMessage("gui.toolbar.vertical.button.save.document.tooltip"));
	}
}