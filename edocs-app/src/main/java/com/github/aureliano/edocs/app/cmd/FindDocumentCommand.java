package com.github.aureliano.edocs.app.cmd;

import com.github.aureliano.edocs.common.exception.EDocsException;

public class FindDocumentCommand implements ICommand {

	@Override
	public void execute() {
		if (!this.canExecute()) {
			return;
		}
		
		throw new EDocsException("Not implemented yet.");
	}

	@Override
	public boolean canExecute() {
		return false;
	}
}