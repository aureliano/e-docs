package com.github.aureliano.edocs.secure.model;

public enum Algorithm {

	MD5("MD5"),
	SHA_1("SHA-1"),
	SHA_2("SHA-2");
	
	private String label;
	
	private Algorithm(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}