package triangleTest;
//
//import static org.junit.Assert.*;
//
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//import javax.imageio.ImageIO;
//
//import org.junit.Test;
//
//import triangle.faceDetection.FaceDetector;
//
//public class LinearDetectTest {
//
//	@Test
//	public void test() throws IOException {
//		String originPath = "./img/1.jpg";
//		String outputPath = "./img/test2.jpg";
//
//		File originFile = new File(originPath);
//		File outputFile = new File(outputPath);
//		
//		BufferedImage originImg;
//		originImg = ImageIO.read(originFile);
//		
//		//System.out.println(System.getProperty("java.library.path"));
//		FaceDetector fd = new FaceDetector(originImg);
//		System.out.println(fd);
//		fd.findFacePoints();
//		List<int[]> fdList = fd.getFacePosition();
//		for (int[] is : fdList) {
//			System.out.println(is.toString());
//		}
//	}
//
//}
