package com.github.aureliano.edocs.file.repository;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import com.github.aureliano.edocs.common.config.ConfigurationSingleton;
import com.github.aureliano.edocs.common.config.FileRepositoryConfiguration;
import com.github.aureliano.edocs.common.helper.FileHelper;
import com.github.aureliano.edocs.common.persistence.IEntity;
import com.github.aureliano.edocs.secure.hash.HashGenerator;

public class FileSystemRepository implements IRepository {

	private static final Logger logger = Logger.getLogger(FileSystemRepository.class.getName());
	
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
	public void writeToLimbo(File source, IEntity entity) {
		String fileName = entity.getId().toString();
		File limboFile = FileHelper.buildFile(this.configuration.getLimboPath(), fileName);
		
		logger.info("Copying file " + source.getAbsolutePath() + " to " + limboFile.getAbsolutePath());
		FileHelper.copyFile(source, limboFile, true);
		logger.info("File " + limboFile.getAbsolutePath() + " saved.");
	}

	@Override
	public String saveFile(IEntity entity) {
		String fileName = entity.getId().toString();
		String hash = HashGenerator.md5(fileName);
		
		String parentDir = hash.substring(0, 2);
		String dir = hash.substring(2, 4);
		
		File srcFile = FileHelper.buildFile(this.configuration.getLimboPath(), fileName);
		File destFile = FileHelper.buildFile(this.configuration.getRootPath(), parentDir, dir, fileName);
		
		logger.info("Copying file " + srcFile.getAbsolutePath() + " to " + destFile.getAbsolutePath());
		FileHelper.copyFile(srcFile, destFile, true);
		logger.info("File " + destFile.getAbsolutePath() + " saved.");
		
		return destFile.getAbsolutePath();
	}

	@Override
	public void deleteFile(IEntity entity) {
		// TODO Auto-generated method stub
		
	}
}