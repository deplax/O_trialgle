package triangleTest;

import org.junit.Test;

import faceDetection.FaceDetector;

public class FaceDetectorTest {

	@Test
	public void faceDetectTest() {
		String originPath = "./img/IU2.jpg";
		FaceDetector f = new FaceDetector();
		f.getFacePositioin(originPath);
	}

}
