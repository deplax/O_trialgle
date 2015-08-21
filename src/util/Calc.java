package util;

public class Calc {

	public static double stdev(Integer[] arr, int option) {
		if (arr.length < 2)
			return Double.NaN;

		double sum = 0.0;
		double sd = 0.0;
		double diff;
		double meanValue = mean(arr);

		for (int i = 0; i < arr.length; i++) {
			diff = arr[i] - meanValue;
			sum += diff * diff;
		}
		sd = Math.sqrt(sum / (arr.length - option));

		return sd;
	}

	public static double mean(Integer[] arr) {
		double sum = 0.0;
		for (int i = 0; i < arr.length; i++)
			sum += arr[i];
		return sum / arr.length;
	}
}
