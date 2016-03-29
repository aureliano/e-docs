package com.github.aureliano.edocs.annotation.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.aureliano.edocs.annotation.validation.apply.AssertTrueValidator;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Constraint(validatedBy = AssertTrueValidator.class)
public @interface AssertTrue {

	public abstract String message() default "Expected field #{0} to be true but got #{1}.";
}