package fi.ipsc_result_server.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipsc_result_server.domain.MatchScore;
import fi.ipsc_result_server.domain.ScoreCard;
import fi.ipsc_result_server.domain.Stage;
import fi.ipsc_result_server.domain.StageScore;
import fi.ipsc_result_server.domain.ResultData.MatchResultData;
import fi.ipsc_result_server.domain.ResultData.StageResultData;

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

	
	final static Logger logger = Logger.getLogger(MatchScoreService.class);
	
	@Transactional
	public void save(MatchScore matchScore) {
		List<Stage> stagesWithNewResults = new ArrayList<Stage>();
		
		// Delete old match result listing
		MatchResultData oldMatchResultData = findByMatchId(matchScore.getMatchId());
		if (oldMatchResultData != null) {
			logger.info("Deleting old MatchResultData");
			delete(oldMatchResultData);
		}
		
		for (StageScore stageScore : matchScore.getStageScores()) {
			Stage stage = stageService.getOne(stageScore.getStageId());
			stagesWithNewResults.add(stage);
			
			// Delete old stage result listing
			List<StageResultData> oldStageResultData = stageScoreService.findByStage(stage);
			if (oldStageResultData != null) {
				stageScoreService.deleteInBatch(oldStageResultData);
			}
			
			// Remove old scorecards
			scoreCardService.deleteInBatch(stageScore.getScoreCards());
			
			for (ScoreCard scoreCard : stageScore.getScoreCards()) {
				scoreCard.setHitsAndPoints();
				scoreCard.setStage(stage);
				scoreCard.setStageId(stage.getId());
				scoreCard.setCompetitor(competitorService.getOne(scoreCard.getCompetitorId()));
				
				scoreCardService.removeOldScoreCard(scoreCard);
				scoreCardService.save(scoreCard);
			}
		}
		if (stagesWithNewResults.size() > 0) {
			for (Stage stageWithNewResults : stagesWithNewResults) {
				logger.info("Generating stage results data for stages with new results...");
				stageScoreService.generateStageResultsListing(stageWithNewResults);
			}
			logger.info("Generating match result data...");
			generateMatchResultListing(matchScore.getMatchId());
		}
		System.out.println("**** MATCH SCORE SAVE DONE");
	}
	
	
	
	@Transactional
	public MatchResultData generateMatchResultListing(String matchId) {
		// TODO: generate match result listing
		
		return null;
	}
	
	@Transactional
	public MatchResultData findByMatchId(String matchId) {
		MatchResultData resultData = null;
		try {
			String queryString = "SELECT m FROM MatchResultData m WHERE m.match.id = :matchId";
			TypedQuery<MatchResultData> query = entityManager.createQuery(queryString, MatchResultData.class); 
			query.setParameter("matchId", matchId);
			List<MatchResultData> resultList = query.getResultList();
			if (resultList != null && resultList.size() > 0) {
				resultData = resultList.get(0);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultData;
	}
	@Transactional
	public void delete(MatchResultData matchResultData) {
		entityManager.remove(matchResultData);
	}
	
}
