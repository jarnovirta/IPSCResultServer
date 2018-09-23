package fi.ipscResultServer.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

@Service
public class MatchService {
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	private MatchRepository matchRepository;
	
	@Autowired
	private MatchResultDataService matchResultDataService;
	
	@Autowired
	private StatisticsService statisticsService;
	
	@Autowired
	private ScoreCardService scoreCardService;
	
	@Autowired
	private UserService userService;
	
	final static Logger logger = Logger.getLogger(MatchService.class);

	@Transactional
	public void saveMatchData(MatchData matchData) throws DatabaseException {
		
		// Delete previously saved match data from DB
		
		logger.info("*** START MATCH DATA SAVE *** \n Delete old match data");
		Match oldMatch = matchRepository.findByPractiScoreId(matchData.getMatch().getPractiScoreId());
		
		if (oldMatch != null) delete(oldMatch.getId());
		
		prepareMatchDataForSave(matchData.getMatch());
		
		Match match = saveMatchDef(matchData.getMatch());
				
		prepareStageScoresForSave(matchData.getMatchScore().getStageScores(), match);
		
		scoreCardService.save(getMatchScoreCards(matchData));
				
		logger.info("**** MATCH RESULT DATA SAVE DONE");
	}
	
	private List<ScoreCard> getMatchScoreCards(MatchData matchData) {
		List<ScoreCard> scoreCards = new ArrayList<ScoreCard>();
		for (StageScore ss : matchData.getMatchScore().getStageScores()) {
			scoreCards.addAll(ss.getScoreCards());
		}
	return scoreCards;
	}
	public void prepareMatchDataForSave(Match match) {
		
		// Prepare stage data
		if (match.getStages() != null) {
			List<Stage> removeStages = new ArrayList<Stage>();
			for (Stage stage : match.getStages()) {
				stage.setMatch(match);
				if (stage.isDeleted() == true || (stage.getScoreType() != null && stage.getScoreType().equals("Chrono"))) {
					removeStages.add(stage);
				}
			}
			match.getStages().removeAll(removeStages);
		}
		// Set user reference to match
		boolean admin = userService.isCurrentUserAdmin();
		
		if (admin == true) match.setUploadedByAdmin(true);
		else match.setUser(userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
		
		// Prepare competitor data
		if (match.getCompetitors() != null) {
			List<Competitor> deletedCompetitors = new ArrayList<Competitor>();
			int competitorNumber = 1;
			
			for (Competitor competitor : match.getCompetitors()) {
				competitor.setShooterNumber(competitorNumber++);
				competitor.setMatch(match);
				// Remove deleted competitors
				if (competitor.isDeleted() == true) {
					deletedCompetitors.add(competitor);
				}
			}
			match.getCompetitors().removeAll(deletedCompetitors);
		}
	}
	
	private void prepareStageScoresForSave(List<StageScore> stageScores, Match match) {
		
		if (match.getDivisions() == null) match.setDivisions(new ArrayList<String>());
		if (match.getDivisionsWithResults() == null) match.setDivisionsWithResults(new ArrayList<String>());
		
		for (StageScore stageScore : stageScores) {
			Stage stage = null;
			for (Stage savedStage : match.getStages()) {
				if (savedStage.getPractiScoreId().equals(stageScore.getStagePractiScoreId())) {
					stage = savedStage;
				}
			}
						
			List<ScoreCard> cardsToRemove = new ArrayList<ScoreCard>();
			for (ScoreCard scoreCard : stageScore.getScoreCards()) {
				
				Competitor competitor = findByPractiScoreId(scoreCard.getCompetitorPractiScoreId(), match);
				
				// Remove cards if competitor or stage has been deleted
				if (competitor == null || competitor.isDeleted() || stage == null) {
					cardsToRemove.add(scoreCard);
					continue;
				}
				
				scoreCard.setCompetitor(competitor);
				
				scoreCard.setHitsAndPoints();
				scoreCard.setStage(stage);
				
				String scoreCardDivision = scoreCard.getCompetitor().getDivision();

				if (!match.getDivisionsWithResults().contains(scoreCardDivision))  {
					match.getDivisionsWithResults().add(scoreCardDivision);
				}
			}
			stageScore.getScoreCards().removeAll(cardsToRemove);
			
			// If match has results for more than one division, add IPSCDivision combined to match for result listing purposes.
			if (match.getDivisionsWithResults().size() > 1 && !match.getDivisionsWithResults().contains(Constants.COMBINED_DIVISION)) {
				match.getDivisionsWithResults().add(Constants.COMBINED_DIVISION);
			}
		}
	}
	
	@Transactional
	public Match saveMatchDef(Match match) throws DatabaseException {
		Match savedMatch = matchRepository.save(match);
		return savedMatch;
	}

	@Transactional
	public List<Match> findAll() {
		return matchRepository.findAll();
	}
	
	private Competitor findByPractiScoreId(String id, Match match) {
		Competitor resultCompetitor = null;
		for (Competitor comp : match.getCompetitors()) {
			if (comp.getPractiScoreId().equals(id)) {
				resultCompetitor = comp;
				break;
			}
		}
		
		return resultCompetitor;
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
	@Transactional
	public Match getOne(Long id) throws DatabaseException {
		return matchRepository.getOne(id);
	}
	@Transactional
	public Match findByPractiScoreId(String practiScoreId) throws DatabaseException {
		Match match = matchRepository.findByPractiScoreId(practiScoreId);
		
		return match;
	}
	@Transactional
	public void delete(Long matchId) {
	
		try {
			Match match = getOne(matchId);
			
			statisticsService.deleteByMatch(matchId);
			matchResultDataService.deleteByMatch(matchId);
			scoreCardService.deleteByMatch(match);
			matchRepository.delete(match);
			
		}
		catch (DatabaseException e) {
			e.printStackTrace();
		}
	
	}
	@Transactional
	public void setMatchStatus(Long matchId, MatchStatus newStatus) throws DatabaseException {
		Match match = getOne(matchId);
		match.setStatus(newStatus);
	}
}
