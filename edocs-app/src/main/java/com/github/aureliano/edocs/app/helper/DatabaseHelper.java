package com.github.aureliano.edocs.app.helper;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Logger;

import com.github.aureliano.edocs.common.exception.EDocsException;
import com.github.aureliano.edocs.common.helper.FileHelper;
import com.github.aureliano.edocs.common.helper.StringHelper;
import com.github.aureliano.edocs.common.persistence.IPersistenceManager;
import com.github.aureliano.edocs.common.persistence.PersistenceService;
import com.github.aureliano.edocs.domain.helper.EntityHelper;
import com.github.aureliano.edocs.service.EdocsServicePersistenceManager;

public final class DatabaseHelper {

	private static final Logger logger = Logger.getLogger(DatabaseHelper.class.getName());
	private static final String DATABASE = FileHelper.buildPath(new File("").getAbsolutePath(), "db");
	
	private DatabaseHelper() {}
	
	public static void prepareDatabase(String user, String password) {
		Connection connection = prepareConnection(user, password);
		createSchema(connection);
		initializePersistenceManager(connection);
		EntityHelper.mapEntities();
	}
	
	public static void openConnection(String user, String password) {
		Connection conn = null;
		
		try {
			conn = createConnection(user, password);
		} catch (Exception ex) {
			throw new EDocsException(ex);
		}
		
		initializePersistenceManager(conn);
		
		logger.info("Mapping entities.");
		EntityHelper.mapEntities();
	}
	
	public static void closeConnection() {
		IPersistenceManager pm = PersistenceService.instance().getPersistenceManager();
		if ((pm != null) && (pm.getConnection() != null) && (pm.isConneceted())) {
			try {
				logger.info("Closing connection.");
				pm.getConnection().close();
			} catch (SQLException ex) {
				logger.warning("Close connection failed. Detail: " + ex.getMessage());
			} finally {
				logger.info("Close connection is done.");
			}
		}
	}
	
	private static void initializePersistenceManager(Connection conn) {
		logger.info("Initialize persistence manager.");
		
		EdocsServicePersistenceManager pm = new EdocsServicePersistenceManager();
		pm.setConnection(conn);
		PersistenceService.instance().registerPersistenceManager(pm);
	}
	
	private static Connection prepareConnection(String user, String password) {
		Connection conn = null;
		
		try {
			conn = createConnection(user, password);
			configureDatabase(conn, user, password);
		} catch (Exception ex) {
			throw new EDocsException(ex);
		}
		
		return conn;
	}
	
	private static void createSchema(Connection conn) {
		logger.info("Apply schema creation if needed.");
		String schemaCreate = FileHelper.readResource("schema-create.sql");
		
		try {
			String[] commands = schemaCreate.split(";");
			for (String command : commands) {
				if (StringHelper.isEmpty(command)) {
					continue;
				}

				PreparedStatement ps = conn.prepareStatement(command);
				ps.executeUpdate();
				ps.close();
			}
		} catch (SQLException ex) {
			if (!ex.getSQLState().equals("X0Y32")) { // Table/View already exist.
				throw new EDocsException(ex);
			}
		}
	}
	
	private static Connection createConnection(String user, String password) throws SQLException, ClassNotFoundException {
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		
		Properties connectionProps = new Properties();
		
		connectionProps.put("create", "true");
		connectionProps.put("user", user);
		connectionProps.put("password", password);
		
		logger.info("Open connection to database " + DATABASE + " with user " + user);
		return DriverManager.getConnection("jdbc:derby:" + DATABASE, connectionProps);
	}
	
	private static void configureDatabase(Connection conn, String user, String password) throws SQLException {
		Statement s = conn.createStatement();
		s.executeUpdate("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY(\n"
				+ "    'derby.connection.requireAuthentication', 'true')");
		s.executeUpdate("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY(\n"
				+ "    'derby.authentication.provider', 'BUILTIN')");
		s.executeUpdate("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY(\n"
				+ "    'derby.user." + user + "', '" + password + "')");
		s.executeUpdate("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY(\n"
				+ "    'derby.database.propertiesOnly', 'true')");
	}
}