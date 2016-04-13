package com.github.aureliano.edocs.app.gui.menu.file;

import javax.swing.JMenu;
import javax.swing.JSeparator;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.github.aureliano.edocs.app.model.IDatabaseConnectionDependent;
import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class FileMenu extends JMenu implements IDatabaseConnectionDependent {

	private static final long serialVersionUID = -662548298147505185L;

	private ConnectMenuItem connectMenuItem;
	private DisconnectMenuItem disconnectMenuItem;
	private CloseTabMenuItem closeTabMenuItem;
	private CloseAllTabsMenuItem closeAllTabsMenuItem;
	private ExitMenuItem exitMenuItem;
	
	public FileMenu() {
		super.setText(EdocsLocale.instance().getMessage("gui.menubar.file"));
		this.addMenuItems();
	}

	private void addMenuItems() {
		this.connectMenuItem = new ConnectMenuItem();
		this.disconnectMenuItem = new DisconnectMenuItem();
		this.closeTabMenuItem = new CloseTabMenuItem();
		this.closeAllTabsMenuItem = new CloseAllTabsMenuItem();
		this.exitMenuItem = new ExitMenuItem();
		
		super.add(this.connectMenuItem);
		super.add(this.disconnectMenuItem);
		
		super.add(new JSeparator());
		
		super.add(this.closeTabMenuItem);
		super.add(this.closeAllTabsMenuItem);
		
		super.add(new JSeparator());
		
		super.add(this.exitMenuItem);
		
		super.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				connectMenuItem.setMenuItemAvailability();
				disconnectMenuItem.setMenuItemAvailability();
				closeTabMenuItem.setMenuItemAvailability();
				closeAllTabsMenuItem.setMenuItemAvailability();
				exitMenuItem.setMenuItemAvailability();
			}

			@Override
			public void menuDeselected(MenuEvent e) {}

			@Override
			public void menuCanceled(MenuEvent e) {}
		});
	}
	
	@Override
	public void setDatabaseGuiEnabled(boolean enabled) {
		this.connectMenuItem.setEnabled(!enabled);
		this.disconnectMenuItem.setEnabled(enabled);
	}
}