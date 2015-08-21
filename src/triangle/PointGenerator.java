package triangle;

import java.util.ArrayList;
import java.util.List;

public class PointGenerator {
	List<Point> points = new ArrayList<Point>();

	public PointGenerator() {
	}

	// 공간분할하여 랜덤찍기
	public PointGenerator(int w, int h, int num, int divide) {
		generate(w, h, num, divide);
	}

	private void generate(int w, int h, int num, int divide) {

		// 공간을 분할한다.
		for (int i = 0; i < divide * divide; i++) {
			int wBlock = w / divide;
			int wMin = (i / divide) * wBlock;
			int wMax = (i / divide) * wBlock + wBlock;

			int hBlock = h / divide;
			int hMin = (i % divide) * hBlock;
			int hMax = (i % divide) * hBlock + hBlock;

			for (int j = 0; j < num / (divide * divide); j++) {
				Point p = new Point(randomNumber(wMin, wMax), randomNumber(
						hMin, hMax));
				points.add(p);
			}
		}
	}

	public int randomNumber(int min, int max) {
		int range = (max - min);
		return (int) (Math.random() * range) + min;
	}

	// 점이 중복되서 득보는 건 없지만 일단 set이 아닌 list로 간다.
	// 빠를 것 같음.
	public List<Point> getPointList() {
		System.out.println("points : " + points.size());
		return points;
	}

	// 원형으로 랜덤찍기
	// 원점과 R, 찍을 점의 수를 받는다.
	// 먼저 2R의 길이만큼 랜덤을 잡는다.
	// x = 루트(r제곱 - y제곱)

	public void generateCircleArea(int x, int y, int r, int num) {

		int cx, t, cy;

		for (int i = 0; i < num; i++) {
			if (i % 2 == 0) {
				cx = randomNumber(-r, +r);
				t = (int) Math.sqrt((double) (r * r - cx * cx));
				cy = randomNumber(-t, +t);
			} else {
				cy = randomNumber(-r, +r);
				t = (int) Math.sqrt((double) (r * r - cy * cy));
				cx = randomNumber(-t, +t);
			}
			Point p = new Point(x + r + cx, y + r + cy);
			points.add(p);
		}

	}
}
