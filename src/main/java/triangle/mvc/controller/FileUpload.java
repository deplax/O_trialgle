package triangle.mvc.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FileUpload {

	private static final Logger log = LoggerFactory.getLogger(FileUpload.class);

	@Autowired
	private ServletContext servletContext;

	@RequestMapping(value = "/file", method = RequestMethod.POST)
	public String updatePos(@RequestParam("key") String str) {
		log.debug("enter file upload page post");
		log.debug(str);
		// System.out.println(file.getName());
		// System.out.println(file.getOriginalFilename());
		// System.out.println(file.getContentType());
		// System.out.println(file.getSize());
		imgProcess(str);
		return "canvasTest";

	}

	@RequestMapping(value = "/file", method = RequestMethod.GET)
	public String updatePos2() {
		log.debug("enter file upload page get");
		return "canvasTest";
	}

	public void imgProcess(String imageValue) {
		String imageDataBytes = imageValue
				.substring(imageValue.indexOf(",") + 1);
		byte[] imageByte = Base64.decodeBase64(imageDataBytes);

		String directory = servletContext.getRealPath("/") + "img/sample.jpg";

		try {
			new FileOutputStream(directory).write(imageByte);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
