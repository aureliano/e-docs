package com.github.aureliano.edocs.app.gui.menu.doc;

import javax.swing.JMenu;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.github.aureliano.edocs.app.model.IDatabaseConnectionDependent;
import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class DocumentMenu extends JMenu implements IDatabaseConnectionDependent {

	private static final long serialVersionUID = 8913482438475098482L;

	private FindDocumentMenuItem findDocumentMenuItem;
	private SaveDocumentMenuItem saveDocumentMenuItem;
	
	public DocumentMenu() {
		super.setText(EdocsLocale.instance().getMessage("gui.menubar.document"));
		this.addMenuItems();
	}

	private void addMenuItems() {
		this.findDocumentMenuItem = new FindDocumentMenuItem();
		this.saveDocumentMenuItem = new SaveDocumentMenuItem();
		
		super.add(this.findDocumentMenuItem);
		super.add(this.saveDocumentMenuItem);
		
		super.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				findDocumentMenuItem.setMenuItemAvailability();
				saveDocumentMenuItem.setMenuItemAvailability();
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {}
			
			@Override
			public void menuCanceled(MenuEvent e) {}
		});
	}

	@Override
	public void setDatabaseGuiEnabled(boolean enabled) {
		this.findDocumentMenuItem.setEnabled(enabled);
		this.saveDocumentMenuItem.setEnabled(enabled);
	}
}