package com.github.aureliano.edocs.common.helper;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Test;

public class FileHelperTest {

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
}