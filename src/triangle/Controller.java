package triangle;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Controller {
	public static void main(String[] args) {

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
			
			System.out.println(((DataBufferByte)originImg.getRaster().getDataBuffer()).getData()[0]);

			int[] colorBox = originImg.getRGB(0, 0, w, h, null, 0, w);
			
			for (int i = 0; i < colorBox.length; i++) {
				
				outputImg.setRGB(i % w, i / w, colorBox[i]);
			}
			
			System.out.println(colorBox[0]);
			
			Color c = new Color(colorBox[0]);
			System.out.println(c.getRed());
			System.out.println(c.getGreen());
			System.out.println(c.getBlue());
			System.out.println(c.getAlpha());
			
			// avgBox를 만든다.
			// 벽인지 판단하고 벽이라면 0을 넣어준다.
			
			System.out.println("img width : " + originImg.getWidth());
			System.out.println("img height : " + originImg.getHeight());
			
			
			
			ImageIO.write(outputImg, "jpg", outputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setAverageBox(int[] arr, int x, int y, int w, int h){
		int[][] box = new int[3][3];
		
		// [-1-w] [-w] [+1-w]
		// [-1]   [ ]  [+1]
		// [-1+w] [+w] [+1+w]
		
		if(x == 0){
			// -1, +1, +w-1, +w, +w+1
			for (int i = 0; i < box.length; i++) {
				box[i][0] = 0;
			}
		}
		if(x == (w - 1)){
			for (int i = 0; i < box.length; i++) {
				box[i][2] = 0;
			}
		}
		if(y == 0){
			for (int i = 0; i < box.length; i++) {
				box[0][i] = 0;
			}
		}
		if(y == (h - 1))
		{
			for (int i = 0; i < box.length; i++) {
				box[2][i] = 0;
			}
		}
	}
	
	public void ninePointAverage(int[] arr, int x, int y, int w, int h){
		int[][] box = new int[3][3];
		
		for (int i = x - w; i < box.length; i++) {
			for (int j = x - w; j < box.length; j+=w) {
				
			}
		}
		//벽인지 확인한다.
		//벽이면 그 벽에 해당하는 배열은 더하지 않는다.
		//벽이 아니라면 모든 포인트를 다 더한다.
	}
}
