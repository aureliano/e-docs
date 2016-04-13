package com.github.aureliano.edocs.app.cmd;

import java.util.logging.Logger;

import com.github.aureliano.edocs.app.EdocsApp;
import com.github.aureliano.edocs.app.helper.DatabaseHelper;

public class ExitCommand implements ICommand {

	private static final Logger logger = Logger.getLogger(ExitCommand.class.getName());
	
	public ExitCommand() {}
	
	@Override
	public void execute() {
		if (!this.canExecute()) {
			return;
		}
		
		DatabaseHelper.closeConnection();;
		this.shutDown();
	}

	@Override
	public boolean canExecute() {
		return true;
	}
	
	private void shutDown() {
		EdocsApp.instance().getFrame().dispose();
		logger.info(" >>> Shut down!");
		System.exit(0);
	}
}