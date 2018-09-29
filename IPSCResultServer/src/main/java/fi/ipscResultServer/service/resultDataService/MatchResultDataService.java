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
import fi.ipscResultServer.repository.springJDBCRepository.MatchResultDataRepository;
import fi.ipscResultServer.service.CompetitorService;
import fi.ipscResultServer.service.MatchService;
import fi.ipscResultServer.service.ScoreCardService;

@Service
public class MatchResultDataService {
	
	@Autowired
	StageResultDataService stageResultDataService;
	
	@Autowired
	private MatchResultDataRepository matchResultDataRepository;
	
	@Autowired
	private MatchService matchService;
	
	@Autowired
	private CompetitorService competitorService;
	
	@Autowired
	private ScoreCardService scoreCardService;
	
	private final static Logger LOGGER = Logger.getLogger(ScoreCardService.class);
	
	public MatchResultData findByMatchAndDivision(Long matchId, String division) {
		MatchResultData data = matchResultDataRepository.findByMatchAndDivision(matchId, division);
		data.setMatch(matchService.lazyGetOne(data.getMatchId()));
		data.setDataLines(matchResultDataRepository.getDataLines(data.getId()));
		data.setDivision(division);
		for (MatchResultDataLine line : data.getDataLines()) {
			line.setCompetitor(competitorService.lazyGetOne(line.getCompetitorId()));
			line.setMatchResultData(data);
		}
		
		return data;
	}
	
	public List<MatchResultData> findByMatch(Long matchId) throws DatabaseException {
		return null;
	}
	
	public MatchResultDataLine findMatchResultDataLinesByCompetitor(Competitor competitor) 
			throws DatabaseException {
		return null;
	}
	
	@Transactional
	public void save(MatchResultData matchResultData) {
		matchResultDataRepository.save(matchResultData);
	}
	@Transactional
	public void deleteByMatch(Long matchId) {
		matchResultDataRepository.deleteByMatch(matchId);
	}
	
	@Transactional
	public MatchResultData generateMatchResultListing(Long matchId) {
		deleteByMatch(matchId);
		Match match = matchService.getOne(matchId);

		for (String division : match.getDivisionsWithResults()) {
			LOGGER.info("Generating match result data for division " + division);
			
			MatchResultData matchResultData = new MatchResultData(match, division);
			List<MatchResultDataLine> matchResultDataLines = new ArrayList<MatchResultDataLine>();
			
			// Get all stage result data lines for division
			List<StageResultDataLine> allDivisionStageResultDatalines = new ArrayList<StageResultDataLine>();			
			for (Stage stage : match.getStages()) {
				allDivisionStageResultDatalines.addAll(stageResultDataService.getStageResultListing(stage, division).getDataLines());
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
			
			// Order by points, set rank, points and percentages
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
