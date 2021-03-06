package com.github.aureliano.edocs.app.gui.menu.help;

import javax.swing.JMenu;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.github.aureliano.edocs.app.model.IDatabaseConnectionDependent;
import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class HelpMenu extends JMenu implements IDatabaseConnectionDependent {

	private static final long serialVersionUID = -3275074190970882751L;

	private LicenseMenuItem licenseMenuItem;
	private AboutMenuItem aboutMenuItem;
	
	public HelpMenu() {
		super.setText(EdocsLocale.instance().getMessage("gui.menubar.help"));
		this.addMenuItems();
	}

	private void addMenuItems() {
		this.licenseMenuItem = new LicenseMenuItem();
		this.aboutMenuItem = new AboutMenuItem();
		
		super.add(this.licenseMenuItem);
		super.add(this.aboutMenuItem);
		
		super.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				licenseMenuItem.setMenuItemAvailability();
				aboutMenuItem.setMenuItemAvailability();
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {}
			
			@Override
			public void menuCanceled(MenuEvent e) {}
		});
	}

	@Override
	public void setDatabaseGuiEnabled(boolean enabled) {}
}