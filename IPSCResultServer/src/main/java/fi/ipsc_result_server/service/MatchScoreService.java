package fi.ipsc_result_server.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipsc_result_server.domain.Competitor;
import fi.ipsc_result_server.domain.IPSCDivision;
import fi.ipsc_result_server.domain.Match;
import fi.ipsc_result_server.domain.MatchScore;
import fi.ipsc_result_server.domain.ScoreCard;
import fi.ipsc_result_server.domain.Stage;
import fi.ipsc_result_server.domain.StageScore;
import fi.ipsc_result_server.domain.ResultData.MatchResultData;
import fi.ipsc_result_server.domain.ResultData.MatchResultDataLine;
import fi.ipsc_result_server.domain.ResultData.StageResultData;
import fi.ipsc_result_server.domain.ResultData.StageResultDataLine;

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
	MatchService matchService;
	
	@Autowired
	CompetitorService competitorService;

	@Autowired
	ScoreCardService scoreCardService;

	@Autowired
	ResultDataService resultDataService;
	
	final static Logger logger = Logger.getLogger(MatchScoreService.class);
	
	@Transactional
	public void save(MatchScore matchScore) {
		logger.info("Saving match score data...");
		
		Match match = matchService.getOne(matchScore.getMatchId());
		if (match.getDivisions() == null) match.setDivisions(new ArrayList<IPSCDivision>());
		
		List<Stage> stagesWithNewResults = new ArrayList<Stage>();
		// Delete old match result listing
		List<MatchResultData> oldMatchResultData = findByMatchId(matchScore.getMatchId());
		if (oldMatchResultData != null) {
			deleteInBatch(oldMatchResultData);
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
			
			
			if (match.getDivisionsWithResults() == null) {
				match.setDivisionsWithResults(new ArrayList<IPSCDivision>());
			}
			List<IPSCDivision> newIPSCDivisionsWithResults = new ArrayList<IPSCDivision>();
			for (ScoreCard scoreCard : stageScore.getScoreCards()) {
				scoreCard.setHitsAndPoints();
				scoreCard.setStage(stage);
				scoreCard.setStageId(stage.getId());
				scoreCard.setCompetitor(competitorService.getOne(scoreCard.getCompetitorId()));
				
				scoreCardService.removeOldScoreCard(scoreCard);
				scoreCardService.save(scoreCard);
				
				IPSCDivision scoreCardDivision = scoreCard.getCompetitor().getDivision();

				if (!match.getDivisions().contains(scoreCardDivision) && !newIPSCDivisionsWithResults.contains(scoreCardDivision))  {
					newIPSCDivisionsWithResults.add(scoreCardDivision);
				}
			}
			// If match has results for more than one division, add IPSCDivision combined to match for result listing purposes.
			if (match.getDivisionsWithResults().size() + newIPSCDivisionsWithResults.size() > 1 && !match.getDivisions().contains(IPSCDivision.COMBINED)) {
				match.getDivisionsWithResults().add(IPSCDivision.COMBINED);
			}
		}
		if (stagesWithNewResults.size() > 0) {
			for (Stage stageWithNewResults : stagesWithNewResults) {
				logger.info("Generating stage results data for stages with new results...");
				stageScoreService.generateStageResultsListing(stageWithNewResults);
			}
		}
		generateMatchResultListing(match);
		System.out.println("**** MATCH SCORE SAVE DONE");
	}
	
	
	
	@Transactional
	public MatchResultData generateMatchResultListing(Match match) {
		logger.info("Generating match result data...");
		
		
		for (IPSCDivision division : match.getDivisionsWithResults()) {
			MatchResultData matchResultData = new MatchResultData(match, division);
			List<MatchResultDataLine> dataLines = new ArrayList<MatchResultDataLine>();
			
			// Calculate competitor total points for match and set scored stages count
			for (Competitor competitor : match.getCompetitors()) {
				MatchResultDataLine competitorDataLine = new MatchResultDataLine(competitor, matchResultData);
				List<StageResultDataLine> stageResultDataLines = resultDataService.getStageResultDataLinesForCompetitor(competitor);
				
				// Exclude competitors with no results for stages (did not show up)
				if (stageResultDataLines.size() == 0) continue;
				
				competitorDataLine.setScoredStages(stageResultDataLines.size());
				double totalPoints = 0.0;
				for (StageResultDataLine stageResultDataLine : stageResultDataLines) {
					totalPoints += stageResultDataLine.getStagePoints();
				}
				competitorDataLine.setPoints(totalPoints);
				dataLines.add(competitorDataLine);
			}
			
			Collections.sort(dataLines);
			
			double topPoints = -1.0;
			int rank = 1;
			for (MatchResultDataLine dataLine : dataLines) {
				if (rank == 1) topPoints = dataLine.getPoints();
				dataLine.setScorePercentage(dataLine.getPoints() / topPoints * 100);
				dataLine.setRank(rank);
				rank++;
			}
			matchResultData.setDataLines(dataLines);
			logger.info("Saving match result data for division " + division);
			resultDataService.save(matchResultData);
		}
		return null;
	}
	
	@Transactional
	public List<MatchResultData> findByMatchId(String matchId) {
		try {
			String queryString = "SELECT m FROM MatchResultData m WHERE m.match.id = :matchId";
			TypedQuery<MatchResultData> query = entityManager.createQuery(queryString, MatchResultData.class); 
			query.setParameter("matchId", matchId);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Transactional
	public void deleteInBatch(List<MatchResultData> matchResultDataList) {
		for (MatchResultData matchResultData : matchResultDataList) {
			entityManager.remove(matchResultData);
		}
	}
	
}
