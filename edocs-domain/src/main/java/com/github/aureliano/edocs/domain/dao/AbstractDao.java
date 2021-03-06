package com.github.aureliano.edocs.domain.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.aureliano.edocs.annotation.validation.apply.ConstraintViolation;
import com.github.aureliano.edocs.annotation.validation.apply.ObjectValidator;
import com.github.aureliano.edocs.common.exception.EDocsException;
import com.github.aureliano.edocs.common.exception.ValidationException;
import com.github.aureliano.edocs.common.message.ContextMessage;
import com.github.aureliano.edocs.common.message.SeverityLevel;
import com.github.aureliano.edocs.common.persistence.DataPagination;
import com.github.aureliano.edocs.common.persistence.IDao;
import com.github.aureliano.edocs.common.persistence.IEntity;
import com.github.aureliano.edocs.common.persistence.IPersistenceManager;
import com.github.aureliano.edocs.common.persistence.PersistenceService;

public abstract class AbstractDao<T> implements IDao<T> {

	protected Connection connection;
	
	public AbstractDao() {
		this.connection = PersistenceService.instance().getPersistenceManager().getConnection();
	}
	
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
	
	protected T saveEntity(T entity) {
		this.validateConfiguration(entity);
		T persistedEntity = null;
		
		try(PreparedStatement ps = this.createPreparedStatement(entity)) {
			int res = ps.executeUpdate();
			this.getLogger().fine("Number of records affected by this action " + res);
			
			Integer id = ((IEntity) entity).getId();
			if (id == null) {
				ResultSet rs = ps.getGeneratedKeys();
				if ((rs != null) && (rs.next())) {
					persistedEntity = this.find(rs.getInt(1));
				}
			} else {
				persistedEntity = this.find(id);
			}
		} catch (SQLException ex) {
			this.getLogger().log(Level.SEVERE, ex.getMessage(), ex);
			throw new EDocsException(ex);
		}
		
		return persistedEntity;
	}
	
	protected void delete(String sql, Integer id) {
		this.getLogger().fine("Delete user SQL: " + sql);
		
		try(PreparedStatement ps = this.connection.prepareStatement(sql)) {
			ps.setInt(1, id);
			
			int res = ps.executeUpdate();
			this.getLogger().fine("Number of records affected by this action " + res);
		} catch (SQLException ex) {
			this.getLogger().log(Level.SEVERE, ex.getMessage(), ex);
			throw new EDocsException(ex);
		}
	}
	
	protected T findEntity(String sql, Integer id) {
		this.getLogger().fine("Find entity with id " + id);
		this.getLogger().fine("Find user SQL: " + sql);
		T entity = null;
		
		try(PreparedStatement ps = this.connection.prepareStatement(sql)) {
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			entity = this.fillEntity(rs);
		} catch (SQLException ex) {
			this.getLogger().log(Level.SEVERE, ex.getMessage(), ex);
			throw new EDocsException(ex);
		}
		
		return entity;
	}
	
	protected List<T> searchEntities(String query) {
		this.getLogger().fine("Find user SQL: " + query);
		
		try(PreparedStatement ps = this.connection.prepareStatement(query)) {
			ResultSet rs = ps.executeQuery();
			
			return this.fillEntities(rs);
		} catch (SQLException ex) {
			this.getLogger().log(Level.SEVERE, ex.getMessage(), ex);
			throw new EDocsException(ex);
		}
	}

	protected void setPaginationParams(DataPagination<T> dp, StringBuilder sql) {
		if (dp.getOffset() != null) {
			sql.append(" offset " + dp.getOffset() + " rows");
		}
		
		if (dp.getLimit() != null) {
			sql.append(" fetch next " + dp.getLimit() + " rows only");
		}
	}
	
	protected abstract PreparedStatement createPreparedStatement(T entity);
	
	protected abstract T fillEntity(ResultSet rs) throws SQLException;
	
	protected abstract List<T> fillEntities(ResultSet rs) throws SQLException;
	
	protected abstract Logger getLogger();
	
	private ContextMessage errorMessage(String msg) {
		return new ContextMessage()
			.withSeverityLevel(SeverityLevel.ERROR)
			.withMessage(msg);
	}
}