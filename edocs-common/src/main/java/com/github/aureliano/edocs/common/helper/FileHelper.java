package com.github.aureliano.edocs.common.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.aureliano.edocs.common.exception.EDocsException;

public final class FileHelper {

	private FileHelper() {}
	
	public static void copyFile(File sourceFile, File destFile) {
		try(
			FileInputStream inputStream = new FileInputStream(sourceFile);
			FileOutputStream outputStream = new FileOutputStream(destFile);

			FileChannel source = inputStream.getChannel();
			FileChannel destination = outputStream.getChannel();
		) {
			if(!destFile.exists()) {
				destFile.createNewFile();
			}
		
			destination.transferFrom(source, 0, source.size());
		} catch (IOException ex) {
			throw new EDocsException(ex);
		}
	}
	
	public static void copyFile(File sourceFile, File destFile, boolean mkdirs) {
		if (mkdirs) {
			File parentDestFile = destFile.getParentFile();
			if ((parentDestFile != null) && (!parentDestFile.exists())) {
				if (!parentDestFile.mkdirs()) {
					throw new EDocsException("Could not create directory " + parentDestFile.getPath());
				}
			}
		}
		
		FileHelper.copyFile(sourceFile, destFile);
	}
	
	public static void delete(File file) {
		if (!file.delete()) {
			throw new EDocsException("Could not delete file " + file.getPath());
		}
	}
	
	public static void delete(File file, boolean recursively) {
		if (recursively) {
			forceDelete(file);
		} else {
			delete(file);
		}
	}
	
	public static void deleteAllFiles(File directory) {
		File[] files = directory.listFiles();
		if (files == null) {
			return;
		}
		
		for (File file : files) {
			if (!file.isDirectory()) {
				delete(file);
			}
		}
	}
	
	public static void deleteAllFiles(File directory, long timeSeed, final String regex) {
		long currentTimeMillis = System.currentTimeMillis();
		long seed = currentTimeMillis - timeSeed;
		
		File[] files = directory.listFiles(new FilenameFilter() {
			Pattern pattern = Pattern.compile(regex);
			
			@Override
			public boolean accept(File dir, String name) {
				Matcher matcher = pattern.matcher(name);
				return matcher.find();
			}
		});
		if (files == null) {
			return;
		}
		
		for (File file : files) {
			long fileLastModificationSeed = currentTimeMillis - file.lastModified();
			if ((!file.isDirectory()) && (fileLastModificationSeed >= seed)) {
				delete(file);
			}
		}
	}
	
	private static void forceDelete(File file) {
		if (file.isDirectory()) {
			File[] children = file.listFiles();
			
			if ((children != null) && (children.length > 0)) {
				for (File child : children) {
					forceDelete(child);
				}
			}
		}
		
		if (!file.delete()) {
			throw new EDocsException("Could not delete file " + file.getPath());
		}
	}
	
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
	
	public static void createParentDirectory(File file, boolean chain) {
		createDirectory(file.getParentFile(), chain);
	}
	
	public static void createDirectory(File file, boolean chain) {
		if (file.exists()) {
			return;
		}
		
		EDocsException ex = new EDocsException("Could not create directory " + file.getPath());
		if (chain) {
			if (!file.mkdirs()) {
				throw ex;
			}
		} else {
			if (!file.mkdir()) {
				throw ex;
			}
		}
	}
	
	public static String getRootPath(String path) {
		String[] tokens = path.split("[\\\\/]+");
		String root = tokens[0].equals("") ? "/" : tokens[0];
		
		return root;
	}
}