package com.github.aureliano.edocs.domain.helper;

public final class DataTypeHelper {

	private DataTypeHelper() {}
	
	public static java.sql.Date toSqlDate(java.util.Date date) {
		if (date == null) {
			return null;
		}
		
		return new java.sql.Date(date.getTime());
	}
	
	public static java.util.Date toJavaDate(java.sql.Date date) {
		if (date == null) {
			return null;
		}
		
		return new java.util.Date(date.getTime());
	}
}