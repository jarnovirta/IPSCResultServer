package fi.ipscResultServer.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.controller.api.MatchData;
import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.Constants;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.MatchStatus;
import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.domain.StageScore;
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
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CompetitorService competitorService;
	
	final static Logger logger = Logger.getLogger(MatchService.class);
	
	@Transactional
	public void saveMatchData(MatchData matchData) throws DatabaseException {
		Match match = saveMatchDef(matchData.getMatch());
		
		saveScores(matchData.getMatchScore().getStageScores(), match);
		logger.info("Generating match result listing...");
		matchResultDataService.generateMatchResultListing(match);
		logger.info("Generating statistics...");
		statisticsService.generateCompetitorStatistics(match);
		logger.info("**** MATCH RESULT DATA SAVE DONE");
	}
	@Transactional
	public Match saveMatchDef(Match match) throws DatabaseException {
		logger.info("Saving match def");
		for (Stage stage : match.getStages()) {
			stage.setMatch(match);
		}
		boolean admin = userService.isCurrentUserAdmin();
		
		if (admin == true) match.setUploadedByAdmin(true);
		else match.setUser(userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
				
		Match oldMatch = matchRepository.findByPractiScoreId(match.getPractiScoreId());
		
		if (oldMatch != null) delete(oldMatch.getId());
		
		Match savedMatch = null;
		if (match != null) {
			if (match.getCompetitors() != null) {
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
				List<Stage> removeStages = new ArrayList<Stage>();
				for (Stage stage : match.getStages()) {
					
					if (stage.isDeleted() == true || (stage.getScoreType() != null && stage.getScoreType().equals("Chrono"))) {
						removeStages.add(stage);
					}
				}
				match.getStages().removeAll(removeStages);
			}
			savedMatch = matchRepository.save(match);
			
		}
		return savedMatch;
	}
	
	// Save match result data from PractiScore and generate result listings
	// for new data
	@Transactional
	public void saveScores(List<StageScore> stageScores, Match match) throws DatabaseException{
			logger.info("Saving result data");
			
			if (match.getDivisions() == null) match.setDivisions(new ArrayList<String>());
			
			List<Stage> stagesWithNewResults = new ArrayList<Stage>();
									
			for (StageScore stageScore : stageScores) {
				Stage stage = null;
				for (Stage savedStage : match.getStages()) {
					if (savedStage.getPractiScoreId().equals(stageScore.getStagePractiScoreId())) {
						stage = savedStage;
					}
				}
				
				// Skip results for deleted stages
				if (stage == null || stage.isDeleted() == true) continue;
				
				stagesWithNewResults.add(stage);
							
				if (match.getDivisionsWithResults() == null) {
					match.setDivisionsWithResults(new ArrayList<String>());
				}
				List<String> newIPSCDivisionsWithResults = new ArrayList<String>();
				for (ScoreCard scoreCard : stageScore.getScoreCards()) {
					// Skip scorecards for deleted competitors 
					Competitor competitor = competitorService.findByPractiScoreReferences(match.getPractiScoreId(), scoreCard.getCompetitorPractiScoreId());
					if (competitor == null) continue;
					scoreCard.setCompetitor(competitor);
					
					scoreCard.setHitsAndPoints();
					scoreCard.setStage(stage);
									
					scoreCardService.save(scoreCard);
					
					String scoreCardDivision = scoreCard.getCompetitor().getDivision();

					if (!match.getDivisionsWithResults().contains(scoreCardDivision) && !newIPSCDivisionsWithResults.contains(scoreCardDivision))  {
						newIPSCDivisionsWithResults.add(scoreCardDivision);
					}
				}
				// If match has results for more than one division, add IPSCDivision combined to match for result listing purposes.
				if (match.getDivisionsWithResults().size() + newIPSCDivisionsWithResults.size() > 1 && !match.getDivisionsWithResults().contains(Constants.COMBINED_DIVISION)) {
					match.getDivisionsWithResults().add(Constants.COMBINED_DIVISION);
				}
			}
			if (stagesWithNewResults.size() > 0) {
				for (Stage stageWithNewResults : stagesWithNewResults) {
					logger.info("Generating stage results data for stages with new results...");
					stageResultDataService.generateStageResultsListing(stageWithNewResults);
				}
			}
	}
	public List<Match> findAll() {
		return matchRepository.findAll();
	}
	
	// Returns Match list with only necessary properties set for listing purposes
	// instead of full instances with a list of Stages etc.
	@Transactional
	public List<Match> getFullMatchList() {
		return matchRepository.getFullMatchList();
	}
	@Transactional
	public List<Match> getMatchListForUser(User user) {
		return matchRepository.getMatchListForUser(user);
	}
	public Match getOne(Long id) throws DatabaseException {
		return matchRepository.getOne(id);
	}
	
	public Match findByPractiScoreId(String practiScoreId) throws DatabaseException {
		Match match = matchRepository.findByPractiScoreId(practiScoreId);
		
		return match;
	}
	
	@Transactional
	public void delete(Long matchId) throws DatabaseException {
		Match match = getOne(matchId);
		
		// Delete statistics for match
		statisticsService.deleteByMatch(match);
		
		// Delete stage and match result data
		stageResultDataService.deleteByMatch(match);
		matchResultDataService.deleteByMatch(match);
		
		// Delete score cards
		scoreCardService.deleteByMatch(match);
		
		// Delete match
		matchRepository.delete(match);
	
	}
	@Transactional
	public void setMatchStatus(Long matchId, MatchStatus newStatus) throws DatabaseException {
		Match match = getOne(matchId);
		match.setStatus(newStatus);
	}
}
