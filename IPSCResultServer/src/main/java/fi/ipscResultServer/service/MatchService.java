package fi.ipscResultServer.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.MatchStatus;
import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.domain.User;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.repository.MatchRepository;
import fi.ipscResultServer.service.resultDataService.MatchResultDataService;
import fi.ipscResultServer.service.resultDataService.StageResultDataService;

@Service
public class MatchService {
	@Autowired
	private MatchRepository matchRepository;
	
	@Autowired
	private MatchResultDataService matchResultDataService;
	
	@Autowired
	private StageResultDataService stageResultDataService;
	
	@Autowired
	private StatisticsService statisticsService;
	
	@Autowired
	private ScoreCardService scoreCardService;
	
	final static Logger logger = Logger.getLogger(MatchService.class);
	
	@Transactional
	public Match save(Match match) throws DatabaseException {
		
		Match oldMatch = matchRepository.findByPractiScoreId(match.getPractiScoreId());
		if (oldMatch != null) delete(oldMatch.getId());
		Match savedMatch = null;
		System.out.println(match == null);
		if (match != null) {
			if (match.getCompetitors() != null) {
				System.out.println("Handling competitors");
				List<Competitor> deletedCompetitors = new ArrayList<Competitor>();
				int competitorNumber = 1;
				
				for (Competitor competitor : match.getCompetitors()) {
					competitor.setMatch(match);
					competitor.setShooterNumber(competitorNumber++);
					// Remove deleted competitors
					if (competitor.isDeleted() == true) {
						deletedCompetitors.add(competitor);
					}
				}
				
				match.getCompetitors().removeAll(deletedCompetitors);
			}
			if (match.getStages() != null) {
				System.out.println("Handling stages");
				List<Stage> deletedStages = new ArrayList<Stage>();
				for (Stage stage : match.getStages()) {
					if (stage.isDeleted() == true) {
						deletedStages.add(stage);
					}
				}
				match.getStages().removeAll(deletedStages);
			}
			System.out.println("Saving match");
			savedMatch = matchRepository.save(match);
			System.out.println("Null " + savedMatch == null);
		}
		
		return savedMatch;
	}
	
	public List<Match> findAll() {
		return matchRepository.findAll();
	}
	
	// Returns Match list with only necessary properties set for listing purposes
	// instead of full instances with a list of Stages etc.
	public List<Match> getFullMatchList() {
		return matchRepository.getFullMatchList();
	}
	public List<Match> getMatchListForUser(User user) {
		return matchRepository.getMatchListForUser(user);
	}
	public Match getOne(Long id) throws DatabaseException {
		return matchRepository.getOne(id);
	}
	
	public Match findByPractiScoreId(String practiScoreId) throws DatabaseException {
		return matchRepository.findByPractiScoreId(practiScoreId);
	}
	
	@Transactional
	public void delete(Long matchId) throws DatabaseException {
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
	@Transactional
	public void setMatchStatus(Long matchId, MatchStatus newStatus) throws DatabaseException {
		Match match = getOne(matchId);
		match.setStatus(newStatus);
	}
}
