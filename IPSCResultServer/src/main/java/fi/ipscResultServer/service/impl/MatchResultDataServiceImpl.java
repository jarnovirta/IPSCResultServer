package fi.ipscResultServer.service.impl;

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
import fi.ipscResultServer.repository.springJDBCRepository.ResultDataRepository;
import fi.ipscResultServer.service.CompetitorService;
import fi.ipscResultServer.service.MatchResultDataService;
import fi.ipscResultServer.service.MatchService;
import fi.ipscResultServer.service.StageResultDataService;

@Service
public class MatchResultDataServiceImpl implements MatchResultDataService {
	
	@Autowired
	StageResultDataService stageResultDataService;
	
	@Autowired
	private ResultDataRepository matchResultDataRepository;
	
	@Autowired
	private MatchService matchService;
	
	@Autowired
	private CompetitorService competitorService;

	
	private final static Logger LOGGER = Logger.getLogger(ScoreCardServiceImpl.class);
	
	public MatchResultData findByMatchAndDivision(String matchPractiScoreId, String division) {
		
		Long matchId = matchService.getIdByPractiScoreId(matchPractiScoreId);
		Match match = matchService.getOne(matchId, false);
		
		List<MatchResultDataLine> dataLines;
		if (division.equals(Constants.COMBINED_DIVISION)) dataLines = matchResultDataRepository.findResultDataLinesByMatch(matchId);
		else dataLines = matchResultDataRepository.findResultDataLinesByMatchAndDivision(matchId, division);
		
		Collections.sort(dataLines);
		MatchResultData data = new MatchResultData(match, division, dataLines);
		setMatchResultData(data);
		
		return data;
	}

	private void setMatchResultData(MatchResultData data) {
		List<Competitor> competitors = competitorService.findByMatchAndDivision(data.getMatch().getId(), data.getDivision());
		int rank = 1;
		double topPoints = -1.0;
		for (MatchResultDataLine line : data.getDataLines()) {
			if (rank == 1) topPoints = line.getPoints();
			if (topPoints > 0) line.setScorePercentage(line.getPoints() / topPoints * 100);
			line.setCompetitor(getCompetitor(competitors, line.getCompetitorId()));
			line.setRank(rank++);
			
		}
	}
	
	private Competitor getCompetitor(List<Competitor> competitors, Long id) {
		for (Competitor comp : competitors) if (comp.getId().equals(id)) return comp;
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
		LOGGER.info("Generating match result data (match ID: " + matchId + ")");

		return null;
	}

//	private List<StageResultDataLine> getCompetitorStageResultDataLines(Competitor competitor, List<StageResultDataLine> allStageResultDatalines) {
//		List<StageResultDataLine> stageResultDataLines = new ArrayList<StageResultDataLine>();
//		for (StageResultDataLine line : allStageResultDatalines) {
//			if (line.getCompetitor().getPractiScoreId().equals(competitor.getPractiScoreId())) stageResultDataLines.add(line);
//		}
//		return stageResultDataLines;
//	}
}
