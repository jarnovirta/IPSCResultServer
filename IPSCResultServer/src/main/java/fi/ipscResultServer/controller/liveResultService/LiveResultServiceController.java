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
import fi.ipscResultServer.domain.Constants;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.domain.resultData.StageResultData;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.service.MatchService;
import fi.ipscResultServer.service.StageService;
import fi.ipscResultServer.service.resultDataService.StageResultDataService;

@Controller
@RequestMapping("/live")
public class LiveResultServiceController {
	
	@Autowired
	private MatchService matchService;
	
	@Autowired
	StageService stageService;
	
	@Autowired
	StageResultDataService stageResultDataService;
	
	
	// TODO: Ei voi olla instanssimuuttujana, muuttuu muuten aina kaikille,
	// pit‰‰ olla session attribute
	private LiveResultServiceConfig liveResultServiceConfig;
		
	@RequestMapping(method = RequestMethod.GET)
	public String getLiveResultServiceSetupPage(Model model) {
		model.addAttribute("matches", matchService.findAll());
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
		
			// Pass configuration info to JavaScript code (rows per page in result table and page change interval)
			model.addAttribute("config", liveResultServiceConfig);
			
			Match match = matchService.getOne(matchService.getIdByPractiScoreId(matchId));
			
			// If no result data exists, set empty data instance and show empty result table
			if (match == null || match.getDivisionsWithResults() == null || match.getDivisionsWithResults().size() == 0) {
				model.addAttribute("stageResultData", getEmptyStageResultData(match));
				return "results/liveResultService/liveResultsPage";
			}
			
			List<String> divisionsWithResults = match.getDivisionsWithResults();
			
			// Determine next stage and division to show in live result service view
			divisionsWithResults.remove(Constants.COMBINED_DIVISION);
			String nextStagePractiScoreId;
			String nextDivision = previousDivision;
			
			// Handle start of live result service, with no previously shown result data
			if (previousStagePractiScoreId == null) {
				nextStagePractiScoreId  = match.getStages().get(0).getPractiScoreId();
				nextDivision = divisionsWithResults.get(0);
			}
			// Handle result data request with previously shown result data
			else {
				nextStagePractiScoreId = getNextStagePractiScoreId(match, previousStagePractiScoreId);
				if (nextStagePractiScoreId == null) {
					nextStagePractiScoreId = match.getStages().get(0).getPractiScoreId();
					nextDivision = getNextDivision(match, divisionsWithResults, previousDivision);
				}
			}
			
//			Stage nextStage = stageService.findByPractiScoreId(match.getPractiScoreId(), nextStagePractiScoreId);
//			
//			StageResultData stageResultData = stageResultDataService.getStageResultListing(match.getPractiScoreId(), nextStage.getPractiScoreId(), nextDivision);
//			
//			// Handle stages with no result data by sending an empty StageResultData instance with match, stage and division
//			// information for use in JSP.
//			if (stageResultData == null) {
//				model.addAttribute("stageResultData", getEmptyStageResultData(nextStage, nextDivision));
//			}
//			else {
//				model.addAttribute("stageResultData", stageResultData);
//			}
			return "results/liveResultService/liveResultsPage";
		
	}

	private StageResultData getEmptyStageResultData(Match match) {
		StageResultData data = new StageResultData();
		Stage stage = new Stage();
		stage.setMatch(match);
		data.setStage(stage);
		return data;
	}
	private StageResultData getEmptyStageResultData(Stage stage, String division) {
		StageResultData data = new StageResultData();
		data.setStage(stage);
		data.setDivision(division);
		return data;
	}
	private String getNextDivision(Match match, List<String> divisions, String previousDivision) {
		int divisionIndex = divisions.indexOf(previousDivision);
		if (divisionIndex + 1 == divisions.size()) return divisions.get(0);
		else return divisions.get(divisionIndex + 1);
	}
	private String getNextStagePractiScoreId(Match match, String previousStagePractiScoreId) {
		int stageIndex = 0;
		for (Stage stage : match.getStages()) {
			if (stage.getPractiScoreId().equals(previousStagePractiScoreId)) break;
			stageIndex++;
		}
		if (stageIndex + 1 == match.getStages().size()) return null;
		return match.getStages().get(stageIndex + 1).getPractiScoreId();
	}
}
