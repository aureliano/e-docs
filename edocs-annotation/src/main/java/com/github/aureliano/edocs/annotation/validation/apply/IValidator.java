package com.github.aureliano.edocs.annotation.validation.apply;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

public interface IValidator {

	public abstract Set<ConstraintViolation> validate(Object object, Method method, Annotation annotation);
}