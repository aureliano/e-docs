package com.github.aureliano.edocs.app.gui.menu.file;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import com.github.aureliano.edocs.app.cmd.ExitCommand;
import com.github.aureliano.edocs.app.cmd.ICommand;
import com.github.aureliano.edocs.app.model.IMenuItemAvailability;
import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class ExitMenuItem extends JMenuItem implements IMenuItemAvailability {

	private static final long serialVersionUID = -2424096936359107552L;

	private ICommand command;
	
	public ExitMenuItem() {
		this.command = new ExitCommand();
		super.setText(EdocsLocale.instance().getMessage("gui.menubar.file.exit"));
		
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