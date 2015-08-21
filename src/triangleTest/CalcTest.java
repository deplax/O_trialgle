package triangleTest;

import static org.junit.Assert.*;

import org.junit.Test;

import util.Calc;

public class CalcTest {

	@Test
	public void test() {
		Integer [] arr = {1,2,3,4,5,6,7,8,9,10};
		System.out.println(Calc.stdev(arr, 1));
		System.out.println(Calc.mean(arr));
	}

}
