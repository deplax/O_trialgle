package triangle.mvc.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import triangle.mvc.model.Canvas;
import triangle.mvc.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContextTest.xml")
public class CanvasDaoTest {
	private static final Logger log = LoggerFactory.getLogger(CanvasDaoTest.class);

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	private CanvasDao canvasDao;
	
	@Autowired
	private UserDao userDao;
	
	@Test
	public void findCanvas() throws Exception {
		User user = userDao.getUser("kuku");
		List<Canvas> canvasList = canvasDao.findCanvas(user);
		for (Canvas canvas : canvasList) {
			log.debug(canvas.toString());
		}
	}
}
