package fi.ipscResultServer.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.domain.resultData.CompetitorResultData;
import fi.ipscResultServer.domain.resultData.MatchResultDataLine;
import fi.ipscResultServer.domain.statistics.CompetitorStatistics;
import fi.ipscResultServer.domain.statistics.CompetitorStatisticsLine;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.repository.CompetitorStatisticsRepository;
import fi.ipscResultServer.service.resultDataService.CompetitorResultDataService;
import fi.ipscResultServer.service.resultDataService.MatchResultDataService;

@Service
public class StatisticsService {
	
	@Autowired
	private CompetitorResultDataService competitorResultDataService;
	
	@Autowired
	private MatchResultDataService matchResultDataService;
	
	@Autowired 
	private CompetitorStatisticsRepository competitorStatisticsRepository;
	
	final static Logger logger = Logger.getLogger(StatisticsService.class);
	
	public CompetitorStatistics findCompetitorStatisticsByMatchAndDivision(String matchId, String division) 
		throws DatabaseException {
		return competitorStatisticsRepository.findCompetitorStatisticsByMatchAndDivision(matchId, division);
	}
	
	@Transactional
	public void generateCompetitorStatistics(Match match) throws DatabaseException {
		deleteByMatch(match);	
				
		for (String division : match.getDivisions()) {
			logger.info("Generating match statistics for " + division);
			CompetitorStatistics statistics = new CompetitorStatistics(match, division);
			List<CompetitorStatisticsLine> lines = new ArrayList<CompetitorStatisticsLine>();
			
			// Generate CompetitorStatisticsLine instances for competitors
			for (Competitor competitor : match.getCompetitors()) {
				if (!competitor.getDivision().equals(division)) continue;
				CompetitorStatisticsLine line = new CompetitorStatisticsLine(competitor);
				double matchTime = 0.0;
				int aHits = 0;
				int cHits = 0;
				int dHits = 0;
				int misses = 0;
				int proceduralPenalties = 0;
				int noShootHits = 0;
				int sumOfPoints = 0;
				CompetitorResultData competitorResultData = competitorResultDataService.findByCompetitorAndMatch(competitor.getId(), match.getId());
				
				// Exclude competitors with no score card data. Will not be shown at all in statistics listing.
				if (competitorResultData.getScoreCards() == null || competitorResultData.getScoreCards().size() == 0) continue; 
				for (ScoreCard card : competitorResultData.getScoreCards().values()) {
					aHits += card.getaHits();
					cHits += card.getcHits();
					dHits += card.getdHits();
					misses += card.getMisses();
					proceduralPenalties += card.getProceduralPenalties();
					noShootHits += card.getNoshootHits();
					sumOfPoints += card.getPoints();
					matchTime += card.getTime();
				}
				line.setaHits(aHits);
				line.setcHits(cHits);
				line.setdHits(dHits);
				line.setMisses(misses);
				line.setProceduralPenalties(proceduralPenalties);
				line.setNoShootHits(noShootHits);
				line.setSumOfPoints(sumOfPoints);
				line.setMatchTime(matchTime);
				
				int totalShots = aHits + cHits + dHits + misses;
				line.setaHitPercentage((double) aHits / (double) totalShots * 100.0);
				
				// Get following data from competitor's MatchResultDataLine instance: position within division results,
				// total points and score percentage within division. 
				MatchResultDataLine matchDataLine = matchResultDataService.findMatchResultDataLinesByCompetitor(competitor);
				if (matchDataLine != null) {
					line.setDivisionRank(matchDataLine.getRank());
					line.setDivisionPoints(matchDataLine.getPoints());
					line.setDivisionScorePercentage(matchDataLine.getScorePercentage());
				}
				lines.add(line);
			}
			if (lines.size() == 0) continue; 
			statistics.setStatisticsLines(lines);
			competitorStatisticsRepository.save(statistics);
		}
	}
	
	@Transactional
	public void deleteByMatch(Match match) throws DatabaseException {
		competitorStatisticsRepository.deleteByMatch(match);
	}
}
