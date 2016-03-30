package com.github.aureliano.edocs.domain.helper;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.github.aureliano.edocs.common.exception.EDocsException;
import com.github.aureliano.edocs.common.helper.FileHelper;
import com.github.aureliano.edocs.common.persistence.PersistenceService;
import com.github.aureliano.edocs.domain.dao.UserDao;
import com.github.aureliano.edocs.domain.entity.Attachment;
import com.github.aureliano.edocs.domain.entity.Document;
import com.github.aureliano.edocs.domain.entity.User;

public final class PersistenceHelper {

	private static PersistenceHelper instance;
	private static final String DATABASE = new File("").getAbsolutePath() + File.separator + "target" + File.separator + "edocs";
	
	private boolean initialized;
	
	private PersistenceHelper() {
		this.initialized = false;
	}

	public static final PersistenceHelper instance() {
		if (instance == null) {
			instance = new PersistenceHelper();
		}
		
		return instance;
	}
	
	public ResultSet executeQuery(String sql) throws SQLException {
		PreparedStatement ps = PersistenceService.instance().getPersistenceManager()
				.getConnection().prepareStatement(sql);
		return ps.executeQuery();
	}
	
	public void executeUpdate(String sql) throws SQLException {
		PreparedStatement ps = PersistenceService.instance().getPersistenceManager()
				.getConnection().prepareStatement(sql);
		ps.executeUpdate();
	}
	
	public void prepareDatabase() {
		if (this.initialized) {
			return;
		}
		
		Connection conn = this.prepareConnection();
		this.registerPersistenceManager(conn);
		this.mapEntities();
		this.createSchema(conn);
		
		this.initialized = true;
	}
	
	private Connection prepareConnection() {
		Connection conn = null;
		
		try {
			conn = this.createConnection();
			this.configureDatabase(conn);
		} catch (Exception ex) {
			throw new EDocsException(ex);
		}
		
		return conn;
	}
	
	private void registerPersistenceManager(Connection conn) {
		DomainPersistenceManager pm = new DomainPersistenceManager();
		pm.setConnection(conn);
		PersistenceService.instance().registerPersistenceManager(pm);
	}
	
	private void mapEntities() {
		PersistenceService.instance()
			.mapEntity(User.class, UserDao.class)
			.mapEntity(Document.class, null)
			.mapEntity(Attachment.class, null);
	}
	
	private void createSchema(Connection conn) {
		if (new File(DATABASE).exists()) {
			return;
		}
		
		String schemaCreate = FileHelper.readResource("schema-create.sql");
		try {
			conn.prepareStatement(schemaCreate).executeUpdate();
		} catch (SQLException ex) {
			throw new EDocsException(ex);
		}
	}
	
	private Connection createConnection() throws SQLException, ClassNotFoundException {
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		
		Properties connectionProps = new Properties();
		
		connectionProps.put("create", "true");
		connectionProps.put("user", "usr_edocs");
		connectionProps.put("password", "pwd-edocs-2016");
		
		return DriverManager.getConnection("jdbc:derby:" + DATABASE, connectionProps);
	}
	
	private void configureDatabase(Connection conn) throws SQLException {
		Statement s = conn.createStatement();
		s.executeUpdate("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY(\n"
				+ "    'derby.connection.requireAuthentication', 'true')");
		s.executeUpdate("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY(\n"
				+ "    'derby.authentication.provider', 'BUILTIN')");
		s.executeUpdate("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY(\n"
				+ "    'derby.user.usr_edocs', 'pwd-edocs-2016')");
		s.executeUpdate("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY(\n"
				+ "    'derby.database.propertiesOnly', 'true')");
	}
}