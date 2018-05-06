package fi.ipscResultServer.controller.result;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.ipscResultServer.domain.Constants;
import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.service.StageService;
import fi.ipscResultServer.service.resultDataService.StageResultDataService;

@Controller
@RequestMapping("/match/{practiScoreMatchId}/stage")
public class StageResultsController {
	@Autowired
	StageResultDataService stageResultDataService;

	@Autowired
	StageService stageService;

	
	@RequestMapping(value = "/{practiScoreStageId}", method = RequestMethod.GET)
	public String getStageResultsPage(Model model, @PathVariable("practiScoreMatchId") String practiScoreMatchId, 
			@PathVariable("practiScoreStageId") String practiScoreStageId) {
		try {
			Stage stage = stageService.findByPractiScoreId(practiScoreMatchId, practiScoreStageId);
			List<String> availableDivisions = new ArrayList<String>();
			if (stage.getMatch().getDivisionsWithResults() != null) {
				for (String division : stage.getMatch().getDivisionsWithResults()) {
					availableDivisions.add(division);
				}
			}
			if (availableDivisions.size() == 0) {
				model.addAttribute("stageResultData", null);
				model.addAttribute("selectedDivision", null);
				return "results/stageResults";
			}
			String division;
			if (availableDivisions.contains(Constants.COMBINED_DIVISION)) division = Constants.COMBINED_DIVISION;
			else division = availableDivisions.get(0);
				
			model.addAttribute("stageResultData", stageResultDataService.findByStageAndDivision(stage.getId(), division));
			model.addAttribute("selectedDivision", division);
			return "results/stageResults";
		}
		// Exception logged in repository
		catch (DatabaseException e) {
			return "results/stageResults";
		}
	}
	
	@RequestMapping(value = "/{practiScoreStageId}/division/{division}", method = RequestMethod.GET)
	public String getStageResultsPageForDivision(Model model, @PathVariable("practiScoreMatchId") String practiScoreMatchId,
			@PathVariable("practiScoreStageId") String practiScoreStageId, @PathVariable("division") String division) {
		try {
			Stage stage =  stageService.findByPractiScoreId(practiScoreMatchId, practiScoreStageId);
			model.addAttribute("stageResultData", 
					stageResultDataService.findByStageAndDivision(stage.getId(), division));
			model.addAttribute("selectedDivision", division);
			return "results/stageResults";
		}
		// Exception logged in repository
		catch (DatabaseException e) {
			return "results/stageResults";
		}
	}
	
}
