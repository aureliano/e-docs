package com.github.aureliano.edocs.service.bean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.aureliano.edocs.common.persistence.PersistenceService;
import com.github.aureliano.edocs.domain.dao.UserDao;
import com.github.aureliano.edocs.domain.entity.User;
import com.github.aureliano.edocs.domain.helper.PersistenceHelper;
import com.github.aureliano.edocs.service.TestHelper;

public class UserServiceBeanTest {

	private UserServiceBean bean;
	
	public UserServiceBeanTest() {
		TestHelper.initiliazeTestEnvironment();
		this.bean = new UserServiceBean();
	}

	@Before
	public void beforeTest() throws SQLException {
		PersistenceHelper.instance().deleteAllRecords();;
	}
	
	@Test
	public void testDeleteUser() throws SQLException {
		String name = "delete-by-entity";
		User u = new User()
			.withName(name)
			.withPassword("test123");
		User user = new UserDao().save(u);
		
		Connection conn = PersistenceService.instance().getPersistenceManager().getConnection();
		conn.setAutoCommit(true);
		
		ResultSet rs = conn.prepareStatement("select count(id) from users where name = '" + name + "'").executeQuery();
		rs.next();
		assertEquals(1, rs.getInt(1));
		rs.close();
		
		this.bean.deleteUser(user);
		
		rs = conn.prepareStatement("select count(id) from users where name = '" + name + "'").executeQuery();
		rs.next();
		assertEquals(0, rs.getInt(1));
		rs.close();
	}
	
	@Test
	public void testFindUserById() {
		User u = new User()
			.withName("iacopo")
			.withPassword("test123");
		
		User user1 = this.bean.saveUser(u);
		User user2 = this.bean.findUserById(user1.getId());
		
		assertEquals(user1, user2);
	}
	
	@Test
	public void testIsValidCredential() {
		String password = "test123";
		
		User u = new User()
			.withName("constantinvs")
			.withPassword(password);
		
		this.bean.saveUser(u);
		boolean validated = this.bean.isValidCredential(u.getName(), null);
		assertFalse(validated);
		
		validated = this.bean.isValidCredential(u.getName(), "invalidPass");
		assertFalse(validated);
		
		validated = this.bean.isValidCredential(u.getName(), password);
		assertTrue(validated);
	}
	
	@Test
	public void testSave() {
		User u = new User()
			.withName("iacopo")
			.withPassword("test123");
		
		User user1 = this.bean.saveUser(u);
		User user2 = this.bean.findUserById(user1.getId());
		
		assertEquals(user1, user2);
	}
	
	@Test
	public void testCreateDatabaseUser() {
		User user1 = this.bean.createDatabaseUser("constantinvs", "test123");
		User user2 = this.bean.findUserById(user1.getId());
		
		assertEquals(user1, user2);
		assertTrue(user1.getDbUser());
	}
	
	@Test
	public void testListUsers() {
		int totalUsers = 50;
		for (byte i = 0; i < totalUsers; i++) {
			TestHelper.createUserSample("user_" + (i + 1));
		}
		
		List<User> users = this.bean.listUsers();
		assertEquals(totalUsers, users.size());
	}
}