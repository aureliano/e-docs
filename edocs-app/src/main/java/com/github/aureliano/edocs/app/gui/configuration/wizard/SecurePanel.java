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

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import com.github.aureliano.edocs.app.EdocsApp;
import com.github.aureliano.edocs.app.helper.GuiHelper;
import com.github.aureliano.edocs.app.model.ComboBoxItemModel;
import com.github.aureliano.edocs.common.locale.EdocsLocale;
import com.github.aureliano.edocs.secure.hash.SaltGenerator;
import com.github.aureliano.edocs.secure.model.Algorithm;

public class SecurePanel extends JPanel {

	private static final long serialVersionUID = -5014710216687095637L;
	public static final String ID = "SECURE_CARD";

	private EdocsLocale locale;
	
	private JComboBox<ComboBoxItemModel<Algorithm>> comboBoxAlgorithms;
	private JTextField textFieldSalt;
	private JButton buttonGenerateSalt;
	private JSpinner spinnerHashIterations;
	
	private JButton buttonCancel;
	private JButton buttonNext;
	
	public SecurePanel() {
		this.locale = EdocsLocale.instance();
		this.buildGui();
	}

	private void buildGui() {
		this.configureComboBoxAlgorithms();
		this.configureTextFieldSalt();
		this.configureButtonGenerateSalt();
		this.configureSliderHashIterations();
		this.configureButtonCancel();
		this.configureButtonNext();
		
		super.setLayout(new BorderLayout());
		super.add(this.createBody(), BorderLayout.CENTER);
		super.add(this.createBottom(), BorderLayout.SOUTH);
	}
	
	private void configureComboBoxAlgorithms() {
		
		List<ComboBoxItemModel<Algorithm>> items = new ArrayList<>();
		items.add(new ComboBoxItemModel<Algorithm>("gui.combo.box.item.empty", null));
		
		for (Algorithm algorithm : Algorithm.values()) {
			String key = "secure.enum.algorithm." + algorithm.name().toLowerCase();
			items.add(new ComboBoxItemModel<Algorithm>(key, algorithm));
		}
		
		@SuppressWarnings("unchecked")
		ComboBoxItemModel<Algorithm>[] data = items.toArray(new ComboBoxItemModel[0]);
		ComboBoxModel<ComboBoxItemModel<Algorithm>> model = new DefaultComboBoxModel<>(data);
		
		this.comboBoxAlgorithms = new JComboBox<>(model);
		int height = (int) this.comboBoxAlgorithms.getPreferredSize().getHeight();
		this.comboBoxAlgorithms.setPreferredSize(new Dimension(235, height));
	}
	
	private void configureTextFieldSalt() {
		this.textFieldSalt = new JTextField();
		this.textFieldSalt.setPreferredSize(new Dimension(180, 25));
	}
	
	private void configureButtonGenerateSalt() {
		this.buttonGenerateSalt = new JButton();
		this.buttonGenerateSalt.setIcon(GuiHelper.createIcon("img/play.png"));
		this.buttonGenerateSalt.setToolTipText(this.locale.getMessage("gui.frame.configuration.wizard.button.generate.salt.number"));
		
		this.buttonGenerateSalt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String salt = SaltGenerator.generate(20);
				textFieldSalt.setText(salt);
			}
		});
	}
	
	private void configureSliderHashIterations() {
		SpinnerModel model = new SpinnerNumberModel(15, 1, 50, 1);
		this.spinnerHashIterations = new JSpinner(model);
		this.spinnerHashIterations.setPreferredSize(new Dimension(235, 25));
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
	
	private void configureButtonNext() {
		this.buttonNext = new JButton();
		this.buttonNext.setIcon(GuiHelper.createIcon("img/right-play.png"));
		this.buttonNext.setText(this.locale.getMessage("gui.frame.configuration.wizard.next"));
		this.buttonNext.addActionListener(new ActionListener() {
			
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
		JLabel title = new JLabel(this.locale.getMessage("gui.frame.configuration.wizard.secure.title"));
		Font font = title.getFont();
		title.setFont(new Font(font.getName(), font.getStyle(), 16));
		panelTitle.add(title);
		panel.add(panelTitle);
		
		JPanel panelAlgorithm = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		panelAlgorithm.add(new JLabel(this.locale.getMessage("gui.frame.configuration.wizard.algorithm")));
		panelAlgorithm.add(this.comboBoxAlgorithms);
		panel.add(panelAlgorithm);
		
		JPanel panelSalt = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		panelSalt.add(new JLabel(this.locale.getMessage("gui.frame.configuration.wizard.salt.number")));
		panelSalt.add(this.textFieldSalt);
		panelSalt.add(this.buttonGenerateSalt);
		panel.add(panelSalt);
		
		JPanel panelIterations = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		panelIterations.add(new JLabel(this.locale.getMessage("gui.frame.configuration.wizard.hash.iterations")));
		panelIterations.add(this.spinnerHashIterations);
		panel.add(panelIterations);
		
		return panel;
	}
	
	private JPanel createBottom() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 2));
		
		JPanel cancelPanel = new JPanel(new FlowLayout());
		cancelPanel.add(this.buttonCancel);
		
		JPanel navigationPanel = new JPanel();
		navigationPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		navigationPanel.add(this.buttonNext);
		
		panel.add(cancelPanel);
		panel.add(navigationPanel);
		
		return panel;
	}
}