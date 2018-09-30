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

import fi.ipscResultServer.domain.practiScore.PractiScoreMatchData;
import fi.ipscResultServer.service.PractiScoreDataService;


@Controller
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	private PractiScoreDataService practiScoreMatchDataService;
	
	private final static Logger LOGGER = Logger.getLogger(ApiController.class);
	
	@RequestMapping(value = "/matches", method = RequestMethod.POST)
	public ResponseEntity<String> postMatchData(@RequestBody PractiScoreMatchData matchData) {
		try {
			long startTime = System.currentTimeMillis();
			LOGGER.info("Saving match data for " + matchData.getMatch().getName());
			practiScoreMatchDataService.save(matchData);
			long estimatedTime = System.currentTimeMillis() - startTime;
			LOGGER.info("Match data saved in " + estimatedTime / 1000 + " sec.");
			
			return new ResponseEntity<String>("Match data saved!", null, HttpStatus.OK);
		}
		catch (Exception e) {
			LOGGER.error(ExceptionUtils.getStackTrace(e));
			return new ResponseEntity<String>("Error occurred on server!", null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "testConnection", method = RequestMethod.GET)
	public ResponseEntity<String> testConnection() {
		return new ResponseEntity<String>("Connection ok!", null, HttpStatus.OK);
		
	}
}
