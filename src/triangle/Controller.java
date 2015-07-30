package triangle;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Controller {
	public static void main(String[] args) {

		Controller controllor = new Controller();

		String originPath = "./img/test.jpg";
		String outputPath = "./img/test2.jpg";

		File originFile = new File(originPath);
		File outputFile = new File(outputPath);

		try {
			BufferedImage originImg = ImageIO.read(originFile);

			// 버퍼 이미지에서 바이트 배열로 변환
			// 블러
			// 바운더리 필
			// 점 위치 들 구하고
			// 트라이엥귤레이션
			// 바이트 배열에서 버퍼 이미지로 변환

			// 이미지 리딩에서 파일
			// BufferedImage -> byte[] ->
			// 이 상테에서 가공이 가능해야 함.

			final byte[] pixels = ((DataBufferByte) originImg.getRaster()
					.getDataBuffer()).getData();

			// ------------------------------------------------
			
			long startTime = System.currentTimeMillis();
			BufferedImage outputImg = controllor.blur(originImg, 10);
			long endTime = System.currentTimeMillis();
			System.out.println(endTime - startTime + " ms");

			long startTime2 = System.currentTimeMillis();
			byte[] test = controllor.asdf(pixels, originImg.getWidth(), originImg.getHeight(), 10);
			BufferedImage outputImg2 = controllor.byteToImg(test, originImg.getWidth(), originImg.getHeight());
			long endTime2 = System.currentTimeMillis();
			System.out.println(endTime2 - startTime2 + " ms");
			
			System.out.println("A : " + outputImg.getRGB(0, 0));
			System.out.println("B : " + outputImg2.getRGB(0, 0));

			ImageIO.write(outputImg2, "jpg", outputFile);

		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			fileWriteForGrunt();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// blur
	public BufferedImage blur(BufferedImage inputImage, int bound) {
		int[][] inputImageArr = convertToArr(inputImage);
		int w = inputImage.getWidth();
		int h = inputImage.getHeight();
		BufferedImage outputImg = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_BGR);
		for (int i = 0; i < inputImageArr.length; i++) {
			for (int j = 0; j < inputImageArr[0].length; j++) {
				outputImg.setRGB(j, i, blurBound(inputImageArr, i, j, bound));
			}
		}
		return outputImg;
	}

	public byte[] asdf(byte inputImgArr[], int w, int h, int bound) {
		// 먼저 효과를 적용할 배열을 생성
		byte[] outputImgArr = new byte[inputImgArr.length];

		// 이제 바운더리 계산해야 함.
		// x, y 를 주면 r g b를 변수에 대입한다.

		int x, y = 0;

		for (int i = 0; i < inputImgArr.length; i = i + 3) {
			x = (i / 3) / w;
			y = (i / 3) % w;

			int startX = 0;
			int endX = 0;
			int startY = 0;
			int endY = 0;

			if (x - bound < 0)
				startX = bound - x;
			if (x + bound > h - 1)
				endX = (x + bound) - (h - 1);
			if (y - bound < 0)
				startY = bound - y;
			if (y + bound > w - 1)
				endY = (y + bound) - (w - 1);

			int avgRed = 0;
			int avgGreen = 0;
			int avgBlue = 0;

			// 바운드의 안전범위를 구했으니 각 포인트를 순회하며 평균을 낸다.
			for (int ih = x - bound + startX; ih <= x + bound - endX; ih++) {
				for (int iw = y - bound + startY; iw <= y + bound - endY; iw++) {
					avgBlue += inputImgArr[coordinate(ih, iw, w)] & 0b11111111;
					avgGreen += inputImgArr[coordinate(ih, iw, w) + 1] & 0b11111111;
					avgRed += inputImgArr[coordinate(ih, iw, w) + 2] & 0b11111111;
				}
			}

			int cnt = (2 * bound - startX - endX + 1)
					* (2 * bound - startY - endY + 1);
			avgBlue /= cnt;
			avgGreen /= cnt;
			avgRed /= cnt;

			// 구한 평균 값을 배열에 넣는다.
			outputImgArr[coordinate(x, y, w)] = (byte) (avgBlue & 0b11111111);
			outputImgArr[coordinate(x, y, w) + 1] = (byte) (avgGreen & 0b11111111);
			outputImgArr[coordinate(x, y, w) + 2] = (byte) (avgRed & 0b11111111);
		}

		return outputImgArr;
	}

	// x y 를 넣으면 일차원 바이트 배열의 위치를 리턴
	public int coordinate(int x, int y, int w) {
		return x * (w * 3) + (y * 3);
	}

	public BufferedImage byteToImg(byte[] pixels, int w, int h) {
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		for (int r = 0; r < h; r++)
			for (int c = 0; c < w; c++) {
				int idx = coordinate(r, c, w);
				int pixel = 0b11111111000000000000000000000000;
				pixel += (pixels[idx + 2] & 0xff) << 16; // R
				pixel += (pixels[idx + 1] & 0xff) << 8; // G
				pixel += (pixels[idx + 0] & 0xff) << 0; // B
				img.setRGB(c, r, pixel);
			}
		return img;
	}

	// 이 함수는 바운드 범위 안의 픽셀 평균을 리턴한다.
	public int blurBound(int[][] img, int x, int y, int bound) {

		int startX = 0;
		int endX = 0;
		int startY = 0;
		int endY = 0;

		if (x - bound < 0)
			startX = Math.abs(x - bound);
		if (x + bound > img.length - 1)
			endX = (x + bound) - (img.length - 1);
		if (y - bound < 0)
			startY = Math.abs(y - bound);
		if (y + bound > img[0].length - 1)
			endY = (y + bound) - (img[0].length - 1);

		int[] pixels = new int[(2 * bound - startX - endX + 1)
				* (2 * bound - startY - endY + 1)];

		for (int h = x - bound + startX, k = 0; h <= x + bound - endX; h++) {
			for (int w = y - bound + startY; w <= y + bound - endY; w++) {
				pixels[k] = img[h][w];
				k++;
			}
		}
		return pixelAverage(pixels);
	}

	public int pixelAverage(int[] pixels) {
		int[] rgbAvg = new int[3];
		for (int i = 0; i < pixels.length; i++) {
			// color를 r g b로 나눠준다.
			int[] rgb = colorToRGB(pixels[i]);
			rgbAvg[0] += rgb[0];
			rgbAvg[1] += rgb[1];
			rgbAvg[2] += rgb[2];
		}

		// 나눈 r g b 의 각 평균을 낸다.
		for (int i = 0; i < rgbAvg.length; i++) {
			rgbAvg[i] /= pixels.length;
		}

		// r g b를 color로 변환한다.
		return RGBToColor(rgbAvg);
	}

	public int[] colorToRGB(int color) {
		int[] rgb = new int[3];
		rgb[0] += (0b00000000111111110000000000000000 & color) >> 16; // R
		rgb[1] += (0b00000000000000001111111100000000 & color) >> 8; // G
		rgb[2] += (0b00000000000000000000000011111111 & color) >> 0; // B
		return rgb;
	}

	public int RGBToColor(int[] rgb) {
		int pixel = 0b11111111000000000000000000000000;
		pixel += (rgb[0] & 0xff) << 16; // R
		pixel += (rgb[1] & 0xff) << 8; // G
		pixel += (rgb[2] & 0xff) << 0; // B
		return pixel;
	}

	public int[][] convertToArr(BufferedImage image) {
		final byte[] pixels = ((DataBufferByte) image.getRaster()
				.getDataBuffer()).getData();
		final int width = image.getWidth();
		final int height = image.getHeight();
		final boolean hasAlphaChannel = image.getAlphaRaster() != null;
		int[][] result = new int[height][width];
		// 알파채널이 있을 경우에 대한 처리.
		if (hasAlphaChannel) {
			final int pixelLength = 4;
			for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
				int argb = 0;
				argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
				argb += ((int) pixels[pixel + 1] & 0xff); // blue
				argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
				argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
				result[row][col] = argb;
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
		} else {
			final int pixelLength = 3;
			for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
				int argb = 0;
				argb += -16777216; // 255 alpha
				argb += ((int) pixels[pixel] & 0xff); // blue
				argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
				argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
				result[row][col] = argb;
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
		}

		return result;
	}

	// 이거 static 아니도록 수정해야 함.
	public static void fileWriteForGrunt() throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter("./img/test.js"));
		String str = "var text = '" + " " + "'\n"
				+ "var node = document.createElement('div');\n"
				+ "node.innerText = text;\n"
				+ "document.querySelector('body').appendChild(node);";
		out.write(str);
		out.close();
	}
}
