package com.github.aureliano.edocs.domain.helper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.aureliano.edocs.common.exception.EDocsException;

public class DataTypeHelperTest {

	@Test
	public void testToSqlDate() {
		java.util.Date jd = new java.util.Date();
		this.sleep(100);
		java.sql.Date sd = DataTypeHelper.toSqlDate(jd);
		
		assertEquals(jd.getTime(), sd.getTime());
	}
	
	@Test
	public void testToJavaDate() {
		java.sql.Date sd = new java.sql.Date(System.currentTimeMillis());
		this.sleep(105);
		java.util.Date jd = DataTypeHelper.toJavaDate(sd);
		
		assertEquals(sd.getTime(), jd.getTime());
	}
	
	private void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException ex) {
			throw new EDocsException(ex);
		}
	}
}