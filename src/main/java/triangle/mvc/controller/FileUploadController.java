package triangle.mvc.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import triangle.mvc.model.Canvas;


@Controller
@SessionAttributes("canvas")
public class FileUploadController {

	private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);

	@Autowired
	private ServletContext servletContext;

	@RequestMapping(value = "/fileControlFloor", method = RequestMethod.GET)
	public String fileControlFloor() {
		log.debug("enter fileControlFloor page get");
		return "fileUpload";
	}
	
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public String getFile(@RequestParam(value ="imageString") String imageString, Model model) {
		log.debug("enter fileUpload page post");
		BufferedImage image = decodeToImage(imageString);
		
		Canvas canvas = new Canvas();
		canvas.setBufferedImage(image);
		model.addAttribute("canvas", canvas);
		
		return "redirect:/modifyFloor";
	}

	public BufferedImage decodeToImage(String imageString) {
		String imageDataBytes = imageString.substring(imageString.indexOf(",") + 1);
		BufferedImage image = null;
	    byte[] imageByte = Base64.decodeBase64(imageDataBytes);
	    try {
	        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
	        image = ImageIO.read(bis);
	        bis.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return image;
	}
	
	public void saveFile(String filename, byte[] imgByte ){
		String directory = servletContext.getRealPath("/") + "img/" + filename+".jpg";
		try {
			new FileOutputStream(directory).write(imgByte);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}