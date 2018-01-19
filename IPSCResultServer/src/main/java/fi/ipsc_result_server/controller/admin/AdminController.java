package fi.ipsc_result_server.controller.admin;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.ipsc_result_server.domain.MatchStatus;
import fi.ipsc_result_server.service.MatchService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	MatchService matchService;
	
	final static Logger logger = Logger.getLogger(AdminController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String getAdminMainPage(Model model) {
		model.addAttribute("matchList", matchService.getAdminPageMatchList());
		model.addAttribute("matchStatusList", Arrays.asList(MatchStatus.values()));
		return "admin/adminMainPage";
	}
	@RequestMapping(value="/match/{matchId}/setStatus/{status}", method = RequestMethod.GET)
	public String setMatchStatus(@PathVariable("matchId") String matchId, 
			@PathVariable("status") MatchStatus status, Model model) {
		logger.info("Set status to " + status + " for match " + matchId);
		
		model.addAttribute("matchList", matchService.getAdminPageMatchList());
		model.addAttribute("matchStatusList", Arrays.asList(MatchStatus.values()));
		return "admin/adminMainPage";
	}
	
	@RequestMapping(value="/deleteMatch/{matchId}", method = RequestMethod.GET)
	public String deleteMatch(@PathVariable("matchId") String matchId, Model model) {
		logger.info("Delete match " + matchId);
		
		matchService.delete(matchId);
		
		model.addAttribute("matchList", matchService.getAdminPageMatchList());
		model.addAttribute("matchStatusList", Arrays.asList(MatchStatus.values()));
		return "admin/adminMainPage";
	}

}
