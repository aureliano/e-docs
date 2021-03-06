package com.github.aureliano.edocs.domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.aureliano.edocs.common.exception.ValidationException;
import com.github.aureliano.edocs.common.message.ContextMessage;
import com.github.aureliano.edocs.common.message.SeverityLevel;
import com.github.aureliano.edocs.common.persistence.DataPagination;
import com.github.aureliano.edocs.common.persistence.IDao;
import com.github.aureliano.edocs.common.persistence.IPersistenceManager;
import com.github.aureliano.edocs.common.persistence.PersistenceService;
import com.github.aureliano.edocs.domain.entity.User;
import com.github.aureliano.edocs.domain.helper.DomainPersistenceManager;
import com.github.aureliano.edocs.domain.helper.PersistenceHelper;

public class UserDaoTest {
	
	private IDao<User> dao;
	
	public UserDaoTest() {
		PersistenceHelper.instance().prepareDatabase();
		
		PersistenceService ps = PersistenceService.instance();
		if (ps.getPersistenceManager() == null) {
			DomainPersistenceManager pm = new DomainPersistenceManager();
			pm.setConnection(PersistenceHelper.instance().getConnection());
			PersistenceService.instance().registerPersistenceManager(pm);
		}
		
		this.dao = new UserDao();
	}

	@Before
	public void beforeTest() throws SQLException {
		PersistenceHelper.instance().deleteAllRecords();;
	}
	
	@Test
	public void testValidateSaveAction() {
		this.checkInvalidName();
		this.checkInvalidPassword();
	}
	
	@Test
	public void testSave() {
		User u = new User()
			.withName("agustine")
			.withPassword("test123")
			.withDbUser(null);
		
		User user = this.dao.save(u);
		assertNotNull(user.getId());
		assertEquals(u.getName(), user.getName());
		assertEquals(u.getPassword(), user.getPassword());
		assertFalse(user.getDbUser());
		
		u = new User()
			.withName("alfonse")
			.withPassword("test123")
			.withDbUser(true);
		
		user = this.dao.save(u);
		assertTrue(user.getDbUser());
	}
	
	@Test
	public void testSaveErrorUniqueName() {
		User u = new User()
			.withName("agustine")
			.withPassword("test123")
			.withDbUser(null);
		
		this.dao.save(u);
		try {
			String sql = "insert into users(name, password) values('augustine', 'test123')";
			PersistenceHelper.instance().executeUpdate(sql);
		} catch (SQLException ex) {
			assertEquals("23505", ex.getSQLState());
		}
	}
	
	@Test
	public void testDeleteByEntity() throws SQLException {
		String name = "delete-by-entity";
		User u = new User()
			.withName(name)
			.withPassword("test123");
		
		User user = this.dao.save(u);
		
		ResultSet rs = PersistenceHelper.instance().executeQuery("select count(id) from users where name = '" + name + "'");
		rs.next();
		assertEquals(1, rs.getInt(1));
		rs.close();
		
		this.dao.delete(user);
		
		rs = PersistenceHelper.instance().executeQuery("select count(id) from users where name = '" + name + "'");
		rs.next();
		assertEquals(0, rs.getInt(1));
		rs.close();
	}
	
	@Test
	public void testDeleteById() throws SQLException {
		String name = "delete-by-id";
		User u = new User()
			.withName(name)
			.withPassword("test123");
		
		User user = this.dao.save(u);
		
		ResultSet rs = PersistenceHelper.instance().executeQuery("select count(id) from users where name = '" + name + "'");
		rs.next();
		assertEquals(1, rs.getInt(1));
		rs.close();
		
		this.dao.delete(user.getId());
		
		rs = PersistenceHelper.instance().executeQuery("select count(id) from users where name = '" + name + "'");
		rs.next();
		assertEquals(0, rs.getInt(1));
		rs.close();
	}
	
	@Test
	public void testFind() {
		User u = new User()
			.withName("iacopo")
			.withPassword("test123");
		
		User user1 = this.dao.save(u);
		User user2 = this.dao.find(user1.getId());
		
		assertEquals(user1, user2);
	}
	
	@Test
	public void testSearchByEntity() {
		User u = new User()
			.withName("petrus")
			.withPassword("test123");
		
		User user1 = this.dao.save(u);
		this.dao.save(new User()
				.withName("maria")
				.withPassword("test12345"));
		
		List<User> data = this.dao.search(new DataPagination<User>().withEntity(u));
		
		assertEquals(1, data.size());
		
		User user2 = data.get(0);		
		assertEquals(user1, user2);
	}
	
	@Test
	public void testSearchByQuery() {
		User u = new User()
			.withName("petrus")
			.withPassword("test123");
		
		User user1 = this.dao.save(u);
		this.dao.save(new User()
				.withName("maria")
				.withPassword("test12345"));
		
		List<User> data = this.dao.search("select * from users where name = 'petrus'");
		
		assertEquals(1, data.size());
		
		User user2 = data.get(0);		
		assertEquals(user1, user2);
	}
	
	private void checkInvalidName() {
		User u = new User().withPassword("test123");
		this.validateContextMessage(u, "Expected to find a not empty text for field name.");
		
		u.withName("1");
		this.validateContextMessage(u, "Expected field name to have size between 3 and 25 but got 1.");
		
		u.withName("1234567890123456789012345");
		this.validateContextMessage(u, "Expected field name to have size between 3 and 25 but got 65.");
	}
	
	private void checkInvalidPassword() {
		User u = new User().withName("caesar-augustus");
		this.validateContextMessage(u, "Expected to find a not empty text for field password.");
		
		u.withPassword("1");
		this.validateContextMessage(u, "Expected field password to have size between 3 and 64 but got 1.");
		
		u.withPassword("01234567890123456789012345678901234567890123456789012345678912345");
		this.validateContextMessage(u, "Expected field password to have size between 3 and 64 but got 65.");
	}
	
	private void validateContextMessage(User user, String message) {
		IPersistenceManager pm = PersistenceService.instance().getPersistenceManager();
		pm.clearContextMessages();
		
		try {
			this.dao.save(user);
		} catch (ValidationException ex) {
			assertEquals(1, pm.getContextMessages().size());
			
			ContextMessage m = pm.getContextMessages().iterator().next();
			assertEquals(SeverityLevel.ERROR, m.getSeverityLevel());
			assertEquals(message, m.getMessage());
		}
	}
}