package fi.ipsc_result_server.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipsc_result_server.domain.MatchScore;
import fi.ipsc_result_server.domain.ScoreCard;
import fi.ipsc_result_server.domain.Stage;
import fi.ipsc_result_server.domain.StageScore;
import fi.ipsc_result_server.domain.ResultData.MatchResultData;
import fi.ipsc_result_server.util.DataFormatUtils;

@Service
public class MatchScoreService {
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	StageService stageService;
	
	@Autowired
	StageScoreService stageScoreService;
	
	@Autowired
	MatchScoreService matchScoreService;
	
	@Autowired
	CompetitorService competitorService;

	@Autowired
	ScoreCardService scoreCardService;
	
	@Transactional
	public void save(MatchScore matchScore) {
		List<Stage> stagesWithNewResults = new ArrayList<Stage>();
		deleteMatchResultListing(matchScore.getMatchId());
		System.out.println("*** STARTING MATCHSCORE SAVE");
		for (StageScore stageScore : matchScore.getStageScores()) {
			Stage stage = stageService.getOne(stageScore.getStageId());
			stagesWithNewResults.add(stage);
			stageScoreService.deleteStageResultListing(stage.getId());
			System.out.println("**** DONE DELETING StageResultListing");
			for (ScoreCard scoreCard : stageScore.getScoreCards()) {
				scoreCard.setStage(stage);
				scoreCard.setStageId(stage.getId());
				scoreCard.setCompetitor(competitorService.getOne(scoreCard.getCompetitorId()));
				scoreCard.setaHits(scoreCard.getaHits() + scoreCard.getPopperHits());
				scoreCard.setMisses(scoreCard.getMisses() + scoreCard.getPopperMisses());
				scoreCard.setCompetitor(competitorService.getOne(scoreCard.getCompetitorId()));
				scoreCard.setHitFactor(DataFormatUtils.round(scoreCard.getPoints() / scoreCard.getTime(), 2));
				
				removeOldScoreCard(scoreCard);
				scoreCardService.save(scoreCard);
			}
		}
		System.out.println(stagesWithNewResults.size() + " STAGES WITH NEW RESULTS. GENERATING STAGE RESULT LISTINGS...");
		if (stagesWithNewResults.size() > 0) {
			for (Stage stageWithNewResults : stagesWithNewResults) {
				stageScoreService.generateStageResultsListing(stageWithNewResults);
			}
			generateMatchResultListing(matchScore.getMatchId());
		}
		System.out.println("**** MATCH SCORE SAVE DONE");
	}
	
	@Transactional
	private void removeOldScoreCard(ScoreCard scoreCard) {
		System.out.println("**** REMOVING OLD SCORECARDS for scorecard with match id " + scoreCard.getStage().getMatch().getId() + 
				" AND stage id " + scoreCard.getStage().getId() + " AND competitor id ");
		try {
			String queryString = "SELECT s FROM ScoreCard s WHERE s.stage.match.id = :matchId AND s.stage.id = :stageId "
					+ "AND s.competitor.id = :competitorId AND s.modified <= :modified";
			TypedQuery<ScoreCard> query = entityManager.createQuery(queryString, ScoreCard.class);
			query.setParameter("matchId", scoreCard.getStage().getMatch().getId());
			query.setParameter("stageId", scoreCard.getStage().getId());
			query.setParameter("competitorId", scoreCard.getCompetitor().getId());
			query.setParameter("modified", scoreCard.getModified(), TemporalType.TIMESTAMP);
			List<ScoreCard> oldScoreCards = query.getResultList();
			
			if (oldScoreCards != null) {
				System.out.println("**** Found " + oldScoreCards.size() + " old cards to remove. Removing...");
				scoreCardService.deleteInBatch(oldScoreCards);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("**** SCORE CARD REMOVE DONE");
	}
	
	@Transactional
	public MatchResultData generateMatchResultListing(String matchId) {
		// TODO: generate match result listing
		
		return null;
	}
	@Transactional
	public void deleteMatchResultListing(String matchId) {
		try {
			String queryString = "DELETE FROM MatchResultData m WHERE m.match.id = :matchId";
			entityManager.createQuery(queryString).setParameter("matchId", matchId).executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
