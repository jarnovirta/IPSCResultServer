package fi.ipscResultServer.service.resultDataService;

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
import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.domain.resultData.MatchResultData;
import fi.ipscResultServer.domain.resultData.MatchResultDataLine;
import fi.ipscResultServer.domain.resultData.StageResultDataLine;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.repository.eclipseLinkRepository.MatchResultDataRepository;
import fi.ipscResultServer.service.ScoreCardService;

@Service
public class MatchResultDataService {

	@Autowired
	MatchResultDataRepository matchResultDataRepository;
	
	@Autowired
	StageResultDataService stageResultDataService;
	
	final static Logger logger = Logger.getLogger(ScoreCardService.class);
	
	public MatchResultData findByMatchAndDivision(Long matchId, String division) 
			throws DatabaseException {
		return matchResultDataRepository.findByMatchAndDivision(matchId, division);
	}
	
	public List<MatchResultData> findByMatch(Long matchId) throws DatabaseException {
		return matchResultDataRepository.find(matchId);
	}
	
	public MatchResultDataLine findMatchResultDataLinesByCompetitor(Competitor competitor) 
			throws DatabaseException {
		return matchResultDataRepository.findMatchResultDataLinesByCompetitor(competitor);
	}
	
	@Transactional
	public MatchResultData save(MatchResultData matchResultData) throws DatabaseException {
		return matchResultDataRepository.save(matchResultData);
		
	}
	@Transactional
	public void deleteByMatch(Long matchId) throws DatabaseException {
		matchResultDataRepository.deleteInBatch(findByMatch(matchId));
	}
	
	@Transactional
	public MatchResultData generateMatchResultListing(Match match) throws DatabaseException {
		for (String division : match.getDivisionsWithResults()) {
			MatchResultData matchResultData = new MatchResultData(match, division);
			List<MatchResultDataLine> matchResultDataLines = new ArrayList<MatchResultDataLine>();
			
			// List all stage result data lines for division
			List<StageResultDataLine> allDivisionStageResultDatalines = new ArrayList<StageResultDataLine>();			
			for (Stage stage : match.getStages()) {
				allDivisionStageResultDatalines.addAll(stageResultDataService.getStageResultListing(match.getPractiScoreId(), stage.getPractiScoreId(), division).getDataLines());
			}
			
			// Calculate competitor total points for match and set scored stages count
			for (Competitor competitor : match.getCompetitors()) {
				
				// Skip competitor if not generating a result list for competitor's division
				// or combined division.
				if (!division.equals(Constants.COMBINED_DIVISION) && !competitor.getDivision().equals(division)) continue;
				
				MatchResultDataLine competitorDataLine = new MatchResultDataLine(competitor, matchResultData);
				
				List<StageResultDataLine> competitorStageResultDataLines = getCompetitorStageResultDataLines(competitor, allDivisionStageResultDatalines);
				
				// Exclude competitors with no results for stages (did not show up)
				if (competitorStageResultDataLines.size() == 0) continue;
				
				competitorDataLine.setScoredStages(competitorStageResultDataLines.size());
				double totalPoints = 0.0;
				for (StageResultDataLine dataLine : competitorStageResultDataLines) {
					totalPoints += dataLine.getStagePoints();
				}
				competitorDataLine.setPoints(totalPoints);
				matchResultDataLines.add(competitorDataLine);
			}
			
			Collections.sort(matchResultDataLines);
			
			double topPoints = -1.0;
			int rank = 1;
			for (MatchResultDataLine dataLine : matchResultDataLines) {
				if (rank == 1) topPoints = dataLine.getPoints();
				dataLine.setScorePercentage(dataLine.getPoints() / topPoints * 100);
				dataLine.setRank(rank);
				rank++;
			}
			matchResultData.setDataLines(matchResultDataLines);
			logger.info("Saving match result data for division " + division);
			save(matchResultData);
		}
		return null;
	}
	private List<StageResultDataLine> getCompetitorStageResultDataLines(Competitor competitor, List<StageResultDataLine> allStageResultDatalines) {
		List<StageResultDataLine> stageResultDataLines = new ArrayList<StageResultDataLine>();
		for (StageResultDataLine line : allStageResultDatalines) {
			if (line.getCompetitor().getPractiScoreId().equals(competitor.getPractiScoreId())) stageResultDataLines.add(line);
		}
		return stageResultDataLines;
	}
}
