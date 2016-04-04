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
import com.github.aureliano.edocs.domain.entity.Attachment;
import com.github.aureliano.edocs.domain.entity.Document;
import com.github.aureliano.edocs.domain.helper.DataTypeHelper;

public class AttachmentDao extends AbstractDao<Attachment> {

	private static final Logger logger = Logger.getLogger(AttachmentDao.class.getName());
	
	public AttachmentDao() {}
	
	@Override
	public Attachment save(Attachment entity) {
		return super.saveEntity(entity);
	}

	@Override
	public void delete(Attachment entity) {
		this.delete(entity.getId());
	}

	@Override
	public void delete(Integer id) {
		String sql = "delete from attachments where id = ?";
		super.delete(sql, id);
	}

	@Override
	public Attachment find(Integer id) {
		String sql = "select * from attachments where id = ?";
		return super.findEntity(sql, id);
	}

	@Override
	public List<Attachment> search(DataPagination<Attachment> dataPagination) {
		StringBuilder sql = new StringBuilder("select * from attachments where");
		Attachment entity = dataPagination.getEntity();
		
		if (entity != null) {
			if (entity.getId() != null) {
				sql.append(" id = " + entity.getId());
				return this.search(sql.toString());
			}
			
			if (entity.getTemp() != null) {
				sql.append(" temp = " + entity.getTemp());
			}
			
			if (entity.getDocument() != null) {
				if (!sql.toString().endsWith("where")) {
					sql.append(" and");
				}
				sql.append(" document_fk = " + entity.getDocument().getId());
			}
		}

		if (sql.toString().endsWith("where")) {
			sql.delete(sql.indexOf("where"), sql.length() - 1);
		}
		
		super.setPaginationParams(dataPagination, sql);
		return this.search(sql.toString());
	}

	@Override
	public List<Attachment> search(String query) {
		return super.searchEntities(query);
	}

	@Override
	protected PreparedStatement createPreparedStatement(Attachment attachment) {
		String sql = this.getSaveQuery(attachment);
		logger.fine("Save user SQL: " + sql);
		
		try {
			PreparedStatement ps = super.connection.prepareStatement(sql, new String[] {"ID"});
			
			ps.setString(1, attachment.getName());
			ps.setDate(2, DataTypeHelper.toSqlDate(attachment.getUploadTime()));
			ps.setBoolean(3, attachment.getTemp());
			Integer documentId = (attachment.getDocument() != null) ? attachment.getDocument().getId() : null;
			ps.setObject(4, documentId);
			
			if (attachment.getId() != null) {
				ps.setInt(5, attachment.getId());
			}
			
			return ps;
		} catch (SQLException ex) {
			logger.log(Level.SEVERE, ex.getMessage(), ex);
			throw new EDocsException(ex);
		}
	}

	@Override
	protected Attachment fillEntity(ResultSet rs) throws SQLException {
		List<Attachment> data = this.fillEntities(rs);
		return (data.isEmpty()) ? null : data.get(0);
	}

	@Override
	protected List<Attachment> fillEntities(ResultSet rs) throws SQLException {
		List<Attachment> data = new ArrayList<>();
		
		while (rs.next()) {
			Attachment e = new Attachment()
				.withId(rs.getInt("id"))
				.withName(rs.getString("name"))
				.withUploadTime(DataTypeHelper.toJavaDate(rs.getDate("upload_time")))
				.withTemp(rs.getBoolean("temp"))
				.withDocument(new Document().withId(rs.getInt("document_fk")));
			
			data.add(e);
		}
		
		logger.fine("Found " + data.size() + " entities.");
		return data;
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}
	
	private String getSaveQuery(Attachment attachment) {
		if (attachment.getId() == null) {
			return "insert into attachments(name, upload_time, temp, document_fk) values(?, ?, ?, ?)";
		} else {
			return "update attachments set name = ?, upload_time = ?, temp = ?, document_fk = ? where id = ?";
		}
	}
}