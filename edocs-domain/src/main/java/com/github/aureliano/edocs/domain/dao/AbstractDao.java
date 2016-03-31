package com.github.aureliano.edocs.domain.dao;

import java.util.Set;

import com.github.aureliano.edocs.annotation.validation.apply.ConstraintViolation;
import com.github.aureliano.edocs.annotation.validation.apply.ObjectValidator;
import com.github.aureliano.edocs.common.exception.EDocsException;
import com.github.aureliano.edocs.common.persistence.IDao;

public abstract class AbstractDao<T> implements IDao<T> {

	protected void validateConfiguration(T entity) {
		Set<ConstraintViolation> violations = ObjectValidator.instance().validate(entity);
		if (violations.isEmpty()) {
			return;
		}
		
		StringBuilder builder = new StringBuilder("Entity validation failed to " + entity.getClass().getName());
		for (ConstraintViolation violation : violations) {
			builder.append("\n - ").append(violation.getMessage());
		}
		
		throw new EDocsException(builder.toString());
	}
}