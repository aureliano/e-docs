package com.github.aureliano.edocs.common.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.github.aureliano.edocs.common.exception.EDocsException;

public final class FileHelper {

	private FileHelper() {}
	
	public static String readFile(String path) {
		return readFile(new File(path));
	}
	
	public static String readFile(String path, String charset) {
		return readFile(new File(path), charset);
	}
	
	public static String readFile(File file) {
		try {
			return readFile(new FileInputStream(file));
		} catch (IOException ex) {
			throw new EDocsException(ex);
		}
	}
	
	public static String readFile(File file, String charset) {
		try {
			return readFile(new FileInputStream(file), charset);
		} catch (IOException ex) {
			throw new EDocsException(ex);
		}
	}
	
	public static String readFile(InputStream stream) {
		return readFile(stream, null);
	}
	
	public static String readFile(InputStream stream, String charset) {
		StringBuilder builder = new StringBuilder();
		
		try {
			InputStreamReader inputReader;
			if (StringHelper.isEmpty(charset)) {
				inputReader = new InputStreamReader(stream);
			} else {
				inputReader = new InputStreamReader(stream, charset);
			}
		
			BufferedReader reader = new BufferedReader(inputReader);
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				if (builder.length() > 0) {
					builder.append("\n");
				}
				builder.append(line);
			}
			
			reader.close();
		} catch (IOException ex) {
			throw new EDocsException(ex);
		}
		
		return builder.toString();
	}
	
	public static String readResource(String resourceName) {
		InputStream stream = ClassLoader.getSystemResourceAsStream(resourceName);
		return FileHelper.readFile(stream);
	}
	
	public static String buildPath(String... tokens) {
		StringBuilder builder = new StringBuilder();
		
		for (String token : tokens) {
			if (builder.length() > 0) {
				builder.append(File.separator);
			}
			builder.append(token);
		}
		
		return builder.toString();
	}
	
	public static File buildFile(String... tokens) {
		return new File(buildPath(tokens));
	}
}