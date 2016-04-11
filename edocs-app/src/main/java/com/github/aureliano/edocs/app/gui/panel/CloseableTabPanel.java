package com.github.aureliano.edocs.app.gui.panel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicButtonUI;

import com.github.aureliano.edocs.common.locale.EdocsLocale;

public class CloseableTabPanel extends JPanel {

	private static final long serialVersionUID = 1660016808846804233L;

	private EdocsLocale locale;
	
	private JTabbedPane tabbedPane;
	private MouseListener buttonMouseListener;
	
	public CloseableTabPanel(final JTabbedPane tabbedPane) {
		super(new FlowLayout(FlowLayout.LEFT, 0, 0));
		super.setOpaque(false);
		
		locale = EdocsLocale.instance();
		this.tabbedPane = tabbedPane;
		this.buttonMouseListener = this.createMouseAdapter();
		
		JLabel labelTitle = this.createLabelTitle();		
		labelTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
		
		super.add(labelTitle);
		super.add(new TabButton());
		super.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
	}
	
	private JLabel createLabelTitle() {
		return new JLabel() {
			
			private static final long serialVersionUID = 443108450248106984L;

			public String getText() {
				int index = tabbedPane.getSelectedIndex();
				if (index != -1) {
					return tabbedPane.getTitleAt(index);
				}
				
				return null;
			}
		};
	}
	
	private MouseListener createMouseAdapter() {
		return new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				Component component = e.getComponent();
				if (component instanceof AbstractButton) {
					AbstractButton button = (AbstractButton) component;
					button.setBorderPainted(true);
				}
			}
			
			public void mouseExited(MouseEvent e) {
				Component component = e.getComponent();
				if (component instanceof AbstractButton) {
					AbstractButton button = (AbstractButton) component;
					button.setBorderPainted(false);
				}
			}
		};
	}
	
	private class TabButton extends JButton implements ActionListener {
		
		private static final long serialVersionUID = 7686250539542131392L;

		public TabButton() {
			int size = 17;
			setPreferredSize(new Dimension(size, size));
			setToolTipText(locale.getMessage("gui.frame.main.tab.button.close"));

			setUI(new BasicButtonUI());
			setContentAreaFilled(false);

			setFocusable(false);
			setBorder(BorderFactory.createEtchedBorder());
			setBorderPainted(false);

			addMouseListener(buttonMouseListener);
			setRolloverEnabled(true);

			addActionListener(this);
		}
	
	public void actionPerformed(ActionEvent e) {
		int i = tabbedPane.indexOfTabComponent(CloseableTabPanel.this);
		if (i != -1) {
			tabbedPane.remove(i);
		}
	}
	
	// Don't update UI for this button.
	public void updateUI() {}
	 
	// Paint the cross.
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g.create();

			if (getModel().isPressed()) {
				g2.translate(1, 1);
			}
			
			g2.setStroke(new BasicStroke(2));
			g2.setColor(Color.BLACK);
			
			if (getModel().isRollover()) {
				g2.setColor(Color.MAGENTA);
			}
			
			int delta = 6;
			g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
			g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
			g2.dispose();
		}
	}
}