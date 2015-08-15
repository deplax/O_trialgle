package triangle;

import java.util.ArrayList;
import java.util.List;

public class PointGenerator {
	List<Point> points = new ArrayList<Point>();

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
				Point p = new Point(randomNumber(wMin, wMax), randomNumber(hMin, hMax));
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
	

	// 가로 세로를 받아 랜덤으로 점을 찍는다.
	// 찍은 점을 리스트에 넣는다.

	// 버퍼이미지 -> 바이트배열 생성.
	// 바이트 배열 접근 함수 제작.

	// 점을 그리는 함수 제작.
}
