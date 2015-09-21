package triangle.mvc.dao;

import static org.junit.Assert.fail;

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
@ContextConfiguration("classpath:/applicationContext.xml")
public class UserDaoTest {
	private static final Logger log = LoggerFactory.getLogger(UserDaoTest.class);
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	private static String COLLECTION_NAME = "triangle";
	
	@Test
	public void mongoTemplate() throws Exception {
		log.debug(mongoTemplate.toString());
		if(mongoTemplate == null)
			fail();
	}
	
	@Test
	public void insert() throws Exception {
		User user = new User("kuku", "dodo");
		mongoTemplate.insert(user, COLLECTION_NAME);
	}
	
	@Test
	public void insertString() throws Exception {
		User user = new User("asdf", "ffff");
		mongoTemplate.insert(user, COLLECTION_NAME);
		
		//이렇게만 하면 user collection에 user object가 들어간다. 
		mongoTemplate.insert(user);
	}
	
	@Test
	public void getUser() throws Exception {
		User user = new User("kuku", "dodo");
		Query query = new Query(new Criteria("email").is(user.getEmail()));
		User u = mongoTemplate.findOne(query, User.class, COLLECTION_NAME);
		log.debug(u.getEmail());
	}
	
	@Test
	public void getUsers() throws Exception {
		List<User> listUser = (List<User>) mongoTemplate.findAll(User.class, COLLECTION_NAME);
		for (User user : listUser) {
			log.debug(user.toString());
		}
	}

}
