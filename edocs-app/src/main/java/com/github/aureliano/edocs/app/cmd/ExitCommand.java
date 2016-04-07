package com.github.aureliano.edocs.app.cmd;

public class ExitCommand implements ICommand {

	public ExitCommand() {}
	
	@Override
	public void execute() {
		if (!this.canExecute()) {
			return;
		}
		
		System.exit(0);
	}

	@Override
	public boolean canExecute() {
		return true;
	}
}