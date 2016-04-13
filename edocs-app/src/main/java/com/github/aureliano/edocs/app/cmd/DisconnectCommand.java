package com.github.aureliano.edocs.app.cmd;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.aureliano.edocs.app.EdocsApp;
import com.github.aureliano.edocs.common.exception.EDocsException;
import com.github.aureliano.edocs.common.persistence.IPersistenceManager;
import com.github.aureliano.edocs.common.persistence.PersistenceService;

public class DisconnectCommand implements ICommand {

	private static final Logger logger = Logger.getLogger(DisconnectCommand.class.getName());
	
	@Override
	public void execute() {
		if (!this.canExecute()) {
			return;
		}
		
		IPersistenceManager pm = PersistenceService.instance().getPersistenceManager();
		try {
			pm.getConnection().close();
			logger.info("Database connection has just been closed.");
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, ex.getMessage(), ex);
			throw new EDocsException(ex);
		}
		EdocsApp.instance().getFrame().setDatabaseGuiEnabled(false);
	}

	@Override
	public boolean canExecute() {
		IPersistenceManager pm = PersistenceService.instance().getPersistenceManager();
		return ((pm != null) && (pm.isConneceted()));
	}
}