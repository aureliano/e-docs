package com.github.aureliano.edocs.app.cmd;

import com.github.aureliano.edocs.app.EdocsApp;
import com.github.aureliano.edocs.app.gui.AppFrame;

public class CloseActiveTabCommand implements ICommand {

	@Override
	public void execute() {
		if (!this.canExecute()) {
			return;
		}
		
		AppFrame frame = EdocsApp.instance().getFrame();
		frame.removeTab(frame.getActiveTab());
	}

	@Override
	public boolean canExecute() {
		int index = EdocsApp.instance().getFrame().getActiveTab();
		return index >= 0;
	}
}