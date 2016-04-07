package com.github.aureliano.edocs.app.cmd;

import com.github.aureliano.edocs.app.EdocsApp;

public class ExitCommand implements ICommand {

	public ExitCommand() {}
	
	@Override
	public void execute() {
		if (!this.canExecute()) {
			return;
		}
		
		EdocsApp.instance().getFrame().dispose();
		System.exit(0);
	}

	@Override
	public boolean canExecute() {
		return true;
	}
}