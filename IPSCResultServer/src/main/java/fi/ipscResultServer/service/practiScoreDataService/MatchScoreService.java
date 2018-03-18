package fi.ipscResultServer.service.practiScoreDataService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.Constants;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.MatchScore;
import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.domain.StageScore;
import fi.ipscResultServer.domain.resultData.MatchResultData;
import fi.ipscResultServer.domain.resultData.MatchResultDataLine;
import fi.ipscResultServer.domain.resultData.StageResultDataLine;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.service.CompetitorService;
import fi.ipscResultServer.service.MatchService;
import fi.ipscResultServer.service.ScoreCardService;
import fi.ipscResultServer.service.StageService;
import fi.ipscResultServer.service.StatisticsService;
import fi.ipscResultServer.service.resultDataService.MatchResultDataService;
import fi.ipscResultServer.service.resultDataService.StageResultDataService;

@Service
public class MatchScoreService {
		
	@Autowired
	StageService stageService;
	
	@Autowired
	StageResultDataService stageResultDataService;
	
	@Autowired
	MatchService matchService;
	
	@Autowired
	CompetitorService competitorService;

	@Autowired
	ScoreCardService scoreCardService;

	@Autowired
	MatchResultDataService matchResultDataService;
	
	@Autowired
	StatisticsService statisticsService;
	
	final static Logger logger = Logger.getLogger(MatchScoreService.class);
	
	// Save match result data from PractiScore and generate result listings
	// for new data
	@Transactional
	public void save(MatchScore matchScore) throws DatabaseException {
		logger.info("SAVING MATCH RESULT DATA...");
		
		if (matchScore == null) return; 
		
		Match match = matchService.getOne(matchScore.getMatchId());
		if (match.getDivisions() == null) match.setDivisions(new ArrayList<String>());
		
		List<Stage> stagesWithNewResults = new ArrayList<Stage>();
		
		// Delete old match result listing
		matchResultDataService.deleteByMatch(match);
						
		for (StageScore stageScore : matchScore.getStageScores()) {
			Stage stage = stageService.getOne(stageScore.getStageId());
			stagesWithNewResults.add(stage);
			
			// Delete old stage result listing
			stageResultDataService.deleteByStage(stage);
			
			// Remove old scorecards
			scoreCardService.deleteInBatch(stageScore.getScoreCards());
						
			if (match.getDivisionsWithResults() == null) {
				match.setDivisionsWithResults(new ArrayList<String>());
			}
			List<String> newIPSCDivisionsWithResults = new ArrayList<String>();
			for (ScoreCard scoreCard : stageScore.getScoreCards()) {
				scoreCard.setHitsAndPoints();
				scoreCard.setStage(stage);
				scoreCard.setStageId(stage.getId());
				scoreCard.setCompetitor(competitorService.getOne(scoreCard.getCompetitorId()));
				
				scoreCardService.delete(scoreCard);
				scoreCardService.save(scoreCard);
				
				String scoreCardDivision = scoreCard.getCompetitor().getDivision();

				if (!match.getDivisions().contains(scoreCardDivision) && !newIPSCDivisionsWithResults.contains(scoreCardDivision))  {
					newIPSCDivisionsWithResults.add(scoreCardDivision);
				}
			}
			// If match has results for more than one division, add IPSCDivision combined to match for result listing purposes.
			if (match.getDivisionsWithResults().size() + newIPSCDivisionsWithResults.size() > 1 && !match.getDivisions().contains(Constants.COMBINED_DIVISION)) {
				match.getDivisionsWithResults().add(Constants.COMBINED_DIVISION);
			}
		}
		if (stagesWithNewResults.size() > 0) {
			for (Stage stageWithNewResults : stagesWithNewResults) {
				logger.info("Generating stage results data for stages with new results...");
				stageResultDataService.generateStageResultsListing(stageWithNewResults);
			}
		}
		generateMatchResultListing(match);
		statisticsService.generateCompetitorStatistics(match);
		logger.info("**** MATCH RESULT DATA SAVE DONE");
	}
	
	@Transactional
	public MatchResultData generateMatchResultListing(Match match) throws DatabaseException {
		logger.info("Generating match result data...");
		for (String division : match.getDivisionsWithResults()) {
			MatchResultData matchResultData = new MatchResultData(match, division);
			List<MatchResultDataLine> dataLines = new ArrayList<MatchResultDataLine>();
			
			// Calculate competitor total points for match and set scored stages count
			for (Competitor competitor : match.getCompetitors()) {
				MatchResultDataLine competitorDataLine = new MatchResultDataLine(competitor, matchResultData);
				List<StageResultDataLine> stageResultDataLines = stageResultDataService.findStageResultDataLinesByCompetitor(competitor);
				
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
			matchResultDataService.save(matchResultData);
		}
		return null;
	}
}
