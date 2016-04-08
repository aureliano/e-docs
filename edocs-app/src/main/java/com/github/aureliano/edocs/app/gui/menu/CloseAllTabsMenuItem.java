package com.github.aureliano.edocs.app.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import com.github.aureliano.edocs.app.cmd.CloseAllTabsCommand;
import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class CloseAllTabsMenuItem extends JMenuItem {

	private static final long serialVersionUID = 5402503871207802801L;

	public CloseAllTabsMenuItem() {
		super.setText(EdocsLocale.instance().getMessage("gui.menubar.file.close.all"));
		
		super.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new CloseAllTabsCommand().execute();
			}
		});
	}
}