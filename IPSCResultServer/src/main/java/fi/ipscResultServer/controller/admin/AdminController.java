package fi.ipscResultServer.controller.admin;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.MatchStatus;
import fi.ipscResultServer.domain.User;
import fi.ipscResultServer.service.MatchService;
import fi.ipscResultServer.service.StatisticsService;
import fi.ipscResultServer.service.UserService;
import fi.ipscResultServer.service.resultDataService.MatchResultDataService;

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
		
	final static Logger logger = Logger.getLogger(AdminController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String getAdminMainPage(Model model) {
		if (userService.isCurrentUserAdmin()) {
			model.addAttribute("matchList", matchService.getFullMatchList());
			model.addAttribute("userList", userService.findEnabledUsers());
		}
		else {
			User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
			model.addAttribute("matchList", matchService.getMatchListForUser(user));
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
		userService.save(user);
		return "redirect:/admin";
	}
	@RequestMapping(value="/deleteUser", method = RequestMethod.POST)
	public String deleteUser(@RequestParam("userId") Long userId, Model model) {
		userService.setEnabled(userId, false);
		return "redirect:/admin";
	}
	
	@RequestMapping(value="/setMatchStatus", method = RequestMethod.POST)
	public String setMatchStatus(@RequestParam("matchId") Long matchId, 
			@RequestParam("status") MatchStatus status, Model model) {
		try {
			matchService.setMatchStatus(matchId, status);
			Match match = matchService.getOne(matchId);
			if (status == MatchStatus.SCORING_ENDED) {
				logger.info("Generating match result listing...");
				matchResultDataService.deleteByMatch(matchId);				
				matchResultDataService.generateMatchResultListing(match);
				logger.info("Generating statistics...");
				statisticsService.deleteByMatch(matchId);
				statisticsService.generateCompetitorStatistics(match);
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "redirect:/admin";
	}
	
	@RequestMapping(value="/deleteMatch", method = RequestMethod.POST)
	public String deleteMatch(@RequestParam("matchId") Long matchId, Model model) {
		
		long startTime = System.currentTimeMillis();
//		matchService.delete(matchId);
		
		long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println("\n\n **** DELETE MATCH TOOK " + estimatedTime / 1000 + " SEC");
		
		model.addAttribute("matchList", matchService.getFullMatchList());
		model.addAttribute("matchStatusList", Arrays.asList(MatchStatus.values()));

		return "redirect:/admin";
	}
}
