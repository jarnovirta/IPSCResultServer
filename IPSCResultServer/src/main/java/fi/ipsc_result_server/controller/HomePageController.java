package fi.ipsc_result_server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomePageController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getHomePage() {
		return "home";
	}
}
