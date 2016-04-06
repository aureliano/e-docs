package com.github.aureliano.edocs.file.repository;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import com.github.aureliano.edocs.common.config.ConfigurationSingleton;
import com.github.aureliano.edocs.common.config.FileRepositoryConfiguration;
import com.github.aureliano.edocs.common.helper.FileHelper;

public class FileSystemRepository implements IRepository {

	private FileRepositoryConfiguration configuration;
	
	public FileSystemRepository() {
		this.configuration = ConfigurationSingleton.instance()
				.getAppConfiguration().getFileRepositoryConfiguration();
	}
	
	@Override
	public Long getAvailableDiskSapce() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getDiskSize() {
		// TODO Auto-generated method stub
		return null;
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
	public InputStream getFileStream(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getFile(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String saveFile(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteFile(String path) {
		// TODO Auto-generated method stub
		
	}
}