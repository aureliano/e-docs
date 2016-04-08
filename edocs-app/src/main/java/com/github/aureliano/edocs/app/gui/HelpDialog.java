package com.github.aureliano.edocs.app.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.github.aureliano.edocs.common.exception.EDocsException;

public class HelpDialog extends JDialog {

	private static final long serialVersionUID = -3369353227801827761L;

	private Properties properties;
	
	public HelpDialog(Frame parent) {
		super(parent);
		this.loadMetadata();
		this.buildGui(parent);
	}
	
	private void loadMetadata() {
		this.properties = new Properties();
		try(InputStream stream = ClassLoader.getSystemResourceAsStream("metadata.properties")) {
			properties.load(stream);
		} catch (IOException ex) {
			throw new EDocsException(ex);
		}
	}

	private void buildGui(Component parent) {
		JLabel labelTitle = this.createLabelTitle();
		JLabel labelDescription = this.createLabelDescription();
		JLabel labelVersion = this.createLabelVersion();
		JLabel labelCopyright = this.createLabelCopyright();
		JButton buttonOk = this.createButtonOk();
		
		this.createLayout(labelTitle, labelDescription, labelVersion, labelCopyright, buttonOk);

		super.setLocationRelativeTo(parent);
		super.setTitle("About e-Docs");
		super.setModal(true);
	}
	
	private void createLayout(JComponent... components) {
		JPanel panel = new JPanel(null);
		for (JComponent component : components) {
			panel.add(component);
		}
		
		super.pack();
		
		super.setContentPane(panel);
		super.setSize(new Dimension(320, 230));
		super.setResizable(false);
	}
	
	private JLabel createLabelTitle() {
		JLabel label = new JLabel("e-Docs");
		label.setBounds(120, 10, 100, 25);
		label.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		
		return label;
	}
	
	private JLabel createLabelDescription() {
		String text = new StringBuilder("<html>")
			.append("<p align=\"center\">")
			.append(this.properties.getProperty("app.description"))
			.append("</p>")
			.append("</html>")
			.toString();
		JLabel label = new JLabel(text);
		label.setBounds(50, 50, 300, 30);
		
		return label;
	}
	
	private JLabel createLabelVersion() {
		JLabel label = new JLabel("Version " + this.properties.getProperty("app.version"));
		label.setBounds(110, 90, 210, 25);
		
		return label;
	}
	
	private JLabel createLabelCopyright() {
		String text = new StringBuilder("<html>")
			.append("<p align=\"center\">")
			.append(this.properties.getProperty("app.copyright"))
			.append("</p>")
			.append("</html>")
			.toString();
		JLabel label = new JLabel(text);
		label.setBounds(25, 120, 400, 30);
		
		return label;
	}

	private JButton createButtonOk() {
		JButton button = new JButton("OK");
		button.setBounds(210, 170, 100, 25);
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		return button;
	}
}