package com.github.aureliano.edocs.app.cmd;

import com.github.aureliano.edocs.app.EdocsApp;

public class CloseAllTabsCommand implements ICommand {

	@Override
	public void execute() {
		if (!this.canExecute()) {
			return;
		}
		
		EdocsApp.instance().getFrame().removeAllTabs();
	}

	@Override
	public boolean canExecute() {
		int index = EdocsApp.instance().getFrame().getActiveTab();
		return index >= 0;
	}
}