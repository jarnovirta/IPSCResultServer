package fi.ipscResultServer.controller.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fi.ipscResultServer.domain.Stage;
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
//			Stage stage = stageService.getOne(stageId);
//			if (division == null) {
//				List<String> availableDivisions = new ArrayList<String>();
//				if (stage.getMatch().getDivisionsWithResults() != null) {
//					for (String divisionWithResults : stage.getMatch().getDivisionsWithResults()) {
//						availableDivisions.add(divisionWithResults);
//					}
//				}
//				if (availableDivisions.size() == 0) {
//					model.addAttribute("stageResultData", null);
//					model.addAttribute("selectedDivision", null);
//					return "results/stageResults";
//				}
//				
//				if (availableDivisions.contains(Constants.COMBINED_DIVISION)) division = Constants.COMBINED_DIVISION;
//				else division = availableDivisions.get(0);
//			}
			Stage stage = stageService.findByPractiScoreId(matchId, stageId);
			model.addAttribute("stageResultData", stageResultDataService.findByStageAndDivision(stage.getId(), division));
			model.addAttribute("selectedDivision", division);
			return "results/stageResults";
		}
		// Exception logged in repository
		catch (DatabaseException e) {
			return "results/stageResults";
		}
	}
}
