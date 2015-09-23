package triangle.faceDetection;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FaceDetector {
	//private static final String HAAR_DIR = "/Users/Deplax/Downloads/opencv-3.0.0/data/haarcascades/";
	 private static final String HAAR_DIR = "C:/Program Files/opencv/build/etc/haarcascades/";

	private static final String FACE = "haarcascade_frontalface_alt2.xml";
	private static final String LEFT_EYE = "haarcascade_mcs_lefteye.xml";
	private static final String RIGHT_EYE = "haarcascade_mcs_righteye.xml";
	private static final String NOSE = "haarcascade_mcs_nose.xml";
	private static final String MOUTH = "haarcascade_mcs_mouth.xml";

	private String imgPath;
	private Mat image;

	// RECT를 가져오는 함수 제작.
	// RECT를 그리는 함수.

	List<int[]> facePosition = new ArrayList<int[]>();

	public FaceDetector(BufferedImage bi) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		this.image = bufferedImagetoMat(bi);
	}

	public List<int[]> getFacePosition() {
		return facePosition;
	}

	public void outputImage(Mat image) {
		faceDetectOutput(image, "./img/output.jpg");
	}
	
	public void findFacePoints(){
		findPoint(LEFT_EYE);
		findPoint(RIGHT_EYE);
		findPoint(NOSE);
		findPoint(MOUTH);
		
	}
	
	public Mat bufferedImagetoMat(BufferedImage image){
		byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
		mat.put(0, 0, data);
		return mat;
	}



	public void findPoint(String pos) {
		CascadeClassifier faceDetector = new CascadeClassifier(HAAR_DIR + pos);
		MatOfRect detections = new MatOfRect();
		faceDetector.detectMultiScale(image, detections);
		System.out.println(String.format("Detected %s",
				detections.toArray().length));
		
		for (Rect rect : detections.toArray()) {
		//Rect rect = detections.toArray()[0];
		Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x
				+ rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
		int[] position = { rect.x, rect.y, rect.width / 2 };
		facePosition.add(position);
		}
		outputImage(image);
	}

	public List<int[]> getFacePositioin(String img) {
		// Library Load
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		String caacade = "haarcascade_frontalface_alt2";
//		CascadeClassifier faceDetector = new CascadeClassifier(
//				"/Users/Deplax/Downloads/opencv-3.0.0/data/haarcascades/"
//						+ caacade + ".xml");
		CascadeClassifier faceDetector = new
		CascadeClassifier("C:/Program Files/opencv/build/etc/haarcascades/"
						+ caacade + ".xml");
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

// https://github.com/thearn/webcam-pulse-detector