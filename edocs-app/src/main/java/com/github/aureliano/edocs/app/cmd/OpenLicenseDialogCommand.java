package com.github.aureliano.edocs.app.cmd;

import com.github.aureliano.edocs.app.EdocsApp;
import com.github.aureliano.edocs.app.gui.help.LicenseDialog;

public class OpenLicenseDialogCommand implements ICommand {

	@Override
	public void execute() {
		if (!this.canExecute()) {
			return;
		}
		
		new LicenseDialog(EdocsApp.instance().getFrame()).setVisible(true);
	}

	@Override
	public boolean canExecute() {
		return true;
	}
}