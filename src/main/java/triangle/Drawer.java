package triangle;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import util.Calc;
import util.NoAscCompare;
import triangle.delaunay.Delaunay_Triangulation;
import triangle.delaunay.Point_dt;
import triangle.delaunay.Triangle_dt;

public class Drawer {
	// point list를 넘겨받는다.
	// 이미지 버퍼를 만든다.
	// 이미지 버퍼에 포인트들을 그린다.
	// 이미지 버퍼 리턴
	// List<Point> points;
	List<Integer> pixels = new ArrayList<>();
	BufferedImage bi;
	BufferedImage origin;
	Iterator<Triangle_dt> triangles;

	public Drawer(BufferedImage bi) {
		this.bi = bi;
	}

	public void setOrigin(BufferedImage origin) {
		this.origin = origin;
	}

	public void drawPoints(List<Point> points) {
		for (Point point : points) {
			pointFill(point.x(), point.y(), Color.RED);
		}
	}

	public void drawPoints(List<Point> points, Color color) {
		for (Point point : points) {
			pointFill(point.x(), point.y(), color);
		}
	}

	public void pointFill(int ox, int oy, Color color) {
		int r = 1;

		if (!(ox - r < 0 || ox + r >= bi.getWidth() || oy - r < 0 || oy + r >= bi
				.getHeight()))
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
			pointsDt[i] = new Point_dt(point.x(), point.y());
			i++;
		}
		Delaunay_Triangulation dt = new Delaunay_Triangulation(pointsDt);
		triangles = dt.trianglesIterator();
		Iterator<Triangle_dt> iter = dt.trianglesIterator();

