package com.github.aureliano.edocs.app.gui.menu.doc;

import javax.swing.JMenuItem;

import com.github.aureliano.edocs.app.cmd.ICommand;
import com.github.aureliano.edocs.app.cmd.SaveDocumentCommand;
import com.github.aureliano.edocs.app.model.IMenuItemAvailability;
import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class SaveDocumentMenuItem extends JMenuItem implements IMenuItemAvailability {

	private static final long serialVersionUID = -2349844888548109933L;

	private ICommand command;
	
	public SaveDocumentMenuItem() {
		this.command = new SaveDocumentCommand();
		super.setText(EdocsLocale.instance().getMessage("gui.menubar.document.save"));
	}

	@Override
	public void setMenuItemAvailability() {
		super.setEnabled(this.command.canExecute());
	}
}