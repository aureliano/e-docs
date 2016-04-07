package com.github.aureliano.edocs.app.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import com.github.aureliano.edocs.app.cmd.ICommand;
import com.github.aureliano.edocs.app.cmd.OpenAboutDialogCommand;

public class AboutMenuItem extends JMenuItem {

	private static final long serialVersionUID = 3258389699083751538L;

	private ICommand command;
	
	public AboutMenuItem() {
		this.command = new OpenAboutDialogCommand();
		super.setText("About");
		
		super.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				command.execute();
			}
		});
	}
}