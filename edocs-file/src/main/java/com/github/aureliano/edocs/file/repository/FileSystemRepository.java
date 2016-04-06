package com.github.aureliano.edocs.file.repository;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.github.aureliano.edocs.common.config.ConfigurationSingleton;
import com.github.aureliano.edocs.common.config.FileRepositoryConfiguration;
import com.github.aureliano.edocs.common.helper.FileHelper;
import com.github.aureliano.edocs.common.persistence.IEntity;

public class FileSystemRepository implements IRepository {

	private FileRepositoryConfiguration configuration;
	
	public FileSystemRepository() {
		this.configuration = ConfigurationSingleton.instance()
				.getAppConfiguration().getFileRepositoryConfiguration();
	}
	
	@Override
	public Long getFreeDiskSapce() {
		String root = FileHelper.getRootPath(new File("").getAbsolutePath());
		return new File(root).getFreeSpace();
	}

	@Override
	public Long getDiskSize() {
		String root = FileHelper.getRootPath(new File("").getAbsolutePath());
		return new File(root).getTotalSpace();
	}

	@Override
	public String createDir(String path) {
		String dirPath = FileHelper.buildPath(this.configuration.getRootPath(), path);
		FileHelper.createDirectory(new File(dirPath), true);

		return dirPath;
	}

	@Override
	public List<String> listFiles(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getFileStream(IEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getFile(IEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String saveFile(IEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteFile(IEntity entity) {
		// TODO Auto-generated method stub
		
	}
}