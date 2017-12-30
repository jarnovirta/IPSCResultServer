package fi.ipsc_result_server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.ipsc_result_server.domain.MatchScore;
import fi.ipsc_result_server.service.MatchScoreService;

@Controller
public class ResultUploaderApiController {
	@Autowired
	private MatchScoreService matchScoreService;

	@RequestMapping(value = "/api/matches", method = RequestMethod.POST)
	public @ResponseBody MatchScore postMatch(@RequestBody MatchScore matchScore) {
		System.out.println("POST new match");
		return matchScoreService.save(matchScore);
	}
}
