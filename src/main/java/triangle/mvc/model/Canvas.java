package triangle.mvc.model;

import java.awt.image.BufferedImage;
import java.util.List;

import org.bson.types.ObjectId;

import triangle.Point;

public class Canvas {
	private BufferedImage bufferedImage;
	private String preview;
	private List<Point> pointList;
	private String imgURL;
	private ObjectId userId;

	// *** image processing parameter ***
	// sampling value
	// edge LowFilter
	// edge HighFilter
	// imageDetect value

	public ObjectId getUserId() {
		return userId;
	}

	public void setUserId(ObjectId userId) {
		this.userId = userId;
	}

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

	@Override
	public String toString() {
		return "Canvas [pointList=" + pointList + ", userId=" + userId + "]";
	}	
}
