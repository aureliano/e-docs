package com.github.aureliano.edocs.app.helper;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public final class GuiHelper {

	private GuiHelper() {}
	
	public static Icon createIcon(String resource) {
		URL url = ClassLoader.getSystemResource(resource);
		return new ImageIcon(url);
	}
}