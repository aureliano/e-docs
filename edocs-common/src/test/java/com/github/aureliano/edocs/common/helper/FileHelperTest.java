package com.github.aureliano.edocs.common.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Test;

public class FileHelperTest {

	@Test
	public void testCopyFile() {
		File srcFile = new File("src/test/resources/simple_file");
		File destFile = new File("target/thrash/copied");
		
		if (destFile.exists()) {
			destFile.delete();
		}
		
		assertTrue(srcFile.isFile());
		assertFalse(destFile.exists());
		
		FileHelper.copyFile(srcFile, destFile, true);
		destFile = new File(destFile.getPath()); // refresh file object.
		
		assertTrue(destFile.exists());
		assertTrue(destFile.isFile());
	}
	
	@Test
	public void testDelete() {
		createDirectoryStructure();
		
		FileHelper.delete(new File("target/thrash"), true);
		assertFalse(new File("target/thrash").exists());
	}
	
	@Test
	public void testDeleteAllFiles() {
		createDirectoryStructure();
		
		FileHelper.deleteAllFiles(new File("target/thrash"));
		File[] files = new File("target/thrash").listFiles();
		
		assertTrue(files.length == 1);
		assertEquals("target/thrash/level2", files[0].getPath());
	}
	
	@Test
	public void testDeleteAllFilesByTime() throws InterruptedException {
		int oneSecond = 1000;
		String dirPath = createDirectoryStructureWithDelay(oneSecond);
		
		assertEquals(5, new File(dirPath).list().length);
		FileHelper.deleteAllFiles(new File(dirPath), System.currentTimeMillis() - oneSecond, "");
		assertEquals(2, new File(dirPath).list().length);
	}
	
	@Test
	public void testReadFile() throws FileNotFoundException {
		String path = "src/test/resources/simple_file";
		String expected = "Ecce quam bonum et quam icundum habitare fratres in unum.";
		
		assertEquals(expected, FileHelper.readFile(path));
		assertEquals(expected, FileHelper.readFile(new File(path)));
		assertEquals(expected, FileHelper.readFile(new FileInputStream(new File(path))));
	}
	
	@Test
	public void testReadResource() {
		String resourceName = "simple_file";
		String expected = "Ecce quam bonum et quam icundum habitare fratres in unum.";
		
		assertEquals(expected, FileHelper.readResource(resourceName));
	}
	
	@Test
	public void testBuildPath() {
		assertEquals("src/test/resources", FileHelper.buildPath("src", "test", "resources"));
	}
	
	@Test
	public void testBuildFile() {
		assertEquals(new File("src/test/resources"), FileHelper.buildFile("src", "test", "resources"));
	}
	
	public static void createDirectoryStructure() {
		File sourceDir = new File("src/test/resources");
		
		for (File file : sourceDir.listFiles()) {
			if (file.isFile()) {
				FileHelper.copyFile(file, new File("target/thrash/" + file.getName()), true);
			}
		}
		
		for (File file : sourceDir.listFiles()) {
			if (file.isFile()) {
				FileHelper.copyFile(file, new File("target/thrash/level2/" + file.getName()), true);
			}
		}
	}
	
	public static String createDirectoryStructureWithDelay(long delay) {
		String dirPath = "target/thrash/drop";
		
		FileHelper.copyFile(new File("src/test/resources/simple_file"), new File(dirPath + "/f1"), true);
		FileHelper.copyFile(new File("src/test/resources/simple_file"), new File(dirPath + "/f2"), true);
		FileHelper.copyFile(new File("src/test/resources/simple_file"), new File(dirPath + "/f3"), true);
		
		try {
			Thread.sleep(delay);
		} catch (InterruptedException ex) {
			fail(ex.getMessage());
		}
		
		FileHelper.copyFile(new File("src/test/resources/simple_file"), new File(dirPath + "/f4"), true);
		FileHelper.copyFile(new File("src/test/resources/simple_file"), new File(dirPath + "/f5"), true);
		
		return dirPath;
	}
}