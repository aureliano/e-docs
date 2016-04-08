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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.github.aureliano.edocs.common.exception.EDocsException;

public class LicenseDialog extends JDialog {

	private static final long serialVersionUID = 8561875072232916684L;

	private Properties properties;
	
	public LicenseDialog(Frame parent) {
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
		JScrollPane paneLicense = this.createScrollPaneLicense();
		JButton buttonOk = this.createButtonOk();
		
		this.createLayout(labelTitle, paneLicense, buttonOk);

		buttonOk.grabFocus();
		
		super.setLocationRelativeTo(parent);
		super.setTitle("License");
		super.setModal(true);
		
		super.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	private void createLayout(JComponent... components) {
		JPanel panel = new JPanel(null);
		for (JComponent component : components) {
			panel.add(component);
		}
		
		super.pack();
		
		super.setContentPane(panel);
		super.setSize(new Dimension(400, 390));
		super.setResizable(false);
	}
	
	private JLabel createLabelTitle() {
		JLabel label = new JLabel("e-Docs");
		label.setBounds(160, 10, 100, 25);
		label.setFont(new Font(Font.SERIF, Font.BOLD, 18));
		
		return label;
	}
	
	private JScrollPane createScrollPaneLicense() {
		JScrollPane pane = new JScrollPane();
		pane.setBounds(15, 50, 370, 270);
		pane.setAutoscrolls(false);
		pane.getViewport().add(this.createTextAreaLicense());
		
		return pane;
	}

	private JTextArea createTextAreaLicense() {
		JTextArea area = new JTextArea();
		
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		area.setText(this.properties.getProperty("project.license"));
		area.setCaretPosition(0);
		area.setEditable(false);
		
		return area;
	}

	private JButton createButtonOk() {
		JButton button = new JButton("OK");
		button.setBounds(290, 330, 100, 25);
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		return button;
	}
}