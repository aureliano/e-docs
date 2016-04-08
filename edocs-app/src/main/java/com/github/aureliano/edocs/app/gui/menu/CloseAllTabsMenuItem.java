package com.github.aureliano.edocs.app.gui.menu;

import javax.swing.JMenuItem;

import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class CloseAllTabsMenuItem extends JMenuItem {

	private static final long serialVersionUID = 5402503871207802801L;

	public CloseAllTabsMenuItem() {
		super.setText(EdocsLocale.instance().getMessage("gui.menubar.file.close.all"));
	}
}