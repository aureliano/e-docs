package com.github.aureliano.edocs.app.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import com.github.aureliano.edocs.app.cmd.ExitCommand;
import com.github.aureliano.edocs.app.cmd.ICommand;

public class ExitMenuItem extends JMenuItem {

	private static final long serialVersionUID = -2424096936359107552L;

	private ICommand command;
	
	public ExitMenuItem() {
		this.command = new ExitCommand();
		super.setText("Exit");
		
		super.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				command.execute();
			}
		});
	}
}