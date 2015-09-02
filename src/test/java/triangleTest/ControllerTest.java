//package triangleTest;
//
//import static org.junit.Assert.*;
//
//import org.junit.Test;
//
//import triangle.Controller;
//
//public class ControllerTest {
//	Controller c = new Controller();
//
//	@Test
//	public void testColorToRGB() {
//		int color = -6067364;
//		int[] result = c.colorToRGB(color);
//		assertEquals(result[0], 163);
//		assertEquals(result[1], 107);
//		assertEquals(result[2], 92);
//	}
//
//	@Test
//	public void testRGBToColor() throws Exception {
//		int[] rgb = new int[3];
//		rgb[0] = 163;
//		rgb[1] = 107;
//		rgb[2] = 92;
//		int result = c.RGBToColor(rgb);
//		assertEquals(result, -6067364);
//	}
//
//	@Test
//	public void testValue() {
//		int color = 6188453;
//		int[] result = c.colorToRGB(color);
//
//		System.out.println(result[0]);
//		System.out.println(result[1]);
//		System.out.println(result[2]);
//
//		assertEquals(result[0], 163);
//		assertEquals(result[1], 107);
//		assertEquals(result[2], 92);
//	}
//
//	@Test
//	public void binaryConvert() {
//
//		int getrgbValue = -10588763;
//		int makeValue = -5935778;
//
//		System.out.println(Integer.toBinaryString(getrgbValue));
//		System.out.println(Integer.toBinaryString(makeValue));
//
//	}
//
//}
