package com.github.aureliano.edocs.app.gui.menu.file;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import com.github.aureliano.edocs.app.cmd.ICommand;
import com.github.aureliano.edocs.app.cmd.OpenConnectionDialogCommand;
import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class ConnectMenuItem extends JMenuItem {

	private static final long serialVersionUID = -2981065009959985562L;

	private ICommand command;
	
	public ConnectMenuItem() {
		this.command = new OpenConnectionDialogCommand();
		super.setText(EdocsLocale.instance().getMessage("gui.menubar.file.connect"));
		
		super.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				command.execute();
			}
		});
	}
}