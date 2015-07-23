package triangleTest;

import static org.junit.Assert.*;

import org.junit.Test;

import triangle.Controller;

public class ControllerTest {
	Controller c = new Controller();
	
	@Test
	public void testColorToRGB(){	
		int color = -6067364;
		int[] result = c.colorToRGB(color);
		assertEquals(result[0], 163);
		assertEquals(result[1], 107);
		assertEquals(result[2], 92);
	}
	
	@Test
	public void testRGBToColor() throws Exception {
		int[] rgb = new int[3];
		rgb[0] = 163;
		rgb[1] = 107;
		rgb[2] = 92;
		int result = c.RGBToColor(rgb);
		assertEquals(result, -6067364);
	}

}
