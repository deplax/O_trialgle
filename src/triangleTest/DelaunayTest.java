package triangleTest;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import delaunay.Delaunay_Triangulation;
import delaunay.Point_dt;
import delaunay.Triangle_dt;

public class DelaunayTest {

	@Test
	public void delaunay() {
		Delaunay_Triangulation dt = new Delaunay_Triangulation();
		
		Point_dt[] points = new Point_dt[4];
		points[0] = new Point_dt(10, 10);
		points[1] = new Point_dt(10, 0);
		points[2] = new Point_dt(0, 10);
		points[3] = new Point_dt(6, 6);
		
		dt.insertPoint(points[0]);
		dt.insertPoint(points[1]);
		dt.insertPoint(points[2]);
		dt.insertPoint(points[3]);
		
		Iterator<Triangle_dt> iter = dt.trianglesIterator();
		int cnt = 0;
		while(iter.hasNext()){
			Triangle_dt triangle = iter.next();
			cnt++;
			
			if(triangle.p1() != null && triangle.p2() != null && triangle.p3() != null){
			Point_dt p1 = triangle.p1();
			Point_dt p2 = triangle.p2();
			Point_dt p3 = triangle.p3();
			
			System.out.println("p1 x = " + p1.x() + ", y = " + p1.y());
			System.out.println("p2 x = " + p2.x() + ", y = " + p2.y());
			System.out.println("p3 x = " + p3.x() + ", y = " + p3.y());
			}
		}
		System.out.println("end : " + cnt);
		
	}

}
