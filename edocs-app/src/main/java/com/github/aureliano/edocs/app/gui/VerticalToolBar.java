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
	private JButton buttonTools;
	private JButton buttonFileRepository;
	private JButton buttonHelp;
	
	public VerticalToolBar() {
		super.setFloatable(false);
		super.setOrientation(JToolBar.VERTICAL);
		super.setMargin(new Insets(10, 5, 5, 5));
		
		this.configureButtons();
		
		super.add(this.buttonFindDocuments);
		super.add(this.buttonNewDocument);
		super.add(this.buttonTools);
		super.add(this.buttonFileRepository);
		super.add(this.buttonHelp);
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
		
		this.buttonTools = new JButton();
		this.buttonTools.setIcon(GuiHelper.createIcon("img/tools.png"));
		this.buttonTools.setToolTipText(locale.getMessage("gui.toolbar.vertical.button.tools.tooltip"));
		
		this.buttonFileRepository = new JButton();
		this.buttonFileRepository.setIcon(GuiHelper.createIcon("img/folder.png"));
		this.buttonFileRepository.setToolTipText(locale.getMessage("gui.toolbar.vertical.button.file.repository.tooltip"));
		
		this.buttonHelp = new JButton();
		this.buttonHelp.setIcon(GuiHelper.createIcon("img/help.png"));
		this.buttonHelp.setToolTipText(locale.getMessage("gui.toolbar.vertical.button.help.tooltip"));
	}
}