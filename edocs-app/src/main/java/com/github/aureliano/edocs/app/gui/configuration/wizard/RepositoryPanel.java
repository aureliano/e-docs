package com.github.aureliano.edocs.app.gui.configuration.wizard;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.aureliano.edocs.app.EdocsApp;
import com.github.aureliano.edocs.app.helper.GuiHelper;
import com.github.aureliano.edocs.app.model.ComboBoxItemModel;
import com.github.aureliano.edocs.common.locale.EdocsLocale;
import com.github.aureliano.edocs.file.repository.RepositoryType;

public class RepositoryPanel extends JPanel {

	private static final long serialVersionUID = -2987262533751437254L;
	public static final String ID = "REPOSITORY_CARD";
	private static final String HOME = new File("").getAbsolutePath();

	private EdocsLocale locale;
	
	private JFileChooser fileChooser;
	private JComboBox<ComboBoxItemModel<RepositoryType>> comboBoxRepositoryTypes;
	private JTextField textFieldRepositoryFileDir;
	private JButton buttonRepositoryFileDir;
	private JTextField textFieldLimboDir;
	private JButton buttonLimboDir;
	private JButton buttonCancel;
	private JButton buttonPrevious;
	
	public RepositoryPanel() {
		this.locale = EdocsLocale.instance();
		this.buildGui();
	}

	private void buildGui() {
		this.configureFileChooser();
		this.configureTextFieldRepositoryFileDir();
		this.configureTextFieldLimboDir();
		this.configureComboBoxRepositoryTypes();
		this.configureButtonLimboDir();
		this.configureButtonRepositoryFileDir();
		this.configureButtonCancel();
		this.configureButtonPrevious();
		
		super.setLayout(new BorderLayout());
		super.add(this.createBody(), BorderLayout.CENTER);
		super.add(this.createBottom(), BorderLayout.SOUTH);
	}
	
	private void configureFileChooser() {
		this.fileChooser = new JFileChooser(HOME);
		this.fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    this.fileChooser.setAcceptAllFileFilterUsed(false);
	}
	
	private void configureComboBoxRepositoryTypes() {
		
		List<ComboBoxItemModel<RepositoryType>> items = new ArrayList<>();
		items.add(new ComboBoxItemModel<RepositoryType>("gui.combo.box.item.empty", null));
		
		for (RepositoryType type : RepositoryType.values()) {
			String key = "repository.type.enum." + type.name().toLowerCase();
			items.add(new ComboBoxItemModel<RepositoryType>(key, type));
		}
		
		@SuppressWarnings("unchecked")
		ComboBoxItemModel<RepositoryType>[] data = items.toArray(new ComboBoxItemModel[0]);
		ComboBoxModel<ComboBoxItemModel<RepositoryType>> model = new DefaultComboBoxModel<>(data);
		
		this.comboBoxRepositoryTypes = new JComboBox<>(model);
		int height = (int) this.comboBoxRepositoryTypes.getPreferredSize().getHeight();
		this.comboBoxRepositoryTypes.setPreferredSize(new Dimension(320, height));
	}
	
	private void configureTextFieldRepositoryFileDir() {
		this.textFieldRepositoryFileDir = new JTextField();
		this.textFieldRepositoryFileDir.setPreferredSize(new Dimension(265, 25));
		this.textFieldRepositoryFileDir.setText(HOME);
		this.textFieldRepositoryFileDir.setEditable(false);
	}
	
	private void configureButtonRepositoryFileDir() {
		this.buttonRepositoryFileDir = new JButton();
		this.buttonRepositoryFileDir.setIcon(GuiHelper.createIcon("img/folder-16.png"));
		this.buttonRepositoryFileDir.setToolTipText(this.locale.getMessage("gui.frame.configuration.wizard.repository.file.dir"));
		
		this.buttonRepositoryFileDir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int option = fileChooser.showOpenDialog(getParent());
				if (option == JFileChooser.APPROVE_OPTION) {
					String path = fileChooser.getSelectedFile().getAbsolutePath();
					textFieldRepositoryFileDir.setText(path);
				}
			}
		});
	}
	
	private void configureTextFieldLimboDir() {
		this.textFieldLimboDir = new JTextField();
		this.textFieldLimboDir.setPreferredSize(new Dimension(265, 25));
		this.textFieldLimboDir.setText(HOME);
		this.textFieldLimboDir.setEditable(false);
	}
	
	private void configureButtonLimboDir() {
		this.buttonLimboDir = new JButton();
		this.buttonLimboDir.setIcon(GuiHelper.createIcon("img/folder-16.png"));
		this.buttonLimboDir.setToolTipText(this.locale.getMessage("gui.frame.configuration.wizard.limbo.dir"));
		
		this.buttonLimboDir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int option = fileChooser.showOpenDialog(getParent());
				if (option == JFileChooser.APPROVE_OPTION) {
					String path = fileChooser.getSelectedFile().getAbsolutePath();
					textFieldLimboDir.setText(path);
				}
			}
		});
	}
	
	private void configureButtonCancel() {
		this.buttonCancel = new JButton(this.locale.getMessage("gui.frame.configuration.wizard.cancel"));
		this.buttonCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				EdocsApp.instance().getFrame().dispose();
				System.exit(0);
			}
		});
	}
	
	private void configureButtonPrevious() {
		this.buttonPrevious = new JButton();
		this.buttonPrevious.setIcon(GuiHelper.createIcon("img/left-play.png"));
		this.buttonPrevious.setText(this.locale.getMessage("gui.frame.configuration.wizard.previous"));
		this.buttonPrevious.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) getParent().getLayout();
				cardLayout.show(getParent(), DatabasePanel.ID);
			}
		});
	}
	
	private JPanel createBody() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		
		JPanel panelTitle = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JLabel title = new JLabel(this.locale.getMessage("gui.frame.configuration.wizard.repository.title"));
		Font font = title.getFont();
		title.setFont(new Font(font.getName(), font.getStyle(), 16));
		panelTitle.add(title);
		panel.add(panelTitle);
		
		JPanel panelRepositoryType = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		panelRepositoryType.add(new JLabel(this.locale.getMessage("gui.frame.configuration.wizard.repository.type")));
		panelRepositoryType.add(this.comboBoxRepositoryTypes);
		panel.add(panelRepositoryType);
		
		JPanel panelRepositoryFileDir = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		panelRepositoryFileDir.add(new JLabel(this.locale.getMessage("gui.frame.configuration.wizard.repository.file.dir")));
		panelRepositoryFileDir.add(this.textFieldRepositoryFileDir);
		panelRepositoryFileDir.add(this.buttonRepositoryFileDir);
		panel.add(panelRepositoryFileDir);
		
		JPanel panelLimboDir = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		panelLimboDir.add(new JLabel(this.locale.getMessage("gui.frame.configuration.wizard.limbo.dir")));
		panelLimboDir.add(this.textFieldLimboDir);
		panelLimboDir.add(this.buttonLimboDir);
		panel.add(panelLimboDir);
		
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
		
		panel.add(cancelPanel);
		panel.add(navigationPanel);
		
		return panel;
	}
}