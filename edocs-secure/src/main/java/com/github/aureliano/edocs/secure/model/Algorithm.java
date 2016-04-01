package com.github.aureliano.edocs.secure.model;

public enum Algorithm {

	MD5("MD5"),
	SHA("SHA"),
	SHA_1("SHA-1");
	
	private String label;
	
	private Algorithm(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}