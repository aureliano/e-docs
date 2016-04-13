package com.github.aureliano.edocs.app.gui.menu.doc;

import javax.swing.JMenuItem;

import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class SaveDocumentMenuItem extends JMenuItem {

	private static final long serialVersionUID = -2349844888548109933L;

	public SaveDocumentMenuItem() {
		super.setText(EdocsLocale.instance().getMessage("gui.menubar.document.save"));
	}
}