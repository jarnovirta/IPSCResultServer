package fi.ipscResultServer.controller.admin;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import fi.ipscResultServer.domain.MatchStatus;
import fi.ipscResultServer.domain.User;
import fi.ipscResultServer.service.MatchResultDataService;
import fi.ipscResultServer.service.MatchService;
import fi.ipscResultServer.service.StatisticsService;
import fi.ipscResultServer.service.UserService;

@Controller
@RequestMapping("/admin")
@SessionAttributes("user")
public class AdminController {
	@Autowired
	private MatchService matchService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MatchResultDataService matchResultDataService;
	
	@Autowired
	private StatisticsService statisticsService;
	
	private final static Logger LOGGER = Logger.getLogger(AdminController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String getAdminMainPage(Model model) {
		if (userService.isCurrentUserAdmin()) {
			model.addAttribute("matchList", matchService.findAll());
			model.addAttribute("userList", userService.findEnabledUsers());
		}
		else {
			User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
			model.addAttribute("matchList", matchService.findByUser(user.getId()));
		}
		model.addAttribute("matchStatusList", Arrays.asList(MatchStatus.values()));
		return "adminPages/adminMainPage";
	}
	
	@RequestMapping(value="/editUser", method = RequestMethod.GET)
	public String getAddUserPage(@RequestParam(value="userId", required = false) Long userId, Model model) {
		User user = new User();
		if (userId != null) {
			user = userService.getOne(userId);
			user.setPassword(null);
			
		}
		model.addAttribute("user", user);
		return "adminPages/editUser";
	}
	@RequestMapping(value="/editUser", method = RequestMethod.POST)
	public String saveNewUser(@ModelAttribute("user") User user, Model model) {
		if ((user.getPassword() == null || user.getPassword().length() < 1) && user.getId() != null) {
			User existingUser = userService.getOne(user.getId());
			user.setPassword(existingUser.getPassword());
		}
		else user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		user.setRole(User.UserRole.ROLE_USER);
		userService.saveOrUpdate(user);
		return "redirect:/admin";
	}
	@RequestMapping(value="/deleteUser", method = RequestMethod.POST)
	public ResponseEntity<String> deleteUser(@RequestParam("userId") Long userId, Model model) {
		userService.setEnabled(userId, false);
		return new ResponseEntity<String>("User deleted", null, HttpStatus.OK);
	}
	
	@RequestMapping(value="/setMatchStatus", method = RequestMethod.POST)
	public String setMatchStatus(@RequestParam("matchId") Long matchId, 
			@RequestParam("status") MatchStatus status, Model model) {
		try {
			matchService.setStatus(matchId, status);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin";
	}
	
	@RequestMapping(value="/deleteMatch", method = RequestMethod.POST)
	public ResponseEntity<String> deleteMatch(@RequestParam("matchId") Long matchId, Model model) {
		matchService.delete(matchId);
		return new ResponseEntity<String>("Match deleted!", null, HttpStatus.OK);
	}
}
