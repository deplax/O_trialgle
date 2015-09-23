package triangle.mvc.model;

import java.awt.image.BufferedImage;
import java.util.List;

import triangle.Point;

public class Canvas {
	private BufferedImage bufferedImage;
	private String preview;
	private List<Point> pointList;
	private String imgURL;

	// *** image processing parameter ***
	// sampling value
	// edge LowFilter
	// edge HighFilter
	// imageDetect value

	public String getPreview() {
		return preview;
	}

	public void setPreview(String preview) {
		this.preview = preview;
	}
	
	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public void setBufferedImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
	}

	public List<Point> getPointList() {
		return pointList;
	}

	public void setPointList(List<Point> pointList) {
		this.pointList = pointList;
	}

	public String getImgURL() {
		return imgURL;
	}

	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}
}
