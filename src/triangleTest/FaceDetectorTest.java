package triangleTest;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import faceDetection.FaceDetector;

public class FaceDetectorTest {

	@Test
	public void faceDetectTest() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		FaceDetector f = new FaceDetector();
//		Mat image = Imgcodecs.imread("./img/IU.jpg");
//		image = f.detectFace(image);
//		image = f.enhance(image);
//		String filename = "./img/output.png";
//		System.out.println(String.format("Writing %s", filename));
//		Imgcodecs.imwrite(filename, image);
	}

}
