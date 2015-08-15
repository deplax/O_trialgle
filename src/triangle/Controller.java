package triangle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Controller {

	public static void main(String[] args) {

		Controller controllor = new Controller();

		String originPath = "./img/IU.jpg";
		String outputPath = "./img/test2.jpg";

		File originFile = new File(originPath);
		File outputFile = new File(outputPath);

		BufferedImage originImg;
		try {
			long startTime = System.currentTimeMillis();

			// 이미지를 읽어온다.
			originImg = ImageIO.read(originFile);

			// create the detector
			CannyEdgeDetector detector = new CannyEdgeDetector();
			// adjust its parameters as desired
			detector.setLowThreshold(1f);
			detector.setHighThreshold(2f);
			detector.setSourceImage(originImg);
			detector.process();
			BufferedImage edges = detector.getEdgesImage();

			// 랜덤하게 포인트를 찍는다.
			int w = originImg.getWidth();
			int h = originImg.getHeight();
			// divide 제곱으로 적어야한다.
			PointGenerator pg = new PointGenerator(w, h, 1800, 30);

			// 그려보자.
			Drawer drawer = new Drawer(edges);
			drawer.drawPoints(pg.getPointList());

			// 포인트에 선이 없다면 포인트를 지운다.
			PointRemover pr = new PointRemover(pg.getPointList());
			pr.removePoint(edges, 5);

			// 다시 그려보자.
			drawer.drawPoints(pg.getPointList(), Color.cyan);

			// 삼각삼각
			BufferedImage canvas = drawer.drawTriangle(pg.getPointList());
			
			// 칠하자.
			drawer.fillTriangle();
			
			long endTime = System.currentTimeMillis();
			System.out.println(endTime - startTime + " ms");

			ImageIO.write(edges, "png", outputFile);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			fileWriteForGrunt();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void fileWriteForGrunt() throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter("./img/test.js"));
		String str = "var text = '" + " " + "'\n" + "var node = document.createElement('div');\n"
				+ "node.innerText = text;\n" + "document.querySelector('body').appendChild(node);";
		out.write(str);
		out.close();
	}

}

// [참고 사이트]
// CannyEdge
// http://www.tomgibara.com/computer-vision/canny-edge-detector
// http://carstart.tistory.com/188
// DelaunayTrianulation
// http://darkpgmr.tistory.com/96
// RegionFilling
// http://derek.dkit.ie/graphics/regionFilling/regionFilling.html
// Triangulation
// http://jonathanpuckey.com/projects/delaunay-raster/