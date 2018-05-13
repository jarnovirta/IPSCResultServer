package fi.ipscResultServer.controller.api;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.MatchScore;
import fi.ipscResultServer.domain.MatchStatus;
import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.service.MatchService;
import fi.ipscResultServer.service.UserService;
import fi.ipscResultServer.service.practiScoreDataService.MatchScoreService;

@Controller
@RequestMapping("/api")
public class ApiController {
	@Autowired
	private MatchScoreService matchScoreService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MatchService matchService;

	final static Logger logger = Logger.getLogger(ApiController.class);
	
	@RequestMapping(value = "/matches", method = RequestMethod.POST)
	public ResponseEntity<String> postMatchData(@RequestBody Match match) {
		logger.info("POST request to /matches " + match.getPractiScoreId());
		boolean admin = userService.isCurrentUserAdmin();
		try {
			Match dbMatch = matchService.findByPractiScoreId(match.getPractiScoreId());
			if (dbMatch != null && dbMatch.getStatus() != MatchStatus.SCORING) {
				return new ResponseEntity<String>("Scoring not open for match", null, HttpStatus.BAD_REQUEST);
			}
		
			if (admin == true) {
				match.setUploadedByAdmin(true);
			}
			else {
				match.setUser(userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
			}
			
			for (Stage stage : match.getStages()) {
				stage.setMatch(match);
			}
			
				matchService.save(match);
				logger.info("Responding with ok from api/matches");
				return new ResponseEntity<String>("Match data saved", null, HttpStatus.OK);
				
		}
		catch (DatabaseException e) {
			logger.info("Responding to API request with status code 500 - Internal Server Error");
			return new ResponseEntity<String>("Error occurred while saving data", null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/scores", method = RequestMethod.POST)
	public ResponseEntity<String> postScoreData(@RequestBody MatchScore matchScore) {
		logger.info("POST request to //scores");
		try {
			Match dbMatch = matchService.findByPractiScoreId(matchScore.getMatchId());
			if (dbMatch != null && dbMatch.getStatus() != MatchStatus.SCORING) {
				return new ResponseEntity<String>("Scoring not open for match", null, HttpStatus.BAD_REQUEST);
			}
			matchScoreService.save(matchScore);
			logger.info("Responding with ok from api/scores");
			return new ResponseEntity<String>("Result data saved", null, HttpStatus.OK);
		}
		catch (DatabaseException e) {
			logger.info("Responding to API request with status code 500 - Internal Server Error");
			return new ResponseEntity<String>("Error occurred while saving data", null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value = "testConnection", method = RequestMethod.GET)
	public ResponseEntity<String> testConnection() {
		logger.info("GET request to /testConnection");
		return new ResponseEntity<String>("Connection ok!", null, HttpStatus.OK);
		
	}
}