		BufferedImage canvas = new BufferedImage(bi.getWidth(), bi.getHeight(),
				bi.getType());
		Graphics2D g = canvas.createGraphics();
		g.setColor(Color.BLACK);
		g.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON));
		int cnt = 0;
		while (iter.hasNext()) {
			Triangle_dt triangle = iter.next();
			if (triangle.p1() != null && triangle.p2() != null
					&& triangle.p3() != null) {
				cnt++;
				Point_dt p1 = triangle.p1();
				Point_dt p2 = triangle.p2();
				Point_dt p3 = triangle.p3();

				g.drawLine((int) p1.x(), (int) p1.y(), (int) p2.x(),
						(int) p2.y());
				g.drawLine((int) p2.x(), (int) p2.y(), (int) p3.x(),
						(int) p3.y());
				g.drawLine((int) p3.x(), (int) p3.y(), (int) p1.x(),
						(int) p1.y());
			}
		}
		System.out.println("Triangle : " + cnt);
		return canvas;
	}

	public BufferedImage fillTriangle() {
		BufferedImage canvas = new BufferedImage(bi.getWidth(), bi.getHeight(),
				bi.getType());
		int cnt = 0;
		Graphics2D g = canvas.createGraphics();
		g.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY));
		g.addRenderingHints(new RenderingHints(
				RenderingHints.KEY_COLOR_RENDERING,
				RenderingHints.VALUE_COLOR_RENDER_QUALITY));
		g.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON));
		g.addRenderingHints(new RenderingHints(RenderingHints.KEY_DITHERING,
				RenderingHints.VALUE_DITHER_ENABLE));
		
		while (triangles.hasNext()) {
			Triangle_dt triangle = triangles.next();
			if (triangle.p1() != null && triangle.p2() != null
					&& triangle.p3() != null) {
				cnt++;

				//triangleAvgColor2(triangle);
				//g.setPaint(makeStdevPaint(triangle));

				g.setColor(new Color(triangleAvgColor(triangle)));
				int[] xs = { (int) triangle.p1().x(), (int) triangle.p2().x(),
						(int) triangle.p3().x() };
				int[] ys = { (int) triangle.p1().y(), (int) triangle.p2().y(),
						(int) triangle.p3().y() };

				g.fillPolygon(xs, ys, 3);
				g.fillPolygon(xs, ys, 3);

			}
		}
		System.out.println("Triangle : " + cnt);
		return canvas;
	}

	public GradientPaint makeStdevPaint(Triangle_dt triangle) {
		List<Integer> rList = getRgbArr('r');
		Integer[] rArr = new Integer[rList.size()];
		rList.toArray(rArr);
		double rStd = Calc.stdev(rArr, 1);

		List<Integer> gList = getRgbArr('g');
		Integer[] gArr = new Integer[gList.size()];
		gList.toArray(gArr);
		double gStd = Calc.stdev(gArr, 1);

		List<Integer> bList = getRgbArr('b');
		Integer[] bArr = new Integer[bList.size()];
		bList.toArray(bArr);
		pixels.clear();
		double bStd = Calc.stdev(bArr, 1);

		double rAvg = Calc.mean(rArr);
		double gAvg = Calc.mean(gArr);
		double bAvg = Calc.mean(bArr);

		int up = rgbToInt(checkBoudary(rAvg + rStd), checkBoudary(gAvg + gStd),
				checkBoudary(bAvg + bStd));
		int down = rgbToInt(checkBoudary(rAvg - rStd),
				checkBoudary(gAvg - gStd), checkBoudary(bAvg - bStd));

		Color upColor = new Color(up);
		Color downColor = new Color(down);

		GradientPaint gp = new GradientPaint((int) triangle.p1().x(),
				(int) triangle.p1().y(), upColor, (int) triangle.p3().x(),
				(int) triangle.p3().y(), downColor);
		return gp;
	}

	public int checkBoudary(double num) {
		if (num > 255)
			return 255;
		if (num < 0)
			return 0;
		return (int) num;
	}

	// r, g, b를 리스트로 받아온다.
	public List<Integer> getRgbArr(char rgb) {
		int mask = 0;
		int shift = 0;
		if (rgb == 'r') {
			mask = 0b00000000111111110000000000000000;
			shift = 16;
		} else if (rgb == 'g') {
			mask = 0b00000000000000001111111100000000;
			shift = 8;
		} else if (rgb == 'b') {
			mask = 0b00000000000000000000000011111111;
			shift = 0;
		}
		int color = 0;
		List<Integer> rgbList = new ArrayList<Integer>();
		for (Integer pixel : pixels) {
			color = (mask & pixel) >> shift;
			rgbList.add(color);
		}
		return rgbList;
	}

	public int triangleAvgColor(Triangle_dt triangle) {
		List<Point_dt> list = sortPointY(triangle);
		Point v1 = new Point((int) list.get(0).x(), (int) list.get(0).y());
		Point v2 = new Point((int) list.get(1).x(), (int) list.get(1).y());
		Point v3 = new Point((int) list.get(2).x(), (int) list.get(2).y());

		if (v2.y() == v3.y()) {
			fillBottomFlatTriangle(v1, v2, v3);
		} else if (v1.y() == v2.y()) {
			fillTopFlatTriangle(v1, v2, v3);
		} else {

			Point v4 = new Point(
					(int) (v1.x() + ((float) (v2.y() - v1.y()) / (float) (v3.y() - v1.y()))
							* (v3.x() - v1.x())), v2.y());
			fillBottomFlatTriangle(v1, v2, v4);
			fillTopFlatTriangle(v2, v4, v3);
		}
		return avgPixels();

	}
	
	public int triangleAvgColor2(Triangle_dt triangle) {
		List<Point_dt> list = sortPointY(triangle);
		Point v1 = new Point((int) list.get(0).x(), (int) list.get(0).y());
		Point v2 = new Point((int) list.get(1).x(), (int) list.get(1).y());
		Point v3 = new Point((int) list.get(2).x(), (int) list.get(2).y());

		if (v2.y() == v3.y()) {
			fillBottomFlatTriangle(v1, v2, v3);
		} else if (v1.y() == v2.y()) {
			fillTopFlatTriangle(v1, v2, v3);
		} else {

			Point v4 = new Point(
					(int) (v1.x() + ((float) (v2.y() - v1.y()) / (float) (v3.y() - v1.y()))
							* (v3.x() - v1.x())), v2.y());
			fillBottomFlatTriangle(v1, v2, v4);
			fillTopFlatTriangle(v2, v4, v3);
		}
		return 0;
		// return avgPixels();

	}

	public int avgPixels() {
		int r = 0, g = 0, b = 0;
		for (Integer pixel : pixels) {
			r += (0b00000000111111110000000000000000 & pixel) >> 16; // R
			g += (0b00000000000000001111111100000000 & pixel) >> 8; // G
			b += (0b00000000000000000000000011111111 & pixel) >> 0; // B
		}
		int err = 0;
		if (pixels.size() == 0)
			err = 1;
		r = r / (pixels.size() + err);
		g = g / (pixels.size() + err);
		b = b / (pixels.size() + err);

		int pixel = rgbToInt(r, g, b);
		pixels.clear();
		return pixel;
	}

	public int rgbToInt(int r, int g, int b) {
		int pixel = 0b11111111000000000000000000000000;
		pixel += (r & 0xff) << 16; // R
		pixel += (g & 0xff) << 8; // G
		pixel += (b & 0xff) << 0; // B
		return pixel;
	}

	public List<Point_dt> sortPointY(Triangle_dt t) {
		List<Point_dt> list = new ArrayList<Point_dt>();
		list.add(t.p1());
		list.add(t.p2());
		list.add(t.p3());
		Collections.sort(list, new NoAscCompare());

		// for (Point_dt point_dt : list) {
		// System.out.println("x: " + point_dt.x() + " y: " + point_dt.y());
		// }
		// System.out.println();

		return list;
	}

	public void fillBottomFlatTriangle(Point v1, Point v2, Point v3) {
		if (v2.x() > v3.x())
			swapPoint(v2, v3);

		float invslope1 = (float) (v2.x() - v1.x()) / (float) (v2.y() - v1.y());
		float invslope2 = (float) (v3.x() - v1.x()) / (float) (v3.y() - v1.y());

		float curx1 = v1.x();
		float curx2 = v1.x();

		for (int scanlineY = v1.y(); scanlineY <= v2.y(); scanlineY++) {
			drawLine((int) curx1, (int) curx2, scanlineY);
			curx1 += invslope1;
			curx2 += invslope2;
		}
	}

	public void fillTopFlatTriangle(Point v1, Point v2, Point v3) {
		if (v1.x() > v2.x())
			swapPoint(v1, v2);

		float invslope1 = (float) (v3.x() - v1.x()) / (float) (v3.y() - v1.y());
		float invslope2 = (float) (v3.x() - v2.x()) / (float) (v3.y() - v2.y());

		float curx1 = v3.x();
		float curx2 = v3.x();

		for (int scanlineY = v3.y(); scanlineY > v1.y(); scanlineY--) {
			curx1 -= invslope1;
			curx2 -= invslope2;
			drawLine((int) curx1, (int) curx2, scanlineY);
		}
	}

	public void swapPoint(Point a, Point b) {
		Point temp = new Point(a.x(), a.y());
		a.setX(b.x());
		a.setY(b.y());
		b.setX(temp.x());
		b.setY(temp.y());
	}

	public void drawLine(int sx, int ex, int sy) {
		for (int i = 0; i < ex - sx; i++) {
			// bi.setRGB(sx + i, sy, Color.cyan.getRGB());
			pixels.add(origin.getRGB(sx + i, sy));
		}
	}
}
