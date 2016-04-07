package com.github.aureliano.edocs.app.cmd;

import com.github.aureliano.edocs.app.EdocsApp;
import com.github.aureliano.edocs.app.gui.HelpDialog;

public class OpenAboutDialogCommand implements ICommand {

	@Override
	public void execute() {
		if (!this.canExecute()) {
			return;
		}
		
		new HelpDialog(EdocsApp.instance().getFrame()).setVisible(true);
	}

	@Override
	public boolean canExecute() {
		return true;
	}
}