package triangle.mvc.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import triangle.mvc.model.Canvas;

@Controller
public class ModifyController {

	private static final Logger log = LoggerFactory.getLogger(ModifyController.class);

	@Autowired
	private ServletContext servletContext;

	@RequestMapping(value = "/modifyFloor", method = RequestMethod.GET)
	public String ModifyFloor(HttpSession session) {
		log.debug("enter modifyFloor page get");
		Canvas c = (Canvas) session.getAttribute("canvas");
		log.debug(c.getBufferedImage().toString());
		return "modifyFloor";
	}
}