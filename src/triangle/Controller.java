package triangle;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Controller {

	public static void main(String[] args) {

		Controller controllor = new Controller();

		String originPath = "./img/test.jpg";
		String outputPath = "./img/test2.jpg";

		File originFile = new File(originPath);
		File outputFile = new File(outputPath);

		BufferedImage originImg;
		try {
			long startTime = System.currentTimeMillis();
			originImg = ImageIO.read(originFile);

			// create the detector
			CannyEdgeDetector detector = new CannyEdgeDetector();

			// adjust its parameters as desired
			detector.setLowThreshold(1f);
			detector.setHighThreshold(2f);

			// apply it to an image
			detector.setSourceImage(originImg);
			detector.process();
			BufferedImage edges = detector.getEdgesImage();

			// canny edge detection을 수행한다.
			// 무작위의 점을 찍는다.
			// 무작위 점을 리스트로 가진다.

			long endTime = System.currentTimeMillis();
			System.out.println(endTime - startTime + " ms");

			ImageIO.write(edges, "jpg", outputFile);

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
		String str = "var text = '" + " " + "'\n"
				+ "var node = document.createElement('div');\n"
				+ "node.innerText = text;\n"
				+ "document.querySelector('body').appendChild(node);";
		out.write(str);
		out.close();
	}

}
