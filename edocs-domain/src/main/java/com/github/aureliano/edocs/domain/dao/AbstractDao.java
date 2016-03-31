package com.github.aureliano.edocs.domain.dao;

import java.util.Set;

import com.github.aureliano.edocs.annotation.validation.apply.ConstraintViolation;
import com.github.aureliano.edocs.annotation.validation.apply.ObjectValidator;
import com.github.aureliano.edocs.common.exception.ValidationException;
import com.github.aureliano.edocs.common.message.ContextMessage;
import com.github.aureliano.edocs.common.message.SeverityLevel;
import com.github.aureliano.edocs.common.persistence.IDao;
import com.github.aureliano.edocs.common.persistence.IPersistenceManager;
import com.github.aureliano.edocs.common.persistence.PersistenceService;

public abstract class AbstractDao<T> implements IDao<T> {

	protected void validateConfiguration(T entity) {
		Set<ConstraintViolation> violations = ObjectValidator.instance().validate(entity);
		if (violations.isEmpty()) {
			return;
		}
		
		IPersistenceManager pm = PersistenceService.instance().getPersistenceManager();
		StringBuilder builder = new StringBuilder("Entity validation failed to " + entity.getClass().getName());
		for (ConstraintViolation violation : violations) {
			pm.addContextMessage(this.errorMessage(violation.getMessage()));
			builder.append("\n - ").append(violation.getMessage());
		}
		
		throw new ValidationException(builder.toString());
	}
	
	private ContextMessage errorMessage(String msg) {
		return new ContextMessage()
			.withSeverityLevel(SeverityLevel.ERROR)
			.withMessage(msg);
	}
}