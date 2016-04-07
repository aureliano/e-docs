package com.github.aureliano.edocs.app.cmd;

public interface ICommand {

	public abstract void execute();
	
	public abstract boolean canExecute();
}