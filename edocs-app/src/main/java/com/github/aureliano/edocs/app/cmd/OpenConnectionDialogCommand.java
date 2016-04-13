package com.github.aureliano.edocs.app.cmd;

import com.github.aureliano.edocs.app.EdocsApp;
import com.github.aureliano.edocs.app.gui.AppFrame;
import com.github.aureliano.edocs.app.gui.connect.OpenDatabaseConnectionDialog;
import com.github.aureliano.edocs.common.persistence.IPersistenceManager;
import com.github.aureliano.edocs.common.persistence.PersistenceService;

public class OpenConnectionDialogCommand implements ICommand {

	@Override
	public void execute() {
		if (!this.canExecute()) {
			return;
		}
		
		AppFrame frame = EdocsApp.instance().getFrame();
		new OpenDatabaseConnectionDialog(frame).setVisible(true);
	}

	@Override
	public boolean canExecute() {
		IPersistenceManager pm = PersistenceService.instance().getPersistenceManager();
		return ((pm == null) || (!pm.isConneceted()));
	}
}