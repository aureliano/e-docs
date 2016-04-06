package com.github.aureliano.edocs.file.repository;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public interface IRepository {

	public abstract Long getAvailableDiskSapce();
	
	public abstract Long getDiskSize();
	
	public abstract String createDir(String path);
	
	public abstract List<String> listFiles(String path);
	
	public abstract InputStream getFileStream(String path);
	
	public abstract File getFile(String path);
	
	public abstract String saveFile(String path);
	
	public abstract void deleteFile(String path);
}