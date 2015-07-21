package triangle;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

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
			BufferedImage outputImg = null;
			int w = originImg.getWidth();
			int h = originImg.getHeight();
			outputImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_BGR);

			System.out.println("---------------");
			int sample = 0b11111111000000000000000000000000;
			System.out.println(Integer.toBinaryString(sample));
			sample += (92 & 0xff) << 0;
			System.out.println(Integer.toBinaryString(sample));
			sample += (107 & 0xff) << 8;
			System.out.println(Integer.toBinaryString(sample));
			sample += (-93 & 0xff) << 16;
			System.out.println(Integer.toBinaryString(sample));
			System.out.println(sample);
			System.out.println("---------------");

			System.out.println(((DataBufferByte) originImg.getRaster()
					.getDataBuffer()).getData()[0]);
			System.out.println(((DataBufferByte) originImg.getRaster()
					.getDataBuffer()).getData()[1]);
			System.out.println(((DataBufferByte) originImg.getRaster()
					.getDataBuffer()).getData()[2]);
			System.out.println(((DataBufferByte) originImg.getRaster()
					.getDataBuffer()).getData()[3]);
			// byte 00000000 // 1 2 4 8 16 32 64 128
			// 1010 0011
			// 0101 1101

			// A B G R 순서로 배열을 받는다.

			int[] colorBox = originImg.getRGB(0, 0, w, h, null, 0, w);
			int[][] imgPixel = new int[h][w];

			// 이건 차후 함수로 빼자.
			for (int i = 0; i < colorBox.length; i++) {
				imgPixel[i / w][i % w] = colorBox[i];
			}

			for (int i = 0; i < imgPixel.length; i++) {
				for (int j = 0; j < imgPixel[0].length; j++) {
					outputImg.setRGB(j, i, imgPixel[i][j]);
				}
			}
			System.out.println(imgPixel.length);

			System.out.println("color int : " + colorBox[0]);
			Color c = new Color(colorBox[0]);
			System.out.println("R : " + c.getRed());
			System.out.println("G : " + c.getGreen());
			System.out.println("B : " + c.getBlue());
			System.out.println("A : " + c.getAlpha());

			System.out.println("img width : " + originImg.getWidth());
			System.out.println("img height : " + originImg.getHeight());

			int[][] img = controllor.convertToArr(originImg);
			
			
			System.out.println(img[0][100]);
			System.out.println(img.length);
			System.out.println(img[0].length);
			
			
			ImageIO.write(outputImg, "jpg", outputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			fileWriteForGrunt();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 이 함수는 바운드 범위 안의 픽셀 평균을 리턴한다.
	public void blurBound(int[][] img, int x, int y, int bound) {

		int startX = 0;
		int endX = 0;
		int startY = 0;
		int endY = 0;

		if (x - bound < 0)
			startX = Math.abs(x - bound);
		if (x + bound > img.length - 1)
			endX = (x + bound) - (img.length - 1);
		if (y - bound < 0)
			startY = Math.abs(x - bound);
		if (y + bound > img[0].length - 1)
			endY = (y + bound) - (img.length - 1);

		int[] pixelBox = new int[(2 * bound - startX - endX)
				* (2 * bound - startY - endY)];

		for (int h = x - bound + startX, k = 0; h <= x + bound - endX; h++) {
			for (int w = y - bound + startY; w <= y + bound - endY; w++) {
				pixelBox[k] = img[h][w];
				k++;
			}
		}
		// 여기에서 평균을 구한다.
		
	}
	
	public void pixelAverage(int color){
		//color를 r g b로 나눠준다.
		//나눈 r g b 의 각 평균을 낸다. 
		//r g b를 color로 변환한다.
	}
	
	public void colorToRGB(int color){
		int sample = 0b11111111000000000000000000000000;
		System.out.println(Integer.toBinaryString(sample));
		sample += (92 & 0xff) << 0;
		System.out.println(Integer.toBinaryString(sample));
		sample += (107 & 0xff) << 8;
		System.out.println(Integer.toBinaryString(sample));
		sample += (-93 & 0xff) << 16;
		System.out.println(Integer.toBinaryString(sample));
	}
	
	public void RGBToColor(int[] rgb){
		int sample = 0b11111111000000000000000000000000;
		System.out.println(Integer.toBinaryString(sample));
		sample += (rgb[0] & 0xff) << 0;
		System.out.println(Integer.toBinaryString(sample));
		sample += (107 & 0xff) << 8;
		System.out.println(Integer.toBinaryString(sample));
		sample += (-93 & 0xff) << 16;
		System.out.println(Integer.toBinaryString(sample));
	}

	public int[][] convertToArr(BufferedImage image) {

		final byte[] pixels = ((DataBufferByte) image.getRaster()
				.getDataBuffer()).getData();
		final int width = image.getWidth();
		final int height = image.getHeight();
		final boolean hasAlphaChannel = image.getAlphaRaster() != null;
		int[][] result = new int[height][width];
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
		String uuid = UUID.randomUUID().toString();
		BufferedWriter out = new BufferedWriter(new FileWriter("./img/test.js"));
		String str = "var text = '" + uuid + "'\n"
				+ "var node = document.createElement('div');\n"
				+ "node.innerText = text;\n"
				+ "document.querySelector('body').appendChild(node);";
		out.write(str);
		out.close();
	}
}
