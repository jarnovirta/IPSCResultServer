package fi.ipsc_result_server.controller.result;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.ipsc_result_server.domain.IPSCDivision;
import fi.ipsc_result_server.domain.Stage;
import fi.ipsc_result_server.service.StageService;
import fi.ipsc_result_server.service.resultDataService.StageResultDataService;

@Controller
@RequestMapping("/match/{matchId}/stage")
public class StageResultsController {
	@Autowired
	StageResultDataService stageResultDataService;

	@Autowired
	StageService stageService;

	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getStageResultsPage(Model model, @PathVariable("id") String stageId) {
		Stage stage = stageService.getOne(stageId);
		List<IPSCDivision> availableDivisions = new ArrayList<IPSCDivision>();
		
		if (stage.getMatch().getDivisionsWithResults() != null) {
			if (stage.getMatch().getDivisionsWithResults().size() > 1) availableDivisions.add(IPSCDivision.COMBINED);
			for (IPSCDivision division : stage.getMatch().getDivisionsWithResults()) {
				availableDivisions.add(division);
			}
		}
		if (availableDivisions.size() == 0) {
			model.addAttribute("stageResultData", null);
			model.addAttribute("selectedDivision", null);
			return "results/stageResults";
		}
		IPSCDivision division;
		if (availableDivisions.contains(IPSCDivision.COMBINED)) division = IPSCDivision.COMBINED;
		else division = availableDivisions.get(0);
				
		model.addAttribute("stageResultData", stageResultDataService.findByStageAndDivision(stageId, division));
		model.addAttribute("selectedDivision", division);
		return "results/stageResults";
	}
	
	@RequestMapping(value = "/{id}/division/{division}", method = RequestMethod.GET)
	public String getStageResultsPageForDivision(Model model, @PathVariable("id") String stageId, 
			@PathVariable("division") String divisionString) {
		
		IPSCDivision division = IPSCDivision.valueOf(divisionString.toUpperCase());
		model.addAttribute("stageResultData", stageResultDataService.findByStageAndDivision(stageId, division));
		model.addAttribute("selectedDivision", division);
		return "results/stageResults";
	}
	
}
