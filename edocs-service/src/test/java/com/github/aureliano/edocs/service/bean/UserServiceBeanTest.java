package com.github.aureliano.edocs.service.bean;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.github.aureliano.edocs.common.persistence.PersistenceService;
import com.github.aureliano.edocs.domain.dao.UserDao;
import com.github.aureliano.edocs.domain.entity.User;
import com.github.aureliano.edocs.domain.helper.PersistenceHelper;
import com.github.aureliano.edocs.service.EdocsServicePersistenceManager;

public class UserServiceBeanTest {

	private UserServiceBean bean;
	
	public UserServiceBeanTest() {
		PersistenceHelper.instance().prepareDatabase();
		PersistenceService ps = PersistenceService.instance();
		
		if (ps.getPersistenceManager() == null) {
			EdocsServicePersistenceManager pm = new EdocsServicePersistenceManager();
			pm.setConnection(PersistenceHelper.instance().getConnection());
			ps.registerPersistenceManager(pm);
		}
		
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
		
		User user1 = new UserDao().save(u); // TODO: Replace by service bean method.
		User user2 = this.bean.findUserById(user1.getId());
		
		assertEquals(user1, user2);
	}
}