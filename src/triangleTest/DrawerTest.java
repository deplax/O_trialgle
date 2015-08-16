package triangleTest;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

import triangle.Controller;
import triangle.Drawer;
import triangle.Point;

public class DrawerTest {

	@Test
	public void triangleFillTest() throws IOException {
		String out01 = "./img/test2.jpg";
		String out02 = "./img/test3.jpg";

		File outFile01 = new File(out01);
		File outFile02 = new File(out02);

		BufferedImage bi = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
		Drawer drawer = new Drawer(bi);
		Point v1 = new Point(100, 100);
		Point v2 = new Point(200, 200);
		Point v3 = new Point(150, 300);
		Point v4 = new Point(
				(int) (v1.x() + ((float) (v2.y() - v1.y()) / (float) (v3.y() - v1.y()))
						* (v3.x() - v1.x())), v2.y());
		System.out.println("x4 : " + v4.x() + ", y4 : " + v4.y());
		drawer.fillBottomFlatTriangle(v1, v4, v2);
		drawer.fillTopFlatTriangle(v4, v2, v3);
		
		ImageIO.write(bi, "png", outFile01);

		BufferedImage bi2 = new BufferedImage(800, 800, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bi2.createGraphics();
		g.setColor(Color.BLACK);
		g.drawOval(v1.x() - 1, v1.y() - 1, 2, 2);
		g.drawOval(v2.x() - 1, v2.y() - 1, 2, 2);
		g.drawOval(v3.x() - 1, v3.y() - 1, 2, 2);
		g.drawOval(v4.x() - 1, v4.y() - 1, 2, 2);

		
		ImageIO.write(bi2, "png", outFile02);
		Controller.fileWriteForGrunt();
	}

}
