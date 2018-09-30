package fi.ipscResultServer.controller.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.service.MatchService;

@Controller
@RequestMapping("/matchMainPage")
public class MatchResultsController {
	@Autowired
	private MatchService matchService;

	@RequestMapping(method = RequestMethod.GET)
	public String getMatchMainPage(Model model, 
			@RequestParam("matchId") String matchId,
			@RequestParam(value="live", required = false) Boolean liveResultsView) {
			
		Match match = matchService.getOne(matchService.getIdByPractiScoreId(matchId), true);
		model.addAttribute("match", match);
					
		return "results/matchResultsMainPage";
	}
}
