package triangle.mvc.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import triangle.mvc.model.User;

@Repository
public class UserDao {
	private static final Logger log = LoggerFactory.getLogger(UserDao.class);
	private static String COLLECTION_NAME = "users";

	@Autowired
	MongoTemplate mongoTemplate;

	public Boolean signin(User user) {
		Query query = new Query(new Criteria("email").is(user.getEmail()).and("password").is(user.getPassword()));
		log.debug(query.toString());
		List<User> listUser = mongoTemplate.find(query, User.class, COLLECTION_NAME);
		if (listUser.size() == 1)
			return true;
		return false;
	}
	
	public void signup(User user){
		mongoTemplate.insert(user, COLLECTION_NAME);
	}
	
	public Boolean isExistEmail(User user){
		Query query = new Query(new Criteria("email").is(user.getEmail()));
		log.debug(query.toString());
		User matchUser = mongoTemplate.findOne(query, User.class, COLLECTION_NAME);
		if(matchUser != null)
			return true;
		return false;
	}
}
