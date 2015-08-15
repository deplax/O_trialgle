package triangle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import util.NoAscCompare;
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
	Iterator<Triangle_dt> triangles;

	public Drawer(BufferedImage bi) {
		this.bi = bi;
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
			pointsDt[i] = new Point_dt(point.x(), point.y());
			i++;
		}
		Delaunay_Triangulation dt = new Delaunay_Triangulation(pointsDt);
		triangles = dt.trianglesIterator();
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

				g.drawLine((int) p1.x(), (int) p1.y(), (int) p2.x(), (int) p2.y());
				g.drawLine((int) p2.x(), (int) p2.y(), (int) p3.x(), (int) p3.y());
				g.drawLine((int) p3.x(), (int) p3.y(), (int) p1.x(), (int) p1.y());
			}
		}
		System.out.println("Triangle : " + cnt);
		return canvas;
	}

	public void fillTriangle() {
		// *** begin triangle filler ***
		// A.y<=B.y<=C.y
		//
		// if (B.y-A.y > 0) dx1=(B.x-A.x)/(B.y-A.y) else dx1=0;
		// if (C.y-A.y > 0) dx2=(C.x-A.x)/(C.y-A.y) else dx2=0;
		// if (C.y-B.y > 0) dx3=(C.x-B.x)/(C.y-B.y) else dx3=0;
		//
		// S=E=A;
		// if(dx1 > dx2) {
		// for(;S.y<=B.y;S.y++,E.y++,S.x+=dx2,E.x+=dx1)
		// horizline(S.x,E.x,S.y,color);
		// E=B;
		// for(;S.y<=C.y;S.y++,E.y++,S.x+=dx2,E.x+=dx3)
		// horizline(S.x,E.x,S.y,color);
		// } else {
		// for(;S.y<=B.y;S.y++,E.y++,S.x+=dx1,E.x+=dx2)
		// horizline(S.x,E.x,S.y,color);
		// S=B;
		// for(;S.y<=C.y;S.y++,E.y++,S.x+=dx3,E.x+=dx2)
		// horizline(S.x,E.x,S.y,color);
		// }
		//
		// *** end triangle filler ***
		while (triangles.hasNext()) {
			Triangle_dt triangle = triangles.next();
			if (triangle.p1() != null && triangle.p2() != null && triangle.p3() != null) {
				List<Point_dt> list = sortPointY(triangle);
				Point a = new Point((int) list.get(0).x(), (int) list.get(0).y());
				Point b = new Point((int) list.get(1).x(), (int) list.get(1).y());
				Point c = new Point((int) list.get(2).x(), (int) list.get(2).y());

				int dx1, dx2, dx3;
				if (b.y() - a.y() > 0)
					dx1 = (b.x() - a.x()) / (b.y() - a.y());
				else
					dx1 = 0;
				if (c.y() - a.y() > 0)
					dx2 = (c.x() - a.x()) / (c.y() - a.y());
				else
					dx2 = 0;
				if (c.y() - b.y() > 0)
					dx3 = (c.x() - b.x()) / (c.y() - b.y());
				else
					dx3 = 0;
				Point s = new Point(a.x(), a.y());
				Point e = new Point(a.x(), a.y());
				if (dx1 > dx2) {
					for (; s.y() <= b.y(); s.setY(s.y() + 1), e.setY(s.y() + 1), s
							.setX(s.x() + dx2), e.setX(e.x() + dx1))
						horizline(s.x(), e.x(), s.y());
					e.setX(b.x());
					e.setY(b.y());
					for (; s.y() <= c.y(); s.setY(s.y() + 1), e.setY(s.y() + 1), s
							.setX(s.x() + dx2), e.setX(e.x() + dx3))
						horizline(s.x(), e.x(), s.y());
				} else {
					for (; s.y() <= b.y(); s.setY(s.y() + 1), e.setY(s.y() + 1), s
							.setX(s.x() + dx1), e.setX(e.x() + dx2))
						horizline(s.x(), e.x(), s.y());
					s.setX(b.x());
					s.setY(b.y());
					for (; s.y() <= c.y(); s.setY(s.y() + 1), e.setY(s.y() + 1), s
							.setX(s.x() + dx3), e.setX(e.x() + dx2))
						horizline(s.x(), e.x(), s.y());
				}

			}
			//break;
		}

	}

	public void horizline(int sx, int ex, int sy) {
		for (int i = 0; i < ex - sx; i++) {
			bi.setRGB(sx + i, sy, Color.cyan.getRGB());			
		}
	}

	public List<Point_dt> sortPointY(Triangle_dt t) {
		List<Point_dt> list = new ArrayList<Point_dt>();
		list.add(t.p1());
		list.add(t.p2());
		list.add(t.p3());
		Collections.sort(list, new NoAscCompare());
		return list;

		// for (Point_dt p : list) {
		// System.out.println(p.y());
		// }
		// System.out.println();
	}

}
