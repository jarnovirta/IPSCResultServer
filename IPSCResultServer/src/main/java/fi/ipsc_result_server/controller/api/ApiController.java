package fi.ipsc_result_server.controller.api;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.ipsc_result_server.domain.Match;
import fi.ipsc_result_server.domain.MatchScore;
import fi.ipsc_result_server.domain.Stage;
import fi.ipsc_result_server.service.MatchScoreService;
import fi.ipsc_result_server.service.MatchService;

@Controller
@RequestMapping("/api")
public class ApiController {
	@Autowired
	private MatchScoreService matchScoreService;
	
	@Autowired
	private MatchService matchService;

	@RequestMapping(value = "/matches", method = RequestMethod.POST)
	public @ResponseBody Match postMatchData(@RequestBody Match match) {
		System.out.println("POST new match data" + Calendar.getInstance().getTime());
		for (Stage stage : match.getStages()) {
			stage.setMatch(match);
		}
		return matchService.save(match);
	}
	
	// TODO: fix return value, set status
	@RequestMapping(value = "/matches/matchId/scores", method = RequestMethod.POST)
	public @ResponseBody MatchScore postScoreData(@RequestBody MatchScore matchScore) {
		System.out.println("POST new score data " + Calendar.getInstance().getTime());
		matchScoreService.save(matchScore);
		return null;
	}
}
