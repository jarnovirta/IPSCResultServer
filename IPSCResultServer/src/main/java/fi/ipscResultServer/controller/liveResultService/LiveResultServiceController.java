package fi.ipscResultServer.controller.liveResultService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fi.ipscResultServer.controller.data.LiveResultServiceConfig;
import fi.ipscResultServer.controller.result.StageResultsController;
import fi.ipscResultServer.domain.Constants;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.service.MatchService;

@Controller
@RequestMapping("/live")
public class LiveResultServiceController {
	
	@Autowired
	StageResultsController stageResultsController;
	
	@Autowired
	private MatchService matchService;
	
	private LiveResultServiceConfig liveResultServiceConfig;
		
	@RequestMapping(method = RequestMethod.GET)
	public String getLiveResultServiceSetupPage(Model model) {
		model.addAttribute("matches", matchService.getFullMatchList());
		model.addAttribute("liveResultServiceConfig", new LiveResultServiceConfig());
		return "results/liveResultService/liveResultServiceSetup";
	}
	@RequestMapping(method = RequestMethod.POST)
	public String processLiveResultServicePage(Model model, 
			@ModelAttribute("liveResultServiceConfig") LiveResultServiceConfig config) {
		
		liveResultServiceConfig = config; 
		
		return "redirect:/live/nextPage?matchId=" + config.getMatchPractiScoreId();
	}		
			
	@RequestMapping(value="/nextPage", method = RequestMethod.GET)
	public String getNextPage(Model model, 
			@RequestParam("matchId") String matchId,
			@RequestParam(value = "previousStagePractiScoreId", required = false) String previousStagePractiScoreId,
			@RequestParam(value = "previousDivision", required = false) String previousDivision) {
		
		try {
			
			// Determine next stage and division to show in live result service view
			Match match = matchService.findByPractiScoreId(matchId);
			List<String> divisions = match.getDivisionsWithResults();
			divisions.remove(Constants.COMBINED_DIVISION);
			String nextStagePractiScoreId = previousStagePractiScoreId;
			String nextDivision;
			if (previousDivision == null) {
				nextStagePractiScoreId = match.getStages().get(0).getPractiScoreId();
				nextDivision = divisions.get(0);
			}
			else {
				nextDivision = getNextDivision(match, divisions, previousDivision);
				if (nextDivision == null) {
					nextStagePractiScoreId = getNextStagePractiScoreId(match, previousStagePractiScoreId);
					nextDivision = divisions.get(0);
				}
			}
			stageResultsController.getStageResultsPage(model, matchId, nextStagePractiScoreId, nextDivision);
			
			model.addAttribute("config", liveResultServiceConfig);
			return "results/liveResultService/liveResultsPage";
		}
		// Exception logged in repository
		catch (DatabaseException e) {
			return "results/matchResultsMainPage";
		}
	}
	private String getNextDivision(Match match, List<String> divisions, String previousDivision) {
		int divisionIndex = divisions.indexOf(previousDivision);
		if (divisionIndex + 1 == divisions.size()) return null;
		else return divisions.get(divisionIndex + 1);
	}
	private String getNextStagePractiScoreId(Match match, String previousStagePractiScoreId) {
		int stageIndex = 0;
		for (Stage stage : match.getStages()) {
			if (stage.getPractiScoreId().equals(previousStagePractiScoreId)) break;
			stageIndex++;
		}
		if (match.getStages().size() == stageIndex + 1) return match.getStages().get(0).getPractiScoreId();
		return match.getStages().get(stageIndex + 1).getPractiScoreId();
	}
}
