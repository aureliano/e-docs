package com.github.aureliano.edocs.app.gui;

import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JToolBar;

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
		this.buttonFindDocuments = new JButton();
		this.buttonFindDocuments.setToolTipText("Find document");
		
		this.buttonSaveDocument = new JButton();
		this.buttonSaveDocument.setToolTipText("Save document");
	}
}