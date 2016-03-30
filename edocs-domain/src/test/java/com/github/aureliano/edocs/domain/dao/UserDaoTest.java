package com.github.aureliano.edocs.domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.aureliano.edocs.common.persistence.IDao;
import com.github.aureliano.edocs.domain.entity.User;
import com.github.aureliano.edocs.domain.helper.PersistenceHelper;

public class UserDaoTest {
	
	private IDao<User> dao;
	
	public UserDaoTest() {
		PersistenceHelper.instance().prepareDatabase();
		this.dao = new UserDao();
	}

	@Before
	public void beforeTest() throws SQLException {
		PersistenceHelper.instance().executeUpdate("delete from users");
	}
	
	@Test
	public void testSave() {
		User u = new User()
			.withName("agustine")
			.withPassword("test123");
		
		User user = this.dao.save(u);
		assertNotNull(user.getId());
		assertEquals(u.getName(), user.getName());
		assertEquals(u.getPassword(), user.getPassword());
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
		
		this.dao.delete(user);
		
		rs = PersistenceHelper.instance().executeQuery("select count(id) from users where name = '" + name + "'");
		rs.next();
		assertEquals(0, rs.getInt(1));
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
		
		this.dao.delete(user.getId());
		
		rs = PersistenceHelper.instance().executeQuery("select count(id) from users where name = '" + name + "'");
		rs.next();
		assertEquals(0, rs.getInt(1));
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
		
		List<User> data = this.dao.search(user1);
		
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
}