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
import fi.ipscResultServer.domain.resultData.MatchResultData;
import fi.ipscResultServer.domain.resultData.MatchResultDataLine;
import fi.ipscResultServer.domain.resultData.StageResultDataLine;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.repository.MatchResultDataRepository;
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
	
	public List<MatchResultData> findByMatch(Match match) throws DatabaseException {
		return matchResultDataRepository.find(match);
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
	public void deleteByMatch(Match match) throws DatabaseException {
		List<MatchResultData> resultDataList = findByMatch(match);
		for (MatchResultData resultData : resultDataList) {
			matchResultDataRepository.delete(resultData);
		}
	}
	
	@Transactional
	public MatchResultData generateMatchResultListing(Match match) throws DatabaseException {
		logger.info("Generating match result data...");
		for (String division : match.getDivisionsWithResults()) {
			MatchResultData matchResultData = new MatchResultData(match, division);
			List<MatchResultDataLine> dataLines = new ArrayList<MatchResultDataLine>();
			
			// Calculate competitor total points for match and set scored stages count
			for (Competitor competitor : match.getCompetitors()) {
				
				// Skip competitor if not generating a result list for competitor's division
				// or combined division.
				if (!division.equals(Constants.COMBINED_DIVISION) && !competitor.getDivision().equals(division)) continue;
				MatchResultDataLine competitorDataLine = new MatchResultDataLine(competitor, matchResultData);
				List<StageResultDataLine> stageResultDataLines = stageResultDataService.findStageResultDataLinesByCompetitorAndDivision(competitor, division);
				
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
			save(matchResultData);
		}
		return null;
	}
}
