package fi.ipscResultServer.controller.matchAnalysis;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/matchAnalysis")
public class MatchAnalysisController {
	@RequestMapping(method = RequestMethod.GET)
	public String getMatchAnalysisPage(Model model) {
		return "matchAnalysis/matchAnalysisPage";
	}
}
