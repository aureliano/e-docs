package com.github.aureliano.edocs.app.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HelpDialog extends JDialog {

	private static final long serialVersionUID = -3369353227801827761L;

	public HelpDialog(Frame parent) {
		super(parent);
		this.buildGui(parent);
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
			.append("A digital document repository")
			.append("<br/>")
			.append("management application.")
			.append("</p>")
			.append("</html>")
			.toString();
		JLabel label = new JLabel(text);
		label.setBounds(50, 50, 300, 30);
		
		return label;
	}
	
	private JLabel createLabelVersion() {
		JLabel label = new JLabel("Version 0.1.0");
		label.setBounds(110, 90, 210, 25);
		
		return label;
	}
	
	private JLabel createLabelCopyright() {
		String text = new StringBuilder("<html>")
			.append("<p align=\"center\">")
			.append("(c) Copyright e-Docs contributors")
			.append("<br/>")
			.append("and others 2016. All rights reserved.")
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