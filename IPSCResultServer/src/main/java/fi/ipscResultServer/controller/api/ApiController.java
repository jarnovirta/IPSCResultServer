package fi.ipscResultServer.controller.api;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.MatchStatus;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.service.MatchService;


@Controller
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	private MatchService matchService;

	final static Logger logger = Logger.getLogger(ApiController.class);
	
	@RequestMapping(value = "/matches", method = RequestMethod.POST)
	public ResponseEntity<String> postMatchData(@RequestBody MatchData matchData) {
		try {
			// Save match definition data
			Match dbMatch = matchService.findByPractiScoreId(matchData.getMatch().getPractiScoreId());
			if (dbMatch != null && dbMatch.getStatus() != MatchStatus.SCORING) {
				return new ResponseEntity<String>("Scoring not open for match", null, HttpStatus.BAD_REQUEST);
				
			}
			matchService.saveMatchData(matchData);
			
			return new ResponseEntity<String>("Match data saved", null, HttpStatus.OK);
		}
		catch (DatabaseException e) {
			return new ResponseEntity<String>("Error occurred while saving data", null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "testConnection", method = RequestMethod.GET)
	public ResponseEntity<String> testConnection() {
		return new ResponseEntity<String>("Connection ok!", null, HttpStatus.OK);
		
	}
}
