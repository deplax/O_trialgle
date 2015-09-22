package triangle.mvc.model;

import java.awt.image.BufferedImage;
import java.util.List;

import triangle.Point;

public class Canvas {
	private BufferedImage bufferedImage;
	private List<Point> pointList;
	private String imgURL;
	
	// *** image processing parameter ***
	// sampling value
	// edge LowFilter
	// edge HighFilter
	// imageDetect value

	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}
	
	//triangle list
	//canvas size
	//sampling value
	//faceDetection bool
	
}
