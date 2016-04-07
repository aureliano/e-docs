package com.github.aureliano.edocs.app.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

public class ExitMenuItem extends JMenuItem {

	private static final long serialVersionUID = -2424096936359107552L;

	public ExitMenuItem() {
		super.setText("Exit");
		
		super.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
}