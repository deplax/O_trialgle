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

	// ## 회원가입을 하지 않는 유저. ## 
	// 클라에서 사진을 선택
	// 선택한 사진을 클라에서 리사이즈
	// 리사이즈 한 사진을 서버로 전송
	// 서버에서 이미지를 인식하여 눈코입이 얼마만큼 잡히는지 확인
	// 결과만 클라에 전송.
	// 클라에서 convert 요청
	// 서버에서 점을 추출.
	// 추출된 점을 클라에 전송.
	
	
	// 컨트롤러에서 리사이즈 이미지를 가져온다.
	// 리사이즈 이미지에서 점을 추출한다.
	// 추출한 점을 gson 형식으로 만든다.
	
	

	@RequestMapping(value = "/file", method = RequestMethod.POST)
	public String updatePos(@RequestParam("key") String str) {
		log.debug("enter file upload page post");
		log.debug(str);
		// System.out.println(file.getName());
		// System.out.println(file.getOriginalFilename());
		// System.out.println(file.getContentType());
		// System.out.println(file.getSize());
		
		
		return "canvasTest";

	}

	@RequestMapping(value = "/file", method = RequestMethod.GET)
	public String updatePos2() {
		log.debug("enter file upload page get");
		return "canvasTest";
	}

	public byte[] imgStringToByteArr(String imgString) {
		String imageDataBytes = imgString.substring(imgString.indexOf(",") + 1);
		return Base64.decodeBase64(imageDataBytes);
	}
	
	public void savefile(String filename, byte[] imgByte ){
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
