package com.github.aureliano.edocs.annotation.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.aureliano.edocs.annotation.validation.apply.AssertFalseValidator;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Constraint(validatedBy = AssertFalseValidator.class)
public @interface AssertFalse {

	public abstract String message() default "Expected field #{0} to be false but got #{1}.";
}