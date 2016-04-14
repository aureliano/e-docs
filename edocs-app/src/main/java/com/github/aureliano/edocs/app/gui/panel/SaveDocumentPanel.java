package com.github.aureliano.edocs.app.gui.panel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.github.aureliano.edocs.app.cmd.CloseActiveTabCommand;
import com.github.aureliano.edocs.app.cmd.ICommand;
import com.github.aureliano.edocs.app.model.ComboBoxItemModel;
import com.github.aureliano.edocs.common.locale.EdocsLocale;
import com.github.aureliano.edocs.domain.entity.Category;
import com.github.aureliano.edocs.domain.entity.Document;
import com.github.aureliano.edocs.domain.entity.User;
import com.github.aureliano.edocs.service.bean.UserServiceBean;

import net.sourceforge.jdatepicker.JDatePicker;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class SaveDocumentPanel extends JPanel {

	private static final long serialVersionUID = 2728249172386578330L;

	private EdocsLocale locale;
	
	private Document document;
	private boolean changed;
	
	private JTextField textFieldName;
	private JComboBox<ComboBoxItemModel<Category>> comboBoxCategory;
	private JTextArea textAreaDescription;
	private JDatePicker datePickerDueDate;
	private JComboBox<ComboBoxItemModel<User>> comboBoxOwner;
	
	private JButton buttonAttach;
	private JButton buttonSave;
	private JButton buttonCancel;
	
	public SaveDocumentPanel() {
		this.locale = EdocsLocale.instance();
		this.document = new Document();
		this.changed = false;
		
		this.buildGui();
	}

	private void buildGui() {
		this.configureTextFieldName();
		this.configureComboBoxCategory();
		this.configureTextAreaDescription();
		this.configureDatePickerDueDate();
		this.configureComboBoxOwner();
		
		this.configureButtonAttach();
		this.configureButtonSave();
		this.configureButtonCancel();
		
		super.setLayout(new BorderLayout());
		super.add(this.createTop(), BorderLayout.NORTH);
		super.add(this.createBody(), BorderLayout.CENTER);
		super.add(this.createBottom(), BorderLayout.SOUTH);
	}
	
	private JPanel createTop() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		
		JPanel panelTitle = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JLabel title = new JLabel(this.locale.getMessage("gui.frame.main.tab.panel.document.save.title"));
		Font font = title.getFont();
		title.setFont(new Font(font.getName(), font.getStyle(), 16));
		panelTitle.add(title);
		panel.add(panelTitle);
		
		return panel;
	}
	
	private JPanel createBody() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.insets = new Insets(10, 0, 0, 0);
		
		List<Component> components = Arrays.asList(
				this.textFieldName, this.comboBoxCategory, this.textAreaDescription,
				(Component) this.datePickerDueDate, this.comboBoxOwner);
		this.addPanels(panel, c, components.toArray(new Component[0]));

		return panel;
	}
	
	private Component createBottom() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panel.add(this.buttonAttach);
		panel.add(this.buttonSave);
		panel.add(this.buttonCancel);
		
		return panel;
	}
	
	private void addPanels(JPanel panel, GridBagConstraints c, Component[] components) {
		for (byte y = 0; y < components.length; y++) {
			c.gridy = y;
			Component component = components[y];
			
			for (byte x = 0; x < 2; x++) {
				c.gridx = x;
				
				if (x == 0) {
					JLabel label = new JLabel(this.locale.getMessage(component.getName()));
					panel.add(label, c);
				} else {
					panel.add(component, c);
				}
			}
		}
	}

	private void configureTextFieldName() {
		this.textFieldName = new JTextField();
		this.textFieldName.setPreferredSize(new Dimension(200, 25));
		this.textFieldName.setName("gui.frame.main.tab.panel.document.save.name");
		this.textFieldName.setText(this.document.getName());
		
		this.textFieldName.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				changed = !textFieldName.getText().equals(document.getName());
				System.out.println("Removed: " + changed);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				changed = !textFieldName.getText().equals(document.getName());
				System.out.println("Inserted: " + changed);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {}
		});
	}
	
	private void configureComboBoxCategory() {
		List<ComboBoxItemModel<Category>> items = new ArrayList<>();
		items.add(new ComboBoxItemModel<Category>("gui.combo.box.item.empty", null));
		
		for (Category category : Category.values()) {
			String key = "domain.enum.category." + category.name().toLowerCase();
			items.add(new ComboBoxItemModel<Category>(key, category));
		}
		
		@SuppressWarnings("unchecked")
		ComboBoxItemModel<Category>[] data = items.toArray(new ComboBoxItemModel[0]);
		ComboBoxModel<ComboBoxItemModel<Category>> model = new DefaultComboBoxModel<>(data);
		
		this.comboBoxCategory = new JComboBox<>();
		this.comboBoxCategory.setModel(model);
		this.comboBoxCategory.setName("gui.frame.main.tab.panel.document.save.category");
	}
	
	private void configureTextAreaDescription() {
		this.textAreaDescription = new JTextArea();
		this.textAreaDescription.setRows(5);
		this.textAreaDescription.setName("gui.frame.main.tab.panel.document.save.description");
	}
	
	private void configureDatePickerDueDate() {
		UtilDateModel model = new UtilDateModel(new Date());
		JDatePanelImpl panel = new JDatePanelImpl(model);
		this.datePickerDueDate = new JDatePickerImpl(panel);
		((Component) this.datePickerDueDate).setName("gui.frame.main.tab.panel.document.save.due_date");
	}
	
	private void configureComboBoxOwner() {
		List<ComboBoxItemModel<User>> items = new ArrayList<>();
		items.add(new ComboBoxItemModel<User>("gui.combo.box.item.empty", null));
		
		for (User user : new UserServiceBean().listUsers()) {
			items.add(new ComboBoxItemModel<User>(null, user) {
				public String toString() {
					return this.getValue().getName();
				}
			});
		}

		@SuppressWarnings("unchecked")
		ComboBoxItemModel<User>[] data = items.toArray(new ComboBoxItemModel[0]);
		ComboBoxModel<ComboBoxItemModel<User>> model = new DefaultComboBoxModel<>(data);
		
		this.comboBoxOwner = new JComboBox<>();
		this.comboBoxOwner.setModel(model);
		this.comboBoxOwner.setName("gui.frame.main.tab.panel.document.save.owner");
	}
	
	private void configureButtonAttach() {
		this.buttonAttach = new JButton();
		this.buttonAttach.setText(this.locale.getMessage("gui.frame.main.tab.panel.document.save.button.attach"));
		this.buttonAttach.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}
	
	private void configureButtonSave() {
		this.buttonSave = new JButton();
		this.buttonSave.setText(this.locale.getMessage("gui.frame.main.tab.panel.document.save.button.save"));
		this.buttonSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}
	
	private void configureButtonCancel() {
		this.buttonCancel = new JButton();
		this.buttonCancel.setText(this.locale.getMessage("gui.frame.main.tab.panel.document.save.button.cancel"));
		this.buttonCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ICommand command = new CloseActiveTabCommand();
				
				if (changed) {
					int option = showConfirmQuitMessage();
					if (JOptionPane.NO_OPTION == option) {
						return;
					}
				}
				
				command.execute();
			}
		});
	}
	
	private int showConfirmQuitMessage() {
		String title = this.locale.getMessage("gui.frame.main.tab.panel.document.save.confirm.title");
		String message = this.locale.getMessage("gui.frame.main.tab.panel.document.save.confirm.message");
		Object[] options = new Object[] {
			this.locale.getMessage("gui.message.yes"),
			this.locale.getMessage("gui.message.no")
		};
		String initialValue = this.locale.getMessage("gui.message.no");
		
		return JOptionPane.showOptionDialog(
			super.getParent(), message, title, JOptionPane.YES_NO_OPTION,
			JOptionPane.QUESTION_MESSAGE, null, options, initialValue
		);
	}
}