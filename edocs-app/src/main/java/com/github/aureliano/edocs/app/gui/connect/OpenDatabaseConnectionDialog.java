package com.github.aureliano.edocs.app.gui.connect;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JPanel;

import com.github.aureliano.edocs.common.helper.FileHelper;
import com.github.aureliano.edocs.common.locale.EdocsLocale;
import com.github.aureliano.edocs.secure.crypto.BasicEncryption;

public class OpenDatabaseConnectionDialog extends JDialog {

	private static final long serialVersionUID = 8730312434218364955L;
	
	public static final String PREFERENCE_PATH = "conf/connection.preference";
	public static final String SEPARATOR = " = ";

	private EdocsLocale locale;
	private ConnectionBodyPanel bodyPanel;
	private ConnectionBottomPanel bottomPanel;
	
	public OpenDatabaseConnectionDialog(Frame parent) {
		super(parent);
		
		this.locale = EdocsLocale.instance();
		this.buildGui(parent);
		this.loadRememberedData();
	}

	private void buildGui(Frame parent) {
		super.setContentPane(this.createContentPane());
		super.setSize(new Dimension(395, 190));
		super.setResizable(false);

		super.setLocationRelativeTo(parent);
		super.setTitle(this.locale.getMessage("gui.frame.connection.title"));
		super.setModal(true);
		
		super.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	private JPanel createContentPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		this.bodyPanel = new ConnectionBodyPanel();
		this.bottomPanel = new ConnectionBottomPanel();
		
		panel.add(this.bodyPanel, BorderLayout.CENTER);
		panel.add(this.bottomPanel, BorderLayout.SOUTH);
		
		return panel;
	}
	
	private void loadRememberedData() {
		File file = new File(PREFERENCE_PATH);
		if (!file.exists()) {
			return;
		}
		
		String encryptedText = FileHelper.readFile(file);
		String text = BasicEncryption.decrypt(encryptedText);
		
		for (String line : text.split("\n")) {
			String[] tokens = line.split(OpenDatabaseConnectionDialog.SEPARATOR);
			if ("user".equals(tokens[0])) {
				this.bodyPanel.setUser(tokens[1]);
			} else if ("password".equals(tokens[0])) {
				this.bodyPanel.setPassword(tokens[1]);
			}
		}
	}
	
	public ConnectionBodyPanel getBodyPanel() {
		return this.bodyPanel;
	}
	
	public ConnectionBottomPanel getBottomPanel() {
		return this.bottomPanel;
	}
}