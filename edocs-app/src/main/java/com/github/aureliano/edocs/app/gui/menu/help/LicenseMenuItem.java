package com.github.aureliano.edocs.app.gui.menu.help;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import com.github.aureliano.edocs.app.cmd.ICommand;
import com.github.aureliano.edocs.app.cmd.OpenLicenseDialogCommand;
import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class LicenseMenuItem extends JMenuItem {

	private static final long serialVersionUID = -2000357649209383142L;

	private ICommand command;

	public LicenseMenuItem() {
		this.command = new OpenLicenseDialogCommand();
		super.setText(EdocsLocale.instance().getMessage("gui.menubar.help.license"));
		
		super.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				command.execute();
			}
		});
	}
}