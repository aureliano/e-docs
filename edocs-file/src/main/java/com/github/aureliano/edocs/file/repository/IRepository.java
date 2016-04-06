package com.github.aureliano.edocs.file.repository;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.github.aureliano.edocs.common.persistence.IEntity;

public interface IRepository {

	public abstract Long getFreeDiskSapce();
	
	public abstract Long getDiskSize();
	
	public abstract String createDir(String path);
	
	public abstract List<String> listFiles(String path);
	
	public abstract InputStream getFileStream(IEntity entity);
	
	public abstract File getFile(IEntity entity);
	
	public abstract void writeToLimbo(File source, IEntity entity);
	
	public abstract String saveFile(IEntity entity);
	
	public abstract void deleteFile(IEntity entity);
}