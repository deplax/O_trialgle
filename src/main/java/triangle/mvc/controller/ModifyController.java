package triangle.mvc.controller;

import java.awt.image.BufferedImage;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import triangle.CannyEdgeDetector;
import triangle.Drawer;
import triangle.Point;
import triangle.PointGenerator;
import triangle.PointRemover;
import triangle.mvc.model.Canvas;

@Controller
public class ModifyController {

	private static final Logger log = LoggerFactory
			.getLogger(ModifyController.class);

	@Autowired
	private ServletContext servletContext;

	@RequestMapping(value = "/modifyFloor", method = RequestMethod.GET)
	public String ModifyFloor(HttpSession session) {
		log.debug("enter modifyFloor page get");
		Canvas c = (Canvas) session.getAttribute("canvas");
		c.setPointList(getPointList(c.getBufferedImage()));

		return "modifyFloor";
	}

	@RequestMapping(value = "/modifyFloor/getPointList", method = RequestMethod.GET)
	public @ResponseBody List<Point> PointList(HttpSession session) {
		Canvas c = (Canvas) session.getAttribute("canvas");
		log.debug("pointList : {}", c.getPointList().size());
		return c.getPointList();
	}

	public List<Point> getPointList(BufferedImage bi) {
		// 버퍼이미지를 받아서
		// 포인트리스트를 반환한다.

		// create the detector
		CannyEdgeDetector detector = new CannyEdgeDetector();
		// adjust its parameters as desired
		detector.setLowThreshold(1.3f);
		detector.setHighThreshold(2f);
		detector.setSourceImage(bi);
		detector.process();
		BufferedImage edges = detector.getEdgesImage();

		// 랜덤하게 포인트를 찍는다.
		int w = bi.getWidth();
		int h = bi.getHeight();
		// divide 제곱으로 적어야한다.
		PointGenerator pg = new PointGenerator(w, h, 2000, 20);

//		// 얼굴 위치를 디텍트 한다.
//		FaceDetector f = new FaceDetector(bi);
//		f.findFacePoints();
//		List<int[]> faceList = f.getFacePosition();
//		// 얼굴 위치에 포인트를 찍는다.
//		for (int[] face : faceList) {
//			pg.generateCircleArea(face[0], face[1], face[2], 50);
//		}

		// 그려보자.
		Drawer drawer = new Drawer(edges);
		drawer.drawPoints(pg.getPointList());

		// 포인트에 선이 없다면 포인트를 지운다.
		PointRemover pr = new PointRemover(pg.getPointList());
		pr.removePoint(edges, 5);

		return pg.getPointList();
	}
}