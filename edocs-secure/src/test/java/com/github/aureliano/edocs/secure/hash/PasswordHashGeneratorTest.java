package com.github.aureliano.edocs.secure.hash;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.aureliano.edocs.secure.model.Algorithm;
import com.github.aureliano.edocs.secure.model.PasswordEncryptionModel;

public class PasswordHashGeneratorTest {

	@Test
	public void testGenerate() {
		PasswordEncryptionModel pwd = new PasswordEncryptionModel()
			.withAlgorithm(Algorithm.SHA_1)
			.withHashIterations(1)
			.withPassword("mypass")
			.withSalt("");

		String hash = PasswordHashGenerator.generate(pwd);
		assertEquals("5yfRRkrhJDbomacm2lsvEdg4GyY=", hash);
		assertEquals(hash, PasswordHashGenerator.generate(pwd));
		
		pwd.withSalt("d+*4T=");
		hash = PasswordHashGenerator.generate(pwd);
		assertEquals(hash, PasswordHashGenerator.generate(pwd));
	}
}