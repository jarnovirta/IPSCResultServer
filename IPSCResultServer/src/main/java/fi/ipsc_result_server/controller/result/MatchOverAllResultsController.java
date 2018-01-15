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
@RequestMapping("/match/{matchId}")
public class MatchOverAllResultsController {
	@Autowired
	ResultDataService resultDataService;
	
	@RequestMapping(value="/division/{division}", method = RequestMethod.GET)
	public String getCombinedResultsPage(Model model, @PathVariable("matchId") String matchId, 
			@PathVariable("division") String divisionString) {
		IPSCDivision division = IPSCDivision.valueOf(divisionString.toUpperCase());
		model.addAttribute("matchResultData", resultDataService.findResultDataForMatch(matchId));
		model.addAttribute("selectedDivision", division);
		return "results/matchOverAllResults";
	}
}
