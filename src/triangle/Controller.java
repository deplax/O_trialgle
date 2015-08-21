package triangle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import faceDetection.FaceDetector;

public class Controller {

	public static void main(String[] args) {

		Controller controllor = new Controller();

		String originPath = "./img/06.jpg";
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
			PointGenerator pg = new PointGenerator(w, h, 900, 30);

			// 얼굴 위치를 디텍트 한다.
			FaceDetector f = new FaceDetector();
			List<int[]> faceList = f.getFacePositioin(originPath);
			
			// 얼굴 위치에 포인트를 찍는다.
			for (int[] face : faceList) {
				pg.generateCircleArea(face[0], face[1], face[2], 1000);	
			}
			
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
			drawer.setOrigin(originImg);
			BufferedImage result = drawer.fillTriangle();
			
			long endTime = System.currentTimeMillis();
			System.out.println(endTime - startTime + " ms");

			String outputPath2 = "./img/test3.jpg";
			File outputFile2 = new File(outputPath2);
			ImageIO.write(result, "png", outputFile);
			ImageIO.write(edges, "png", outputFile2);

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
// TriangleFill
// http://www.sunshine2k.de/coding/java/TriangleRasterization/TriangleRasterization.html
// http://www-users.mat.uni.torun.pl/~wrona/3d_tutor/tri_fillers.html
// FaceDetection
// https://blog.openshift.com/day-12-opencv-face-detection-for-java-developers/