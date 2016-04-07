package com.github.aureliano.edocs.app.gui.menu;

import javax.swing.JMenu;

public class DocumentMenu extends JMenu {

	private static final long serialVersionUID = 8913482438475098482L;

	private FindDocumentMenuItem findDocumentMenuItem;
	private SaveDocumentMenuItem saveDocumentMenuItem;
	
	public DocumentMenu() {
		super.setText("Document");
		this.addMenuItems();
	}

	private void addMenuItems() {
		this.findDocumentMenuItem = new FindDocumentMenuItem();
		this.saveDocumentMenuItem = new SaveDocumentMenuItem();
		
		super.add(this.findDocumentMenuItem);
		super.add(this.saveDocumentMenuItem);
	}
}