package triangle.mvc.dao;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import triangle.mvc.model.Canvas;
import triangle.mvc.model.User;

@Repository
public class CanvasDao {
	private static final Logger log = LoggerFactory.getLogger(CanvasDao.class);
	private static String COLLECTION_NAME = "canvas";
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	public User getUserId(User user) throws Exception {
		Query query = new Query(new Criteria("email").is(user.getEmail()));
		User u = mongoTemplate.findOne(query, User.class, "users");
		log.debug(u.toString());
		return u;
	}
	
	public List<Canvas> findCanvas(User user){
		Query query = new Query(new Criteria("userId").is(user.get_id()));
		List<Canvas> listCanvas = mongoTemplate.find(query, Canvas.class, COLLECTION_NAME);
		log.debug("listSize : {}",listCanvas.size());
		return listCanvas;
	}	
}
