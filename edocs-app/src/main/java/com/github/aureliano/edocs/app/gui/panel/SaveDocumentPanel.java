package com.github.aureliano.edocs.app.gui.panel;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class SaveDocumentPanel extends JPanel {

	private static final long serialVersionUID = 2728249172386578330L;

	private EdocsLocale locale;
	
	private JTextField textFieldName;
	private JComboBox<Object> comboBoxCategory;
	private JTextArea textAreaDescription;
	private JTextField textFieldDueDate;
	private JComboBox<Object> comboBoxOwner;
	
	public SaveDocumentPanel() {
		this.locale = EdocsLocale.instance();
		this.buildGui();
	}

	private void buildGui() {
		this.initializeGui();
		super.setLayout(new GridLayout(0, 2));
		
		super.add(new JLabel("Name"));
		super.add(this.textFieldName);
		
		super.add(new JLabel("Category"));
		super.add(this.comboBoxCategory);
		
		super.add(new JLabel("Description"));
		super.add(this.textAreaDescription);
		
		super.add(new JLabel("Due date"));
		super.add(this.textFieldDueDate);
		
		super.add(new JLabel("Owner"));
		super.add(this.comboBoxOwner);
	}
	
	private void initializeGui() {
		this.textFieldName = new JTextField();
		this.comboBoxCategory = new JComboBox<>();
		this.textAreaDescription = new JTextArea();
		this.textFieldDueDate = new JTextField();
		this.comboBoxOwner = new JComboBox<>();
	}
}