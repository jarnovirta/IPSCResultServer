package fi.ipscResultServer.controller.api;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
			long startTime = System.currentTimeMillis();
			
			matchService.saveMatchData(matchData);
			long estimatedTime = System.currentTimeMillis() - startTime;
			System.out.println("\n\n **** TOTAL ELAPSED TIME: " + estimatedTime / 1000 + " SEC");
			
			return new ResponseEntity<String>("Match data saved!", null, HttpStatus.OK);
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			return new ResponseEntity<String>("Error occurred on server!", null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "testConnection", method = RequestMethod.GET)
	public ResponseEntity<String> testConnection() {
		return new ResponseEntity<String>("Connection ok!", null, HttpStatus.OK);
		
	}
}
