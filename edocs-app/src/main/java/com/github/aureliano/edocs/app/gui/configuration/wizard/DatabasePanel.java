package com.github.aureliano.edocs.app.gui.configuration.wizard;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.github.aureliano.edocs.app.helper.GuiHelper;
import com.github.aureliano.edocs.common.helper.StringHelper;
import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class DatabasePanel extends JPanel {

	private static final long serialVersionUID = -1113524655585948647L;
	public static final String ID = "DATABASE_CARD";

	private EdocsLocale locale;
	
	private JTextField textFieldUserName;
	private JPasswordField passwordFieldUser;
	
	private JButton buttonCancel;
	private JButton buttonPrevious;
	private JButton buttonNext;
	
	public DatabasePanel() {
		this.locale = EdocsLocale.instance();
		this.buildGui();
	}

	private void buildGui() {
		this.configureTextFieldUserName();
		this.configurePasswordFieldUser();
		
		this.buttonCancel = ConfigurationWizardDialog.createButtonCancel();
		this.configureButtonPrevious();
		this.configureButtonNext();
		
		super.setLayout(new BorderLayout());
		super.add(this.createBody(), BorderLayout.CENTER);
		super.add(this.createBottom(), BorderLayout.SOUTH);
	}
	
	private String applyValidation() {
		List<String> messages = new ArrayList<>();
		
		String userName = this.textFieldUserName.getText();
		final byte NAME_MIN = 5;
		final byte NAME_MAX = 25;
		if ((userName.length() < NAME_MIN) || (userName.length() > NAME_MAX)) {
			String message = this.locale.getMessage("gui.frame.configuration.wizard.database.validation.user.name.length");
			messages.add(message
					.replaceFirst("\\$\\{0\\}", String.valueOf(NAME_MIN))
					.replaceFirst("\\$\\{1\\}", String.valueOf(NAME_MAX)));
		}
		
		char[] userPass = this.passwordFieldUser.getPassword();
		final byte PASS_MIN = 5;
		final byte PASS_MAX = 30;
		if ((userPass.length < PASS_MIN) || (userPass.length > PASS_MAX)) {
			String message = this.locale.getMessage("gui.frame.configuration.wizard.database.validation.user.password.length");
			messages.add(message
					.replaceFirst("\\$\\{0\\}", String.valueOf(PASS_MIN))
					.replaceFirst("\\$\\{1\\}", String.valueOf(PASS_MAX)));
		}
		
		return (messages.isEmpty()) ? null : StringHelper.join(messages, "\n");
	}
	
	private void configureTextFieldUserName() {
		this.textFieldUserName = new JTextField();
		this.textFieldUserName.setPreferredSize(new Dimension(300, 25));
	}
	
	private void configurePasswordFieldUser() {
		this.passwordFieldUser = new JPasswordField();
		this.passwordFieldUser.setPreferredSize(new Dimension(300, 25));
	}
	
	private void configureButtonPrevious() {
		this.buttonPrevious = new JButton();
		this.buttonPrevious.setIcon(GuiHelper.createIcon("img/left-play.png"));
		this.buttonPrevious.setText(this.locale.getMessage("gui.frame.configuration.wizard.previous"));
		this.buttonPrevious.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) getParent().getLayout();
				cardLayout.show(getParent(), SecurePanel.ID);
			}
		});
	}
	
	private void configureButtonNext() {
		this.buttonNext = new JButton();
		this.buttonNext.setIcon(GuiHelper.createIcon("img/right-play.png"));
		this.buttonNext.setText(this.locale.getMessage("gui.frame.configuration.wizard.next"));
		this.buttonNext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String message = applyValidation();
				if (message != null) {
					String title = locale.getMessage("gui.frame.configuration.wizard.validation.title");
					JOptionPane.showMessageDialog(getParent(), message, title, JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				CardLayout cardLayout = (CardLayout) getParent().getLayout();
				cardLayout.show(getParent(), RepositoryPanel.ID);
			}
		});
	}
	
	private JPanel createBody() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		
		JPanel panelTitle = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JLabel title = new JLabel(this.locale.getMessage("gui.frame.configuration.wizard.database.title"));
		Font font = title.getFont();
		title.setFont(new Font(font.getName(), font.getStyle(), 16));
		panelTitle.add(title);
		panel.add(panelTitle);
		
		JPanel panelUserName = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		panelUserName.add(new JLabel(this.locale.getMessage("gui.frame.configuration.wizard.user.name")));
		panelUserName.add(this.textFieldUserName);
		panel.add(panelUserName);
		
		JPanel panelUserPassword = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		panelUserPassword.add(new JLabel(this.locale.getMessage("gui.frame.configuration.wizard.user.password")));
		panelUserPassword.add(this.passwordFieldUser);
		panel.add(panelUserPassword);
		
		panel.add(new JPanel());
		
		return panel;
	}
	
	private JPanel createBottom() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		
		JPanel cancelPanel = new JPanel(new FlowLayout());
		cancelPanel.add(this.buttonCancel);
		
		JPanel navigationPanel = new JPanel();
		navigationPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		navigationPanel.add(this.buttonPrevious);
		navigationPanel.add(this.buttonNext);
		
		panel.add(cancelPanel);
		panel.add(navigationPanel);
		
		return panel;
	}
}