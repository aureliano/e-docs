package com.github.aureliano.edocs.domain.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.aureliano.edocs.common.exception.EDocsException;
import com.github.aureliano.edocs.common.persistence.DataPagination;
import com.github.aureliano.edocs.domain.entity.Category;
import com.github.aureliano.edocs.domain.entity.Document;
import com.github.aureliano.edocs.domain.entity.User;
import com.github.aureliano.edocs.domain.helper.DataTypeHelper;

public class DocumentDao extends AbstractDao<Document> {

	private static final Logger logger = Logger.getLogger(DocumentDao.class.getName());
	
	public DocumentDao() {}

	@Override
	public Document save(Document entity) {
		return super.saveEntity(entity);
	}

	@Override
	public void delete(Document entity) {
		this.delete(entity.getId());
	}

	@Override
	public void delete(Integer id) {
		String sql = "delete from documents where id = ?";
		super.delete(sql, id);
	}

	@Override
	public Document find(Integer id) {
		String sql = "select * from documents where id = ?";
		return super.findEntity(sql, id);
	}

	@Override
	public List<Document> search(DataPagination<Document> dataPagination) {
		StringBuilder sql = new StringBuilder("select id, category, due_date, owner_fk from documents where");
		Document entity = dataPagination.getEntity();
		
		if (entity.getId() != null) {
			sql.append(" id = " + entity.getId());
			return this.search(sql.toString());
		}
		
		if (entity.getCategory() != null) {
			sql.append(" category = " + entity.getCategory());
		}
		
		return this.search(sql.toString());
	}

	@Override
	public List<Document> search(String query) {
		return super.searchEntities(query);
	}

	@Override
	protected PreparedStatement createPreparedStatement(Document document) {
		String sql = this.getSaveQuery(document);
		logger.fine("Save user SQL: " + sql);
		
		try {
			PreparedStatement ps = super.connection.prepareStatement(sql, new String[] {"ID"});
			
			ps.setString(1, document.getCategory().toString());
			ps.setString(2, document.getDescription());
			ps.setDate(3, DataTypeHelper.toSqlDate(document.getDueDate()));
			ps.setObject(4, ((document.getOwner() == null) ? null : document.getOwner().getId()));
			
			if (document.getId() != null) {
				ps.setInt(5, document.getId());
			}
			
			return ps;
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, ex.getMessage(), ex);
			throw new EDocsException(ex);
		}
	}

	@Override
	protected Document fillEntity(ResultSet rs) throws SQLException {
		List<Document> data = this.fillEntities(rs);
		return (data.isEmpty()) ? null : data.get(0);
	}

	@Override
	protected List<Document> fillEntities(ResultSet rs) throws SQLException {
		List<Document> data = new ArrayList<>();
		
		while (rs.next()) {
			Document e = new Document()
				.withId(rs.getInt("id"))
				.withCategory(Category.valueOf(rs.getString("category")))
				.withDueDate(DataTypeHelper.toJavaDate(rs.getDate("due_date")))
				.withOwner(new User().withId(rs.getInt("owner_fk")));
			
			if (rs.getMetaData().getColumnCount() == 5) {
				e.withDescription(rs.getString("description"));
			}
			
			data.add(e);
		}
		
		logger.fine("Found " + data.size() + " entities.");
		return data;
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}
	
	private String getSaveQuery(Document document) {
		if (document.getId() == null) {
			return "insert into documents(category, description, due_date, owner_fk) values(?, ?, ?, ?)";
		} else {
			return "update documents set category = ?, description = ?, due_date = ?, owner_fk = ? where id = ?";
		}
	}
}