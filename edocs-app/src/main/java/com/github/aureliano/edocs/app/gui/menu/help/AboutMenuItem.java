package com.github.aureliano.edocs.app.gui.menu.help;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import com.github.aureliano.edocs.app.cmd.ICommand;
import com.github.aureliano.edocs.app.cmd.OpenAboutDialogCommand;
import com.github.aureliano.edocs.app.model.IMenuItemAvailability;
import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class AboutMenuItem extends JMenuItem implements IMenuItemAvailability {

	private static final long serialVersionUID = 3258389699083751538L;

	private ICommand command;
	
	public AboutMenuItem() {
		this.command = new OpenAboutDialogCommand();
		super.setText(EdocsLocale.instance().getMessage("gui.menubar.help.about"));
		
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