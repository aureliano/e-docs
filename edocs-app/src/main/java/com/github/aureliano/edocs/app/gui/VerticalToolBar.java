package com.github.aureliano.edocs.app.gui;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

import com.github.aureliano.edocs.app.cmd.NewDocumentCommand;
import com.github.aureliano.edocs.app.helper.GuiHelper;
import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class VerticalToolBar extends JToolBar {

	private static final long serialVersionUID = -176640015146742392L;

	private JButton buttonFindDocuments;
	private JButton buttonNewDocument;
	
	public VerticalToolBar() {
		super.setFloatable(false);
		super.setOrientation(JToolBar.VERTICAL);
		super.setMargin(new Insets(10, 5, 5, 5));
		
		this.configureButtons();
		
		super.add(this.buttonFindDocuments);
		super.add(this.buttonNewDocument);
	}

	private void configureButtons() {
		EdocsLocale locale = EdocsLocale.instance();
		
		this.buttonFindDocuments = new JButton();
		this.buttonFindDocuments.setIcon(GuiHelper.createIcon("img/search.png"));
		this.buttonFindDocuments.setToolTipText(locale.getMessage("gui.toolbar.vertical.button.find.document.tooltip"));
		
		this.buttonNewDocument = new JButton();
		this.buttonNewDocument.setIcon(GuiHelper.createIcon("img/documents.png"));
		this.buttonNewDocument.setToolTipText(locale.getMessage("gui.toolbar.vertical.button.new.document.tooltip"));
		this.buttonNewDocument.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new NewDocumentCommand().execute();
			}
		});
	}
}