package com.github.aureliano.edocs.app.cmd;

import com.github.aureliano.edocs.app.EdocsApp;
import com.github.aureliano.edocs.app.gui.AppFrame;
import com.github.aureliano.edocs.app.gui.panel.CloseableTabPanel;
import com.github.aureliano.edocs.app.gui.panel.SaveDocumentPanel;
import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class NewDocumentCommand implements ICommand {

	@Override
	public void execute() {
		if (!this.canExecute()) {
			return;
		}
		
		AppFrame frame = EdocsApp.instance().getFrame();
		String title = EdocsLocale.instance().getMessage("gui.frame.main.tabbed.pane.document.new");
		
		frame.addTabPanel(title, new SaveDocumentPanel());
		frame.getTabbedPane().setTabComponentAt(frame.getActiveTab(), new CloseableTabPanel(frame.getTabbedPane()));
	}

	@Override
	public boolean canExecute() {
		return true;
	}
}