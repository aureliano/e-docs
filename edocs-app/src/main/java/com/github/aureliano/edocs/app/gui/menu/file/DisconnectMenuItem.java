package com.github.aureliano.edocs.app.gui.menu.file;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import com.github.aureliano.edocs.app.cmd.DisconnectCommand;
import com.github.aureliano.edocs.app.cmd.ICommand;
import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class DisconnectMenuItem extends JMenuItem {

	private static final long serialVersionUID = -6899709122453780576L;
	
	private ICommand command;
	
	public DisconnectMenuItem() {
		this.command = new DisconnectCommand();
		super.setText(EdocsLocale.instance().getMessage("gui.menubar.file.disconnect"));
		
		super.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				command.execute();
			}
		});
	}
}