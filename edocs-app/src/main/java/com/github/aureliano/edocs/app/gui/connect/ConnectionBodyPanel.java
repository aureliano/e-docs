package com.github.aureliano.edocs.app.gui.connect;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class ConnectionBodyPanel extends JPanel {

	private static final long serialVersionUID = -2160277270456833837L;

	private EdocsLocale locale;
	
	private JTextField textFieldUserName;
	private JPasswordField passwordFieldUser;
	private JCheckBox checkBoxRemember;
	
	public ConnectionBodyPanel() {
		this.locale = EdocsLocale.instance();
		this.buildGui();
	}
	
	public String getUser() {
		return this.textFieldUserName.getText();
	}
	
	public void setUser(String user) {
		this.textFieldUserName.setText(user);
	}
	
	public String getPassword() {
		return new String(this.passwordFieldUser.getPassword());
	}
	
	public void setPassword(String password) {
		this.passwordFieldUser.setText(password);
	}
	
	public boolean hasToRemember() {
		return this.checkBoxRemember.isSelected();
	}
	
	private void buildGui() {
		this.configureTextFieldUserName();
		this.configurePasswordFieldUser();
		this.configureCheckBoxRemember();
		
		super.setLayout(new BorderLayout());
		super.add(this.createBody(), BorderLayout.CENTER);
	}

	private void configureTextFieldUserName() {
		this.textFieldUserName = new JTextField();
		this.textFieldUserName.setPreferredSize(new Dimension(300, 25));
	}
	
	private void configurePasswordFieldUser() {
		this.passwordFieldUser = new JPasswordField();
		this.passwordFieldUser.setPreferredSize(new Dimension(300, 25));
	}
	
	private void configureCheckBoxRemember() {
		File file = new File(OpenDatabaseConnectionDialog.PREFERENCE_PATH);
		
		this.checkBoxRemember = new JCheckBox();
		this.checkBoxRemember.setText(this.locale.getMessage("gui.frame.connection.remember"));
		this.checkBoxRemember.setSelected(file.exists());
	}
	
	private JPanel createBody() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		
		JPanel panelTitle = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JLabel title = new JLabel(this.locale.getMessage("gui.frame.connection.title"));
		Font font = title.getFont();
		title.setFont(new Font(font.getName(), font.getStyle(), 16));
		panelTitle.add(title);
		panel.add(panelTitle);
		
		JPanel panelUserName = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		panelUserName.add(new JLabel(this.locale.getMessage("gui.frame.connection.user.name")));
		panelUserName.add(this.textFieldUserName);
		panel.add(panelUserName);
		
		JPanel panelUserPassword = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		panelUserPassword.add(new JLabel(this.locale.getMessage("gui.frame.connection.user.password")));
		panelUserPassword.add(this.passwordFieldUser);
		panel.add(panelUserPassword);
		
		JPanel panelRemember = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		panelRemember.add(this.checkBoxRemember);
		panel.add(panelRemember);
		
		return panel;
	}
}