package com.github.aureliano.edocs.common.config;

public class SecureConfiguration {

	private String salt;
	private Integer hashIterations;
	private String algorithm;
	
	public SecureConfiguration() {}

	public String getSalt() {
		return salt;
	}

	public SecureConfiguration withSalt(String salt) {
		this.salt = salt;
		return this;
	}

	public Integer getHashIterations() {
		return hashIterations;
	}

	public SecureConfiguration withHashIterations(Integer hashIterations) {
		this.hashIterations = hashIterations;
		return this;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public SecureConfiguration withAlgorithm(String algorithm) {
		this.algorithm = algorithm;
		return this;
	}
}