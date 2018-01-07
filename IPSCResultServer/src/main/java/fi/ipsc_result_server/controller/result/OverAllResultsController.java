package fi.ipsc_result_server.controller.result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fi.ipsc_result_server.service.MatchService;

@Controller
@RequestMapping("/results/overall")
public class OverAllResultsController {
	@Autowired
	MatchService matchService;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String getOverallResultsPage() {
		return "results/overallResults";
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String TESTgetOverallResultsForReport(Model model, @RequestParam("match") String matchId) {
		model.addAttribute("match", matchService.getOne(matchId));
		return "results/overallResults";
	}
}
