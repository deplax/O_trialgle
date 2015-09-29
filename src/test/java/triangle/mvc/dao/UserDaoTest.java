package triangle.mvc.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import triangle.mvc.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContextTest.xml")
public class UserDaoTest {
	private static final Logger log = LoggerFactory.getLogger(UserDaoTest.class);
	private static String COLLECTION_NAME = "users";
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	private UserDao userDao;
	
	@Test
	public void loginTest() throws Exception {
		User user1 = new User("kuku", "dodo");
		User user2 = new User("kuku", "do");
		User user3 = new User("ku", "dodo");
		assertEquals(true, userDao.signin(user1));
		assertEquals(false, userDao.signin(user2));
		assertEquals(false, userDao.signin(user3));
	}

	@Test
	public void mongoTemplate() throws Exception {
		log.debug(mongoTemplate.toString());
		if (mongoTemplate == null)
			fail();
	}

	@Test
	public void getUser() throws Exception {
		User user = new User("kuku", "dodo");
		Query query = new Query(new Criteria("email").is(user.getEmail()));
		log.debug(query.toString());
		User u = mongoTemplate.findOne(query, User.class, COLLECTION_NAME);
		log.debug(u.getEmail());
	}

	@Test
	public void getUsers() throws Exception {
		List<User> listUser = (List<User>) mongoTemplate.findAll(User.class,
				COLLECTION_NAME);
		for (User user : listUser) {
			log.debug(user.toString());
		}
	}
	
	@Test
	public void one() throws Exception {
		User user = new User("kuku", "dodo");
		Query query = new Query(new Criteria("email").is(user.getEmail()));
		User u = mongoTemplate.findOne(query, User.class, COLLECTION_NAME);
		log.debug(u.toString());
	}

	@Test
	public void selectQuerySelect() throws Exception {
		Query query = new Query(new Criteria("email").is("kuku")
				.and("password").is("dodo"));
		log.debug(query.toString());
		List<User> listUser = mongoTemplate.find(query, User.class,
				COLLECTION_NAME);
		for (User user : listUser) {
			log.debug(user.toString());
		}
	}

}
