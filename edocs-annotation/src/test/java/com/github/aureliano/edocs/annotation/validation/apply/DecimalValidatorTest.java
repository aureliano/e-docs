package com.github.aureliano.edocs.annotation.validation.apply;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.aureliano.edocs.annotation.validation.Decimal;
import com.github.aureliano.edocs.annotation.validation.model.AnnotationModel;

public class DecimalValidatorTest {

	private DecimalValidator validator;
	
	public DecimalValidatorTest() {
		this.validator = new DecimalValidator();
	}
	
	@Test
	public void testValidateWithMinError() throws SecurityException, NoSuchMethodException {
		AnnotationModel configuration = new AnnotationModel().withMyDoubleField(4.312145454);
		Method method = configuration.getClass().getMethod("getMyDoubleField", new Class[] {});
		Annotation annotation = method.getAnnotation(Decimal.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation).iterator().next();
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected field myDoubleField to have size between 4.5 and 9.2 but got 4.312145454.", constraint.getMessage());
		Assert.assertEquals(Decimal.class, constraint.getValidator());
	}
	
	@Test
	public void testValidateWithMaxError() throws SecurityException, NoSuchMethodException {
		AnnotationModel configuration = new AnnotationModel().withMyDoubleField(9.20001);
		Method method = configuration.getClass().getMethod("getMyDoubleField", new Class[] {});
		Annotation annotation = method.getAnnotation(Decimal.class);
		 
		ConstraintViolation constraint = this.validator.validate(configuration, method, annotation).iterator().next();
		Assert.assertNotNull(constraint);
		
		Assert.assertEquals("Expected field myDoubleField to have size between 4.5 and 9.2 but got 9.20001.", constraint.getMessage());
		Assert.assertEquals(Decimal.class, constraint.getValidator());
	}
	
	@Test
	public void testValidate() throws SecurityException, NoSuchMethodException {
		AnnotationModel configuration = new AnnotationModel().withMyDoubleField(4.50000001);
		Method method = configuration.getClass().getMethod("getMyDoubleField", new Class[] {});
		Annotation annotation = method.getAnnotation(Decimal.class);
		 
		Set<ConstraintViolation> res = this.validator.validate(configuration, method, annotation);
		Assert.assertTrue(res.isEmpty());
		
		configuration.withMyDoubleField(9.19999999);
		method = configuration.getClass().getMethod("getMyDoubleField", new Class[] {});
		annotation = method.getAnnotation(Decimal.class);
		
		res = this.validator.validate(configuration, method, annotation);
		Assert.assertTrue(res.isEmpty());
	}
}