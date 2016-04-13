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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import com.github.aureliano.edocs.app.EdocsApp;
import com.github.aureliano.edocs.app.helper.DatabaseHelper;
import com.github.aureliano.edocs.app.helper.GuiHelper;
import com.github.aureliano.edocs.app.model.ComboBoxItemModel;
import com.github.aureliano.edocs.common.config.AppConfiguration;
import com.github.aureliano.edocs.common.config.DatabaseConfiguration;
import com.github.aureliano.edocs.common.exception.EDocsException;
import com.github.aureliano.edocs.common.helper.ConfigurationHelper;
import com.github.aureliano.edocs.common.helper.StringHelper;
import com.github.aureliano.edocs.common.locale.EdocsLocale;
import com.github.aureliano.edocs.file.repository.RepositoryType;

public class RepositoryPanel extends JPanel {

	private static final long serialVersionUID = -2987262533751437254L;
	private static final Logger logger = Logger.getLogger(RepositoryPanel.class.getName());
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
	private JButton buttonFinish;
	private JProgressBar progressBar;
	
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
		this.configureProgressBar();
		
		this.buttonCancel = ConfigurationWizardDialog.createButtonCancel();
		this.configureButtonPrevious();
		this.configureButtonFinish();
		
		super.setLayout(new BorderLayout());
		super.add(this.createBody(), BorderLayout.CENTER);
		super.add(this.createBottom(), BorderLayout.SOUTH);
	}
	
	private String applyValidation() {
		List<String> messages = new ArrayList<>();
		
		if (this.getRepositoryType() == null) {
			messages.add(this.locale.getMessage("gui.frame.configuration.wizard.repository.validation.type"));
		}
		
		if (!this.getRepositoryFile().isDirectory()) {
			messages.add(this.locale.getMessage("gui.frame.configuration.wizard.repository.validation.file"));
		}
		
		if (!this.getLimboFile().isDirectory()) {
			messages.add(this.locale.getMessage("gui.frame.configuration.wizard.repository.validation.limbo"));
		}
		
		return (messages.isEmpty()) ? null : StringHelper.join(messages, "\n");
	}
	
	public RepositoryType getRepositoryType() {
		@SuppressWarnings("unchecked")
		ComboBoxItemModel<RepositoryType> repositoryType = (ComboBoxItemModel<RepositoryType>) this.comboBoxRepositoryTypes.getSelectedItem();
		return repositoryType.getValue();
	}
	
	public File getRepositoryFile() {
		return new File(this.textFieldRepositoryFileDir.getText());
	}
	
	public File getLimboFile() {
		return new File(this.textFieldLimboDir.getText());
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
	
	private void configureButtonFinish() {
		this.buttonFinish = new JButton();
		this.buttonFinish.setText(this.locale.getMessage("gui.frame.configuration.wizard.finish"));
		this.buttonFinish.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String message = applyValidation();
				if (message != null) {
					String title = locale.getMessage("gui.frame.configuration.wizard.validation.title");
					JOptionPane.showMessageDialog(getParent(), message, title, JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				new ConfigurationWorker().execute();
			}
		});
	}
	
	private void configureProgressBar() {
		this.progressBar = new JProgressBar();
		this.progressBar.setIndeterminate(true);
	}
	
	private void applyConfiguration() {
		this.inProgress();
		AppConfiguration configuration = GuiHelper.buildConfigurationFromWizard(super.getParent().getComponents());
		String comments = new StringBuilder()
			.append("e-Docs configuration created by ")
			.append(configuration.getDatabaseConfiguration().getUser())
			.append(" from application.")
			.toString();
		
		try {
			ConfigurationHelper.saveConfigurationFile(configuration, comments);
			DatabaseConfiguration db = configuration.getDatabaseConfiguration();
			DatabaseHelper.prepareDatabase(db.getUser(), db.getPassword());
			
			this.progressDone();
			EdocsApp.instance().getFrame().getToolBar().setDatabaseButtonsEnabled(true);
			GuiHelper.getFrame(super.getParent()).dispose();
		} catch (EDocsException ex) {
			logger.log(Level.SEVERE, ex.getMessage(), ex);
			
			String title = this.locale.getMessage("gui.frame.configuration.wizard.apply.error.title");
			String message = this.locale.getMessage("gui.frame.configuration.wizard.save.file.error");
			JOptionPane.showMessageDialog(super.getParent(), message, title, JOptionPane.ERROR_MESSAGE);
			this.progressDone();
		}
	}
	
	private void inProgress() {
		GuiHelper.getFrame(super.getParent()).setSize(450, 240);
		JPanel panel = (JPanel) super.getComponent(0);
		panel.add(this.progressBar);
		
		this.comboBoxRepositoryTypes.setEnabled(false);
		this.setButtonsEnabled(false);
	}
	
	private void progressDone() {
		JPanel panel = (JPanel) super.getComponent(0);
		panel.add(this.progressBar);
		GuiHelper.getFrame(super.getParent()).setSize(450, 200);

		this.comboBoxRepositoryTypes.setEnabled(true);
		this.setButtonsEnabled(true);
	}
	
	private void setButtonsEnabled(boolean enabled) {
		this.buttonRepositoryFileDir.setEnabled(enabled);
		this.buttonLimboDir.setEnabled(enabled);
		this.buttonCancel.setEnabled(enabled);
		this.buttonPrevious.setEnabled(enabled);
		this.buttonFinish.setEnabled(enabled);
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
		navigationPanel.add(this.buttonFinish);
		
		panel.add(cancelPanel);
		panel.add(navigationPanel);
		
		return panel;
	}
	
	private class ConfigurationWorker extends SwingWorker<Void, Void> {

		@Override
		protected Void doInBackground() throws Exception {
			applyConfiguration();
			return null;
		}
	}
}