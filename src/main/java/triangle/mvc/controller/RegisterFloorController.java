package triangle.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import triangle.mvc.dao.UserDao;
import triangle.mvc.model.Message;
import triangle.mvc.model.User;

@Controller
public class RegisterFloorController {
	private static final Logger log = LoggerFactory.getLogger(RegisterFloorController.class);
	
	@Autowired
	UserDao userDao;
	
	@RequestMapping(value = "/registerFloor", method = RequestMethod.GET)
	public String fileModifyFloor() {
		log.debug("welcome FrontFloor!");
		return "registerFloor";
	}
	
	@RequestMapping(value = "/registerFloor/signup", method = RequestMethod.POST)
	public @ResponseBody Message login(@RequestBody User user) {
		log.debug(user.toString());
		if(userDao.isExistEmail(user))
			return new Message("This email already exists.");
		userDao.signup(user);
		return new Message("Success");
	}
}
