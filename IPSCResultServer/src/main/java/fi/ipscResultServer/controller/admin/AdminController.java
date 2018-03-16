package fi.ipscResultServer.controller.admin;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.ipscResultServer.domain.MatchStatus;
import fi.ipscResultServer.domain.User;
import fi.ipscResultServer.service.MatchService;
import fi.ipscResultServer.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private MatchService matchService;
	
	@Autowired
	private UserService userService;
	
	final static Logger logger = Logger.getLogger(AdminController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String getAdminMainPage(Model model) {
		model.addAttribute("matchList", matchService.getAdminPageFullMatchList());
		model.addAttribute("userList", userService.findEnabledUsers());
		model.addAttribute("matchStatusList", Arrays.asList(MatchStatus.values()));
		return "adminPages/adminMainPage";
	}

	@RequestMapping(value="/addUser", method = RequestMethod.GET)
	public String getAddUserPage(Model model) {
		model.addAttribute("user", new User());
		return "adminPages/editUser";
	}
	@RequestMapping(value="/addUser", method = RequestMethod.POST)
	public String saveNewUser(@ModelAttribute("user") User user, Model model) {
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		user.setRole(User.UserRole.ROLE_REGULAR);
		userService.save(user);
		return getAdminMainPage(model);
	}
	@RequestMapping(value="/deleteUser/{userId}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable("userId") Long userId, Model model) {
		userService.setEnabled(userId, false);
		return getAdminMainPage(model);
	}
}
