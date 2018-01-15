package fi.ipsc_result_server.controller.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.ipsc_result_server.domain.IPSCDivision;
import fi.ipsc_result_server.service.ResultDataService;

@Controller
@RequestMapping("/match/{matchId}/stage")
public class StageResultsController {
	@Autowired
	ResultDataService resultDataService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getStageResultsPage(Model model, @PathVariable("id") String stageId) {
		IPSCDivision division = IPSCDivision.COMBINED;
		model.addAttribute("stageResultData", resultDataService.findResultDataForStage(stageId, division));
		model.addAttribute("selectedDivision", division);
		return "results/stageResults";
	}
	
	@RequestMapping(value = "/{id}/division/{division}", method = RequestMethod.GET)
	public String getStageResultsPageForDivision(Model model, @PathVariable("id") String stageId, 
			@PathVariable("division") String divisionString) {
		IPSCDivision division = IPSCDivision.valueOf(divisionString.toUpperCase());
		model.addAttribute("stageResultData", resultDataService.findResultDataForStage(stageId, division));
		model.addAttribute("selectedDivision", division);
		return "results/stageResults";
	}
	
}
