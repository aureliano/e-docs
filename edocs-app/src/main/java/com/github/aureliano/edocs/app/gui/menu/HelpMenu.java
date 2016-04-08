package com.github.aureliano.edocs.app.gui.menu;

import javax.swing.JMenu;

import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class HelpMenu extends JMenu {

	private static final long serialVersionUID = -3275074190970882751L;

	private LicenseMenuItem licenseMenuItem;
	private AboutMenuItem aboutMenuItem;
	
	public HelpMenu() {
		super.setText(EdocsLocale.instance().getMessage(""));
		this.addMenuItems();
	}

	private void addMenuItems() {
		this.licenseMenuItem = new LicenseMenuItem();
		this.aboutMenuItem = new AboutMenuItem();
		
		super.add(this.licenseMenuItem);
		super.add(this.aboutMenuItem);
	}
}