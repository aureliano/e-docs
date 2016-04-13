package com.github.aureliano.edocs.app.gui.menu.file;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import com.github.aureliano.edocs.app.cmd.CloseActiveTabCommand;
import com.github.aureliano.edocs.app.cmd.ICommand;
import com.github.aureliano.edocs.app.model.IMenuItemAvailability;
import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class CloseTabMenuItem extends JMenuItem implements IMenuItemAvailability {

	private static final long serialVersionUID = -3834903211976535165L;

	private ICommand command;
	
	public CloseTabMenuItem() {
		this.command = new CloseActiveTabCommand();
		super.setText(EdocsLocale.instance().getMessage("gui.menubar.file.close"));
		
		super.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				command.execute();
			}
		});
	}

	@Override
	public void setMenuItemAvailability() {
		super.setEnabled(this.command.canExecute());
	}
}