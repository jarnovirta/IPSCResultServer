package fi.ipsc_result_server.controller.api;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.ipsc_result_server.domain.MatchScore;
import fi.ipsc_result_server.service.MatchScoreService;

@Controller
@RequestMapping("/api")
public class ApiController {
	@Autowired
	private MatchScoreService matchScoreService;

	@RequestMapping(value = "/matches", method = RequestMethod.POST)
	public @ResponseBody MatchScore postMatch(@RequestBody MatchScore matchScore) {
		System.out.println("POST new match " + Calendar.getInstance().getTime());
		return matchScoreService.save(matchScore);
	}
}
