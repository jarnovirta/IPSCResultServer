package fi.ipsc_result_server.controller.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.ipsc_result_server.service.ResultDataService;

@Controller
@RequestMapping("/match/{matchId}/stage")
public class StageResultsController {
	@Autowired
	ResultDataService resultDataService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getStageResultsPage(Model model, @PathVariable("id") String stageId) {
		model.addAttribute("stageResultData", resultDataService.findResultDataForStage(stageId));
		return "results/stageResults";
	}
}
