package fi.ipsc_result_server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.ipsc_result_server.domain.MatchScore;

@Controller
public class ResultUploaderApiController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String testMethod() {
		System.out.println("testmethod");
		return "test";
	}
	@RequestMapping(value = "/api/matches", method = RequestMethod.POST)
	public @ResponseBody MatchScore putMatch(@RequestBody MatchScore matchScore) {
		System.out.println("PUT new match");
		// TODO: save match;
		
		return matchScore;
	}
}
