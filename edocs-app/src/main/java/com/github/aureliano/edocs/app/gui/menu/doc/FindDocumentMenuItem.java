package com.github.aureliano.edocs.app.gui.menu.doc;

import javax.swing.JMenuItem;

import com.github.aureliano.edocs.app.cmd.FindDocumentCommand;
import com.github.aureliano.edocs.app.cmd.ICommand;
import com.github.aureliano.edocs.app.model.IMenuItemAvailability;
import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class FindDocumentMenuItem extends JMenuItem implements IMenuItemAvailability {

	private static final long serialVersionUID = -2048071523465789664L;

	private ICommand command;
	
	public FindDocumentMenuItem() {
		this.command = new FindDocumentCommand();
		super.setText(EdocsLocale.instance().getMessage("gui.menubar.document.find.documents"));
	}

	@Override
	public void setMenuItemAvailability() {
		super.setEnabled(this.command.canExecute());
	}
}