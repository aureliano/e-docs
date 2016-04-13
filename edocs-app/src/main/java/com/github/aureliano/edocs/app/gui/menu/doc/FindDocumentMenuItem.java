package com.github.aureliano.edocs.app.gui.menu.doc;

import javax.swing.JMenuItem;

import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class FindDocumentMenuItem extends JMenuItem {

	private static final long serialVersionUID = -2048071523465789664L;

	public FindDocumentMenuItem() {
		super.setText(EdocsLocale.instance().getMessage("gui.menubar.document.find.documents"));
	}
}