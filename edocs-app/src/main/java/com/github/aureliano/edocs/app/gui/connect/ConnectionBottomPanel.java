package com.github.aureliano.edocs.app.gui.connect;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.github.aureliano.edocs.app.EdocsApp;
import com.github.aureliano.edocs.app.helper.DatabaseHelper;
import com.github.aureliano.edocs.app.helper.GuiHelper;
import com.github.aureliano.edocs.common.exception.EDocsException;
import com.github.aureliano.edocs.common.locale.EdocsLocale;
import com.github.aureliano.edocs.secure.hash.PasswordHashGenerator;

public class ConnectionBottomPanel extends JPanel {

	private static final long serialVersionUID = 5398963938621005885L;
	private static final Logger logger = Logger.getLogger(ConnectionBottomPanel.class.getName());

	private EdocsLocale locale;
	
	private JButton buttonCancel;
	private JButton buttonConnect;
	
	public ConnectionBottomPanel() {
		this.locale = EdocsLocale.instance();
		this.buildGui();
	}

	private void buildGui() {
		this.configureButtonCancel();
		this.configureButtonConnect();
		
		super.setLayout(new BorderLayout());
		super.add(this.createBody(), BorderLayout.CENTER);
	}
	
	private void configureButtonCancel() {
		this.buttonCancel = new JButton();
		this.buttonCancel.setText(this.locale.getMessage("gui.frame.connection.cancel"));
		this.buttonCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GuiHelper.getFrame(getParent()).dispose();;
			}
		});
	}
	
	private void configureButtonConnect() {
		this.buttonConnect = new JButton();
		this.buttonConnect.setText(this.locale.getMessage("gui.frame.connection.connect"));
		this.buttonConnect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				OpenDatabaseConnectionDialog frame = (OpenDatabaseConnectionDialog) GuiHelper.getFrame(getParent());
				ConnectionBodyPanel bodyPanel = frame.getBodyPanel();
				
				try {
					String password = PasswordHashGenerator.generateFromAppConfiguration(bodyPanel.getPassword());
					DatabaseHelper.openConnection(bodyPanel.getUser(), password);
					EdocsApp.instance().getFrame().setDatabaseGuiEnabled(true);
					GuiHelper.getFrame(getParent()).dispose();
				} catch (EDocsException ex) {
					logger.log(Level.SEVERE, ex.getMessage(), ex);
					
					String title = locale.getMessage("gui.frame.connection.open.error.title");
					String message = locale.getMessage("gui.frame.connection.open.error.message");
					JOptionPane.showMessageDialog(getParent(), message, title, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	
	private JPanel createBody() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		
		JPanel cancelPanel = new JPanel(new FlowLayout());
		cancelPanel.add(this.buttonCancel);
		
		JPanel navigationPanel = new JPanel();
		navigationPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		navigationPanel.add(this.buttonConnect);
		navigationPanel.add(this.buttonCancel);
		
		panel.add(cancelPanel);
		panel.add(navigationPanel);
		
		return panel;
	}
}