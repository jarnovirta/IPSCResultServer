package fi.ipscResultServer.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.repository.MatchRepository;
import fi.ipscResultServer.service.resultDataService.MatchResultDataService;
import fi.ipscResultServer.service.resultDataService.StageResultDataService;

@Service
public class MatchService {
	@Autowired
	private MatchRepository matchRepository;
	
	@Autowired
	MatchResultDataService matchResultDataService;
	
	@Autowired
	StageResultDataService stageResultDataService;
	
	@Autowired
	StatisticsService statisticsService;
	
	@Autowired
	ScoreCardService scoreCardService;
	
	final static Logger logger = Logger.getLogger(MatchService.class);
	
	@Transactional
	public Match save(Match match) {
		if (match.getCompetitors() != null) {
			int competitorNumber = 1;
			for (Competitor competitor : match.getCompetitors()) {
				competitor.setShooterNumber(competitorNumber++);
			}
		}
		return matchRepository.save(match);
	}
	
	public List<Match> findAll() {
		return matchRepository.findAll();
	}
	// Returns Match list with only necessary properties set for listing purposes
	// instead of full instances with a list of Stages etc.
	public List<Match> getAdminPageMatchList() {
		return matchRepository.getAdminPageMatchList();
	}
	public Match getOne(String id) {
		return matchRepository.getOne(id);
	}
	
	@Transactional
	public void delete(String matchId) {
		Match match = getOne(matchId);
		
		logger.info("Deleting statistics");
		// Delete statistics for match
		statisticsService.deleteByMatch(match);
		
		// Delete stage and match result data
		logger.info("Deleting stage result data");
		stageResultDataService.deleteByMatch(match);
		logger.info("Deleting match result data");
		matchResultDataService.deleteByMatch(match);
		
		// Delete score cards
		logger.info("Deleting score cards");
		scoreCardService.deleteByMatch(match);
		
		// Delete match
		logger.info("Deleting match");
		matchRepository.delete(match);
		logger.info("Match delete done!");
	}
}
