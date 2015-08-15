package triangle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;

import delaunay.Delaunay_Triangulation;
import delaunay.Point_dt;
import delaunay.Triangle_dt;

public class Drawer {
	// point list를 넘겨받는다.
	// 이미지 버퍼를 만든다.
	// 이미지 버퍼에 포인트들을 그린다.
	// 이미지 버퍼 리턴
	List<Point> points;
	BufferedImage bi;

	public Drawer(BufferedImage bi) {
		this.bi = bi;
	}

	public void drawPoints(List<Point> points) {
		for (Point point : points) {
			pointFill(point.getX(), point.getY(), Color.RED);
		}
	}

	public void drawPoints(List<Point> points, Color color) {
		for (Point point : points) {
			pointFill(point.getX(), point.getY(), color);
		}
	}

	public void pointFill(int ox, int oy, Color color) {
		int r = 1;

		if (!(ox - r < 0 || ox + r >= bi.getWidth() || oy - r < 0 || oy + r >= bi.getHeight()))
			for (int x = -r; x < r; x++) {
				int height = (int) Math.sqrt(r * r - x * x);

				for (int y = -height; y < height; y++)
					bi.setRGB(x + ox, y + oy, color.getRGB());
			}
	}

	public BufferedImage drawTriangle(List<Point> points) {
		Point_dt[] pointsDt = new Point_dt[points.size()];
		int i = 0;
		for (Point point : points) {
			pointsDt[i] = new Point_dt(point.getX(), point.getY());
			i++;
		}
		Delaunay_Triangulation dt = new Delaunay_Triangulation(pointsDt);
		Iterator<Triangle_dt> iter = dt.trianglesIterator();

		BufferedImage canvas = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
		Graphics2D g = canvas.createGraphics();
		g.setColor(Color.BLACK);
		
		int cnt = 0;
		while (iter.hasNext()) {
			Triangle_dt triangle = iter.next();
			cnt++;
			if (triangle.p1() != null && triangle.p2() != null && triangle.p3() != null) {
				Point_dt p1 = triangle.p1();
				Point_dt p2 = triangle.p2();
				Point_dt p3 = triangle.p3();

				g.drawLine((int)p1.x(), (int)p1.y(), (int)p2.x(), (int)p2.y());
				g.drawLine((int)p2.x(), (int)p2.y(), (int)p3.x(), (int)p3.y());
				g.drawLine((int)p3.x(), (int)p3.y(), (int)p1.x(), (int)p1.y());
			}
		}
		System.out.println("Triangle : " + cnt);
		return canvas;
	}

}
