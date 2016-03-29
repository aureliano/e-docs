package com.github.aureliano.edocs.common.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.List;

import org.junit.Test;

import com.github.aureliano.edocs.common.model.CommonModel;

public class ReflectionHelperTest {
	
	@Test
	public void testNewInstance() {
		Object model = ReflectionHelper.newInstance(CommonModel.class);
		
		assertNotNull(model);
		assertTrue(model instanceof CommonModel);
	}

	@Test
	public void testGetMethod() {
		String methodName = "getValues";
		Method method = ReflectionHelper.getMethod(CommonModel.class, methodName, new Class<?>[0]);
		
		assertEquals(methodName, method.getName());
		assertEquals(List.class, method.getReturnType());
	}
	
	@Test
	public void testGetGenericTypeByMethod() {
		Method method = ReflectionHelper.getMethod(CommonModel.class, "getValues", new Class<?>[0]);
		Class<?> expected = String.class;
		Class<?> actual = ReflectionHelper.getGenericType(method);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testCallMethod() {
		CommonModel model = new CommonModel().withId(1).withName("test");
		Object expected = 1;
		Object actual = ReflectionHelper.callMethod(model, "getId", new Class<?>[0], new Object[0]);
		
		assertEquals(expected, actual);
		
		expected = "test";
		actual = ReflectionHelper.callMethod(model, "getName", new Class<?>[0], new Object[0]);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetSimpleAccessMethodName() {
		Method method = ReflectionHelper.getMethod(CommonModel.class, "getId", new Class<?>[0]);
		String expected = "id";
		String actual = ReflectionHelper.getSimpleAccessMethodName(method);
		
		assertEquals(expected, actual);
		
		method = ReflectionHelper.getMethod(CommonModel.class, "setId", new Class<?>[] { Integer.class });
		expected = "id";
		actual = ReflectionHelper.getSimpleAccessMethodName(method);
		
		assertEquals(expected, actual);
		
		method = ReflectionHelper.getMethod(CommonModel.class, "withId", new Class<?>[] { Integer.class });
		expected = "id";
		actual = ReflectionHelper.getSimpleAccessMethodName(method);
		
		assertEquals(expected, actual);
	}
}