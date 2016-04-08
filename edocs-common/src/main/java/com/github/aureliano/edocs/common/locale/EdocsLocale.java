package com.github.aureliano.edocs.common.locale;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

import com.github.aureliano.edocs.common.exception.EDocsException;

public final class EdocsLocale {

	private static final String DEFAULT_LOCALE = "en_US";
	private static EdocsLocale instance;
	
	private Properties properties;
	
	private EdocsLocale() {
		this.loadLanguageProperties();
	}
	
	public String getMessage(String property) {
		return this.properties.getProperty(property);
	}
	
	public static final EdocsLocale instance() {
		if (instance == null) {
			instance = new EdocsLocale();
		}
		
		return instance;
	}
	
	private InputStream getDefaultLocaleStream() {
		String locale = Locale.getDefault().toString();
		
		InputStream stream = ClassLoader.getSystemResourceAsStream(this.getLocaleResourceName(locale));
		if (stream != null) {
			return stream;
		}
		
		return ClassLoader.getSystemResourceAsStream(this.getLocaleResourceName(DEFAULT_LOCALE));
	}
	
	private String getLocaleResourceName(String locale) {
		return String.format("locale/%s.properties", locale);
	}
	
	private void loadLanguageProperties() {
		this.properties = new Properties();
		try(InputStream stream = this.getDefaultLocaleStream()) {
			properties.load(stream);
		} catch (IOException ex) {
			throw new EDocsException(ex);
		}
	}
}