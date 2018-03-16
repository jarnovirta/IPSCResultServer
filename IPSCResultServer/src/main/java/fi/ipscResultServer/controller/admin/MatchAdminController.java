package fi.ipscResultServer.controller.admin;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.ipscResultServer.domain.MatchStatus;
import fi.ipscResultServer.domain.User;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.service.MatchService;
import fi.ipscResultServer.service.UserService;

@Controller
@RequestMapping("/matchAdmin")
public class MatchAdminController {
	
	@Autowired
	private MatchService matchService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getMatchAdminMainPage(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUserName = authentication.getName();
		User user = userService.findByUsername(currentUserName);
		model.addAttribute("matchList", matchService.getAdminPageMatchListByUser(user));
		model.addAttribute("matchStatusList", Arrays.asList(MatchStatus.values()));
		return "adminPages/adminMainPage";
	}
	
	@RequestMapping(value="/match/{matchId}/setStatus/{status}", method = RequestMethod.GET)
	public String setMatchStatus(@PathVariable("matchId") String matchId, 
			@PathVariable("status") MatchStatus status, Model model) {
		model.addAttribute("matchList", matchService.getAdminPageFullMatchList());
		model.addAttribute("matchStatusList", Arrays.asList(MatchStatus.values()));
		return "admin/adminMainPage";
	}
	
	@RequestMapping(value="/deleteMatch/{matchId}", method = RequestMethod.GET)
	public String deleteMatch(@PathVariable("matchId") String matchId, Model model) {
		try {
			matchService.delete(matchId);
			
			model.addAttribute("matchList", matchService.getAdminPageFullMatchList());
			model.addAttribute("matchStatusList", Arrays.asList(MatchStatus.values()));
			return "adminPages/adminMainPage";
		}
		catch (DatabaseException e) {
			return "adminPages/adminMainPage";
		}
	}
}
