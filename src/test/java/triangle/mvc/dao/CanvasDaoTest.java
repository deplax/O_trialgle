package triangle.mvc.dao;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

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

import triangle.CannyEdgeDetector;
import triangle.Drawer;
import triangle.PointGenerator;
import triangle.PointRemover;
import triangle.mvc.model.Canvas;
import triangle.mvc.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContextTest.xml")
public class CanvasDaoTest {
	private static final Logger log = LoggerFactory.getLogger(CanvasDaoTest.class);
	private static String COLLECTION_NAME = "canvas";

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	private CanvasDao canvasDao;
	
	@Autowired
	private UserDao userDao;
	
	@Test
	public void insert() throws Exception {
		String originPath = "./img/01.jpg";
		File originFile = new File(originPath);
		BufferedImage originImg;
		originImg = ImageIO.read(originFile);
		CannyEdgeDetector detector = new CannyEdgeDetector();
		detector.setLowThreshold(1f);
		detector.setHighThreshold(2f);
		detector.setSourceImage(originImg);
		detector.process();
		BufferedImage edges = detector.getEdgesImage();
		int w = originImg.getWidth();
		int h = originImg.getHeight();
		PointGenerator pg = new PointGenerator(w, h, 4000, 20);
		Drawer drawer = new Drawer(edges);
		drawer.drawPoints(pg.getPointList());
		PointRemover pr = new PointRemover(pg.getPointList());
		pr.removePoint(edges, 5);
		
		Canvas canvas = new Canvas();
		canvas.setPointList(pg.getPointList());
		canvas.setUserId(userDao.getUser("dodo").get_id());
		
		mongoTemplate.insert(canvas, COLLECTION_NAME);
	}
	
	@Test
	public void findCanvas() throws Exception {
		User user = userDao.getUser("kuku");
		List<Canvas> canvasList = canvasDao.findCanvas(user);
		for (Canvas canvas : canvasList) {
			log.debug(canvas.toString());
		}
	}
}
