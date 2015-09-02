package triangle;
//package triangle;
//
//import java.awt.image.BufferedImage;
//import java.awt.image.DataBufferByte;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//
//import javax.imageio.ImageIO;
//
//public class Controller {
//	public static void main(String[] args) {
//
//		Controller controllor = new Controller();
//
//		String originPath = "./img/test.jpg";
//		String outputPath = "./img/test2.jpg";
//
//		File originFile = new File(originPath);
//		File outputFile = new File(outputPath);
//
//		try {
//			BufferedImage originImg = ImageIO.read(originFile);
//			final byte[] pixels = ((DataBufferByte) originImg.getRaster()
//					.getDataBuffer()).getData();
//
//			// 버퍼 이미지에서 바이트 배열로 변환
//			// 블러
//			// 바운더리 필
//			// 점 위치 들 구하고
//			// 트라이엥귤레이션
//			// 바이트 배열에서 버퍼 이미지로 변환
//
//			// 이미지 리딩에서 파일
//			// BufferedImage -> byte[] ->
//			// 이 상테에서 가공이 가능해야 함.
//
//			// ------------------------------------------------
//
//			long startTime = System.currentTimeMillis();
//
//			int width = originImg.getWidth();
//			int height = originImg.getHeight();
//
//			byte[] outputTemp = controllor.blur(pixels, width, height, 10);
//			
//			controllor.boundaryFill(outputTemp, width, height, 2);
//			
//			BufferedImage outputImg = controllor.byteToImg(outputTemp, width,
//					height);
//
//			long endTime = System.currentTimeMillis();
//			System.out.println(endTime - startTime + " ms");
//
//			ImageIO.write(outputImg, "jpg", outputFile);
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		try {
//			fileWriteForGrunt();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public byte[] blur(byte inputImgArr[], int w, int h, int bound) {
//
//		// 먼저 효과를 적용할 배열을 생성
//		byte[] outputImgArr = new byte[inputImgArr.length];
//
//		// 이제 바운더리 계산해야 함.
//		// x, y 를 주면 r g b를 변수에 대입한다.
//		int x, y = 0;
//		for (int i = 0; i < inputImgArr.length; i = i + 3) {
//			x = (i / 3) / w;
//			y = (i / 3) % w;
//
//			int startX = 0;
//			int endX = 0;
//			int startY = 0;
//			int endY = 0;
//
//			if (x - bound < 0)
//				startX = bound - x;
//			if (x + bound > h - 1)
//				endX = (x + bound) - (h - 1);
//			if (y - bound < 0)
//				startY = bound - y;
//			if (y + bound > w - 1)
//				endY = (y + bound) - (w - 1);
//
//			int avgRed = 0;
//			int avgGreen = 0;
//			int avgBlue = 0;
//
//			// 바운드의 안전범위를 구했으니 각 포인트를 순회하며 평균을 낸다.
//			for (int ih = x - bound + startX; ih <= x + bound - endX; ih++) {
//				for (int iw = y - bound + startY; iw <= y + bound - endY; iw++) {
//					avgBlue += inputImgArr[coordinate(ih, iw, w)] & 0b11111111;
//					avgGreen += inputImgArr[coordinate(ih, iw, w) + 1] & 0b11111111;
//					avgRed += inputImgArr[coordinate(ih, iw, w) + 2] & 0b11111111;
//				}
//			}
//
//			int cnt = (2 * bound - startX - endX + 1)
//					* (2 * bound - startY - endY + 1);
//			avgBlue /= cnt;
//			avgGreen /= cnt;
//			avgRed /= cnt;
//
//			// 구한 평균 값을 배열에 넣는다.
//			outputImgArr[coordinate(x, y, w)] = (byte) (avgBlue & 0b11111111);
//			outputImgArr[coordinate(x, y, w) + 1] = (byte) (avgGreen & 0b11111111);
//			outputImgArr[coordinate(x, y, w) + 2] = (byte) (avgRed & 0b11111111);
//		}
//
//		return outputImgArr;
//	}
//
//	public void boundaryFill(byte[] inputImgArr, int w, int h, int threshold) {
//		// 검사한 블럭인지 확인할 마커배열을 생성.
//		boolean[][] checked = new boolean[h][w];
//
//		// 한 픽셀을 잡고 주변 탐색
//		// 임계값 이내일 경우 같은 색으로 간주
//		// 같은 색일 경우 같은 영역임을 표기
//
//		int x, y = 0;
//		// for (int i = 0; i < inputImgArr.length; i = i + 3) {
//		for (int i = 0; i < 3; i = i + 3) {
//			x = (i / 3) / w;
//			y = (i / 3) % w;
//
//			int b = inputImgArr[coordinate(x, y, w)];
//			int g = inputImgArr[coordinate(x, y, w) + 1];
//			int r = inputImgArr[coordinate(x, y, w) + 2];
//			fill(inputImgArr, checked, r, g, b, w, h, x, y, threshold);
//
//		}
//
//	}
//
//	// 인자가 많지만 속도를 위해 좌표를 통으로 넘기지 않고 r g b를 따로 넘긴다.
//	public void fill(byte[] inputImgArr, boolean[][] checked, int r, int g,
//			int b, int w, int h, int x, int y, int threshold) {
//		// 체크된 픽셀이 아니라면
//		if (checked[x][y] == false) {
//			int curb = inputImgArr[coordinate(x, y, w)];
//			int curg = inputImgArr[coordinate(x, y, w) + 1];
//			int curr = inputImgArr[coordinate(x, y, w) + 2];
//
//			// 비슷비슷한가?
//			if (checkSimilar(curr, curg, curb, r, g, b, threshold)) {
//				// 체크한다.
//				checked[x][y] = true;
//
//				// 색상을 채운다.
//				inputImgArr[coordinate(x, y, w)] = (byte) 0b11111111;
//				inputImgArr[coordinate(x, y, w) + 1] = (byte) 0b11111111;
//				inputImgArr[coordinate(x, y, w) + 2] = (byte) 0b11111111;
//
//				// 좌표를 배열에 저장.
//
//				// 이어서 탐색
//				if (y > 0)
//					fill(inputImgArr, checked, curr, curg, curb, w, h, x,
//							y - 1, threshold);
//				if (y < w - 1)
//					fill(inputImgArr, checked, curr, curg, curb, w, h, x,
//							y + 1, threshold);
//				if (x > 0)
//					fill(inputImgArr, checked, curr, curg, curb, w, h, x - 1,
//							y, threshold);
//				if (x < h - 1)
//					fill(inputImgArr, checked, curr, curg, curb, w, h, x + 1,
//							y, threshold);
//			}
//
//		}
//
//	}
//
//	public boolean checkSimilar(int curr, int curg, int curb, int r, int g,
//			int b, int threshold) {
//		if (curr < r + threshold && curr > r - threshold
//				&& curg < g + threshold && curg > g - threshold
//				&& curb < b + threshold && curb > b - threshold)
//			return true;
//		return false;
//	}
//
//	// x y 를 넣으면 일차원 바이트 배열의 위치를 리턴
//	public int coordinate(int x, int y, int w) {
//		return x * (w * 3) + (y * 3);
//	}
//
//	public BufferedImage byteToImg(byte[] pixels, int w, int h) {
//		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
//		for (int r = 0; r < h; r++)
//			for (int c = 0; c < w; c++) {
//				int idx = coordinate(r, c, w);
//				int pixel = 0b11111111000000000000000000000000;
//				pixel += (pixels[idx + 2] & 0xff) << 16; // R
//				pixel += (pixels[idx + 1] & 0xff) << 8; // G
//				pixel += (pixels[idx + 0] & 0xff) << 0; // B
//				img.setRGB(c, r, pixel);
//			}
//		return img;
//	}
//
//	public int[] colorToRGB(int color) {
//		int[] rgb = new int[3];
//		rgb[0] += (0b00000000111111110000000000000000 & color) >> 16; // R
//		rgb[1] += (0b00000000000000001111111100000000 & color) >> 8; // G
//		rgb[2] += (0b00000000000000000000000011111111 & color) >> 0; // B
//		return rgb;
//	}
//
//	public int RGBToColor(int[] rgb) {
//		int pixel = 0b11111111000000000000000000000000;
//		pixel += (rgb[0] & 0xff) << 16; // R
//		pixel += (rgb[1] & 0xff) << 8; // G
//		pixel += (rgb[2] & 0xff) << 0; // B
//		return pixel;
//	}
//
//	public int[][] convertToArr(BufferedImage image) {
//		final byte[] pixels = ((DataBufferByte) image.getRaster()
//				.getDataBuffer()).getData();
//		final int width = image.getWidth();
//		final int height = image.getHeight();
//		final boolean hasAlphaChannel = image.getAlphaRaster() != null;
//		int[][] result = new int[height][width];
//		// 알파채널이 있을 경우에 대한 처리.
//		if (hasAlphaChannel) {
//			final int pixelLength = 4;
//			for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
//				int argb = 0;
//				argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
//				argb += ((int) pixels[pixel + 1] & 0xff); // blue
//				argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
//				argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
//				result[row][col] = argb;
//				col++;
//				if (col == width) {
//					col = 0;
//					row++;
//				}
//			}
//		} else {
//			final int pixelLength = 3;
//			for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
//				int argb = 0;
//				argb += -16777216; // 255 alpha
//				argb += ((int) pixels[pixel] & 0xff); // blue
//				argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
//				argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
//				result[row][col] = argb;
//				col++;
//				if (col == width) {
//					col = 0;
//					row++;
//				}
//			}
//		}
//
//		return result;
//	}
//
//	// 이거 static 아니도록 수정해야 함.
//	public static void fileWriteForGrunt() throws IOException {
//		BufferedWriter out = new BufferedWriter(new FileWriter("./img/test.js"));
//		String str = "var text = '" + " " + "'\n"
//				+ "var node = document.createElement('div');\n"
//				+ "node.innerText = text;\n"
//				+ "document.querySelector('body').appendChild(node);";
//		out.write(str);
//		out.close();
//	}
//}
