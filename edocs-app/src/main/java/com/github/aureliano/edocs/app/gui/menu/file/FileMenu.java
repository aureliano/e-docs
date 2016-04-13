package com.github.aureliano.edocs.app.gui.menu.file;

import javax.swing.JMenu;
import javax.swing.JSeparator;

import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class FileMenu extends JMenu {

	private static final long serialVersionUID = -662548298147505185L;

	private ConnectMenuItem connectMenuItem;
	private CloseTabMenuItem closeTabMenuItem;
	private CloseAllTabsMenuItem closeAllTabsMenuItem;
	private ExitMenuItem exitMenuItem;
	
	public FileMenu() {
		super.setText(EdocsLocale.instance().getMessage("gui.menubar.file"));
		this.addMenuItems();
	}

	private void addMenuItems() {
		this.connectMenuItem = new ConnectMenuItem();
		this.closeTabMenuItem = new CloseTabMenuItem();
		this.closeAllTabsMenuItem = new CloseAllTabsMenuItem();
		this.exitMenuItem = new ExitMenuItem();
		
		super.add(this.connectMenuItem);
		
		super.add(new JSeparator());
		
		super.add(this.closeTabMenuItem);
		super.add(this.closeAllTabsMenuItem);
		
		super.add(new JSeparator());
		
		super.add(this.exitMenuItem);
	}
}