package com.github.aureliano.edocs.secure.model;

public final class PasswordEncryptionModel {

	private static final Integer DEFAULT_HASH_ITERATIONS = 25;
	private static final Algorithm DEFAULT_ALGORITHM = Algorithm.SHA;
	
	private String password;
	private String salt;
	private Integer hashIterations;
	private Algorithm algorithm;
	
	public PasswordEncryptionModel() {}
	
	public PasswordEncryptionModel(String password, String salt) {
		this(password, salt, DEFAULT_HASH_ITERATIONS);
	}
	
	public PasswordEncryptionModel(String password, String salt, Integer hashIterations) {
		this(password, salt, DEFAULT_HASH_ITERATIONS, DEFAULT_ALGORITHM);
	}
	
	public PasswordEncryptionModel(String password, String salt, Integer hashIterations, Algorithm algorithm) {
		this.password = password;
		this.salt = salt;
		this.hashIterations = hashIterations;
		this.algorithm = algorithm;
	}

	public String getPassword() {
		return password;
	}

	public PasswordEncryptionModel withPassword(String password) {
		this.password = password;
		return this;
	}

	public String getSalt() {
		return salt;
	}

	public PasswordEncryptionModel withSalt(String salt) {
		this.salt = salt;
		return this;
	}

	public Integer getHashIterations() {
		return hashIterations;
	}

	public PasswordEncryptionModel withHashIterations(Integer hashIterations) {
		this.hashIterations = hashIterations;
		return this;
	}

	public Algorithm getAlgorithm() {
		return algorithm;
	}

	public PasswordEncryptionModel withAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
		return this;
	}
}