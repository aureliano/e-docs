package com.github.aureliano.edocs.app.gui.connect;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JPanel;

import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class OpenDatabaseConnectionDialog extends JDialog {

	private static final long serialVersionUID = 8730312434218364955L;

	private EdocsLocale locale;
	private ConnectionBodyPanel bodyPanel;
	private ConnectionBottomPanel bottomPanel;
	
	public OpenDatabaseConnectionDialog(Frame parent) {
		super(parent);
		
		this.locale = EdocsLocale.instance();
		this.buildGui(parent);
	}

	private void buildGui(Frame parent) {
		super.setContentPane(this.createContentPane());
		super.setSize(new Dimension(395, 170));
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
	
	public ConnectionBodyPanel getBodyPanel() {
		return this.bodyPanel;
	}
	
	public ConnectionBottomPanel getBottomPanel() {
		return this.bottomPanel;
	}
}