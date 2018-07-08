package fi.ipscResultServer.controller.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.domain.resultData.StageResultData;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.service.StageService;
import fi.ipscResultServer.service.resultDataService.StageResultDataService;

@Controller
@RequestMapping("/stageResults")
public class StageResultsController {
	@Autowired
	StageResultDataService stageResultDataService;

	@Autowired
	StageService stageService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String getStageResultsPage(Model model, @RequestParam("matchId") String matchId, 
			@RequestParam("stageId") String stageId, 
			@RequestParam("division") String division) {
		try {
			
			Stage stage = stageService.findByPractiScoreId(matchId, stageId);
			StageResultData resultData = stageResultDataService.findByStageAndDivision(stage.getId(), division);
			if (resultData == null) {
				resultData = new StageResultData();
				resultData.setStage(stage);
				resultData.setDivision(division);
			}
			model.addAttribute("stageResultData", resultData);
			return "results/stageResults/stageResultsPage";
		}
		// Exception logged in repository
		catch (DatabaseException e) {
			return "results/stageResults/stageResultsPage";
		}
	}
}
