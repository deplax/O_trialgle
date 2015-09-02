package util;

import java.util.Comparator;
import triangle.delaunay.Point_dt;

public class NoAscCompare implements Comparator<Point_dt> {

	@Override
	public int compare(Point_dt o1, Point_dt o2) {
		// TODO Auto-generated method stub
		return o1.y() < o2.y() ? -1 : o1.y() > o2.y() ? 1 : 0;
	}
}