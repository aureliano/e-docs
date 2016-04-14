package com.github.aureliano.edocs.app.gui.panel;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.github.aureliano.edocs.app.model.ComboBoxItemModel;
import com.github.aureliano.edocs.common.locale.EdocsLocale;
import com.github.aureliano.edocs.domain.entity.Category;
import com.github.aureliano.edocs.domain.entity.User;
import com.github.aureliano.edocs.service.bean.UserServiceBean;

public class SaveDocumentPanel extends JPanel {

	private static final long serialVersionUID = 2728249172386578330L;

	private EdocsLocale locale;
	
	private JTextField textFieldName;
	private JComboBox<ComboBoxItemModel<Category>> comboBoxCategory;
	private JTextArea textAreaDescription;
	private JTextField textFieldDueDate;
	private JComboBox<ComboBoxItemModel<User>> comboBoxOwner;
	
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
		this.fillCategoryComboBox();
		
		super.add(new JLabel("Description"));
		super.add(this.textAreaDescription);
		
		super.add(new JLabel("Due date"));
		super.add(this.textFieldDueDate);
		
		super.add(new JLabel("Owner"));
		super.add(this.comboBoxOwner);
		this.fillOwnerComboBox();
	}
	
	private void initializeGui() {
		this.textFieldName = new JTextField();
		this.comboBoxCategory = new JComboBox<>();
		this.textAreaDescription = new JTextArea();
		this.textFieldDueDate = new JTextField();
		this.comboBoxOwner = new JComboBox<>();
	}
	
	private void fillCategoryComboBox() {
		List<ComboBoxItemModel<Category>> items = new ArrayList<>();
		items.add(new ComboBoxItemModel<Category>("gui.combo.box.item.empty", null));
		
		for (Category category : Category.values()) {
			String key = "domain.enum.category." + category.name().toLowerCase();
			items.add(new ComboBoxItemModel<Category>(key, category));
		}
		
		@SuppressWarnings("unchecked")
		ComboBoxItemModel<Category>[] data = items.toArray(new ComboBoxItemModel[0]);
		ComboBoxModel<ComboBoxItemModel<Category>> model = new DefaultComboBoxModel<>(data);
		
		this.comboBoxCategory.setModel(model);
	}
	
	private void fillOwnerComboBox() {
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
		
		this.comboBoxOwner.setModel(model);
	}
}