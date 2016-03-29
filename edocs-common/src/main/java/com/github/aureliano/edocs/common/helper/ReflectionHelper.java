package com.github.aureliano.edocs.common.helper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

import com.github.aureliano.edocs.common.exception.EDocsException;

public final class ReflectionHelper {
    
    public static Object newInstance(Class<?> clazz) {
    	try {
    		return clazz.newInstance();
    	} catch (Exception ex) {
    		throw new EDocsException(ex);
    	}
    }
    
    public static Method getMethod(Class<?> clazz, String methodName, Class<?>[] parameters) {
    	try {
    		return clazz.getMethod(methodName, parameters);
    	} catch (NoSuchMethodException ex) {
    		throw new EDocsException(ex);
    	}
    }

    public static Class<?> getGenericType(Field field) {
        if (!Collection.class.isAssignableFrom(field.getType())) {
            return field.getType();
        }
        ParameterizedType type = (ParameterizedType) field.getGenericType();
        return (Class<?>) type.getActualTypeArguments()[0];
    }

    public static Class<?> getGenericType(Method method) {
        if (!Collection.class.isAssignableFrom(method.getReturnType())) {
            return method.getReturnType();
        }
        ParameterizedType type = (ParameterizedType) method.getGenericReturnType();
        return (Class<?>) type.getActualTypeArguments()[0];
    }
    
    public static Object callMethod(Object object, String methodName, Class<?>[] parametersType, Object[] methodParameters) {
    	Class<?> clazz = object.getClass();
    	try {
    		Method method = clazz.getMethod(methodName, parametersType);
    		return method.invoke(object, methodParameters);
    	} catch (Exception ex) {
    		throw new EDocsException(ex);
    	}
    }
    
    public static String getSimpleAccessMethodName(Method method) {
    	String name = method.getName().replaceFirst("^(get|is|set|with)", "");
    	return Character.toLowerCase(name.charAt(0)) + name.substring(1);
    }
}