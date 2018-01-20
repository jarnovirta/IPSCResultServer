package fi.ipscResultServer.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.MatchScore;
import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.service.MatchService;
import fi.ipscResultServer.service.practiScoreDataService.MatchScoreService;

@Controller
@RequestMapping("/api")
public class ApiController {
	@Autowired
	private MatchScoreService matchScoreService;
	
	@Autowired
	private MatchService matchService;

	@RequestMapping(value = "/matches", method = RequestMethod.POST)
	public @ResponseBody Match postMatchData(@RequestBody Match match) {
		for (Stage stage : match.getStages()) {
			stage.setMatch(match);
		}
		return matchService.save(match);
	}
	
	// TODO: fix return value, set status
	@RequestMapping(value = "/matches/matchId/scores", method = RequestMethod.POST)
	public @ResponseBody MatchScore postScoreData(@RequestBody MatchScore matchScore) {
		matchScoreService.save(matchScore);
		return null;
	}
}
