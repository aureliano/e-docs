package com.github.aureliano.edocs.app;

import javax.swing.SwingUtilities;

import com.github.aureliano.edocs.app.gui.AppFrame;

public class EdocsApp {

	private AppFrame frame;
	
	public EdocsApp() {
		this.frame = new AppFrame();
	}
	
	public AppFrame getFrame() {
		return this.frame;
	}
	
	public static void main(String[] args) {
		final EdocsApp application = new EdocsApp();
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				application.getFrame().showFrame();
			}
		});
	}
}