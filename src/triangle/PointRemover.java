package triangle;

import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;

public class PointRemover {

	BufferedImage bi;
	List<Point> points;

	public PointRemover(List<Point> points) {
		this.points = points;
	}

	public void removePoint(BufferedImage bi, int r) {
		this.bi = bi;
		Iterator<Point> it = points.iterator();
		while(it.hasNext()){
			Point p = it.next();
			if(!isWhite(p.getX(), p.getY(), r)){
				it.remove();
			}
		}
	}

	public Boolean isWhite(int ox, int oy, int r) {

		if (!(ox - r < 0 || ox + r >= bi.getWidth() || oy - r < 0 || oy + r >= bi.getHeight()))
			for (int x = -r; x < r; x++) {
				int height = (int) Math.sqrt(r * r - x * x);

				for (int y = -height; y < height; y++){
					if(bi.getRGB(x + ox, y + oy) == -1)
						return true;
				}
			}
		return false;
	}
}
