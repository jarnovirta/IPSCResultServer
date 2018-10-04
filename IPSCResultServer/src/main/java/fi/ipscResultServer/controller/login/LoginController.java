package fi.ipscResultServer.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String getLoginPage(Model model, @RequestParam(value="login_error", required=false) 
		Boolean loginFailed) {
		if (loginFailed != null && loginFailed == true) model.addAttribute("loginError", true);
		return "login";
	}
}
