package triangleTest;

import static org.junit.Assert.*;

import org.junit.Test;

public class PointGeneratorTest {

	@Test
	public void randomTest() {
		int min = 5;
		int max = 10;
		int range = (max - min);

		for (int i = 0; i < 100; i++) {
			int num = (int) (Math.random() * range) + min;
			if (!(num >= 5 && num < 10)) {
				System.out.println(num);
				fail("out of random value");
			}
		}
	}
}
