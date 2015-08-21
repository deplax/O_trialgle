package faceDetection;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FaceDetector {

	List<int[]> facePosition = new ArrayList<int[]>();

	public List<int[]> getFacePositioin(String img) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		String caacade = "haarcascade_frontalface_alt2";
		CascadeClassifier faceDetector = new CascadeClassifier(
				"/Users/Deplax/Downloads/opencv-3.0.0/data/haarcascades/" + caacade + ".xml");
		// CascadeClassifier faceDetector = new
		// CascadeClassifier("C:/Program Files/opencv/build/etc/haarcascades/haarcascade_frontalface_alt.xml");
		Mat image = Imgcodecs.imread(img);

		MatOfRect faceDetections = new MatOfRect();
		faceDetector.detectMultiScale(image, faceDetections);

		System.out.println(String.format("Detected %s faces",
				faceDetections.toArray().length));

		for (Rect rect : faceDetections.toArray()) {
			Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(
					rect.x + rect.width, rect.y + rect.height), new Scalar(0,
					255, 0));

			int[] position = { rect.x, rect.y, rect.width / 2 };
			facePosition.add(position);
		}

		faceDetectOutput(image, "./img/output.jpg");
		return facePosition;

	}

	public void faceDetectOutput(Mat image, String output) {
		Imgcodecs.imwrite(output, image);
	}
}
