package com.github.aureliano.edocs.app;

import javax.swing.SwingUtilities;

import com.github.aureliano.edocs.app.gui.AppFrame;

public class EdocsApp {

	private static EdocsApp instance;
	private AppFrame frame;
	
	public static void main(String[] args) {
		final EdocsApp application = new EdocsApp();
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				application.getFrame().showFrame();
			}
		});
	}
	
	public static EdocsApp instance() {
		if (instance == null) {
			instance = new EdocsApp();
		}
		
		return instance;
	}
	
	public EdocsApp() {
		this.frame = new AppFrame();
	}
	
	public AppFrame getFrame() {
		return this.frame;
	}
}