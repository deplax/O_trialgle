package triangleTest;

import org.junit.Test;

import triangle.faceDetection.FaceDetector;

public class FaceDetectorTest {

	@Test
	public void faceDetectTest() {
		String originPath = "./img/002.jpg";
		FaceDetector f = new FaceDetector(originPath);
		f.findFacePoints();
		
	}

}
