package com.github.aureliano.edocs.app.gui;

import javax.swing.JTabbedPane;

public class TabbedPane extends JTabbedPane {

	private static final long serialVersionUID = -2115756515254997652L;

	public TabbedPane() {
		super.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}
}