package fi.ipscResultServer.controller.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.domain.resultData.StageResultData;
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

		Stage stage = stageService.getOne(stageService.getIdByPractiScoreReference(matchId, stageId));
			
		StageResultData resultData = stageResultDataService.getStageResultListing(stage, division);
		if (resultData == null) {
			resultData = new StageResultData(stage, division);
		}
		model.addAttribute("stageResultData", resultData);
		return "results/stageResults/stageResultsPage";
	}
}
