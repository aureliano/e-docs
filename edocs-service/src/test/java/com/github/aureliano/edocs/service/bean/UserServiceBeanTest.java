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

public class UserServiceBeanTest {

	public UserServiceBeanTest() {
		PersistenceHelper.instance().prepareDatabase();
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
		new UserDao().save(u);
		
		Connection conn = PersistenceService.instance().getPersistenceManager().getConnection();
		conn.setAutoCommit(true);
		
		ResultSet rs = conn.prepareStatement("select count(id) from users where name = '" + name + "'").executeQuery();
		rs.next();
		assertEquals(1, rs.getInt(1));
		rs.close();
		
		UserServiceBean bean = new UserServiceBean();
		bean.deleteUser(u);
		
		rs = conn.prepareStatement("select count(id) from users where name = '" + name + "'").executeQuery();
		rs.next();
		assertEquals(0, rs.getInt(1));
		rs.close();
	}
}