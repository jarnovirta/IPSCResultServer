package fi.ipscResultServer.service;

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
	
	public List<CompetitorStatistics> findCompetitorStatisticsByMatchAndDivision(Long matchId, String division) 
		throws DatabaseException {
		return competitorStatisticsRepository.findCompetitorStatisticsByMatchAndDivision(matchId, division);
	}
	
	public List<CompetitorStatistics> findCompetitorStatisticsByMatch(Long matchId) 
			throws DatabaseException {
			return competitorStatisticsRepository.findCompetitorStatisticsByMatch(matchId);
		}
		
	
	@Transactional
	public void generateCompetitorStatistics(Match match) throws DatabaseException {
		deleteByMatch(match);	
				
		for (String division : match.getDivisionsWithResults()) {
			logger.info("Generating match statistics for " + division);
			
			// Generate CompetitorStatisticsLine instances for competitors
			for (Competitor competitor : match.getCompetitors()) {
				if (!competitor.getDivision().equals(division)) continue;
				CompetitorStatistics line = new CompetitorStatistics(competitor, match);
				double matchTime = 0.0;
				int aHits = 0;
				int cHits = 0;
				int dHits = 0;
				int misses = 0;
				int proceduralPenalties = 0;
				int additionalPenalties = 0;
				int noShootHits = 0;
				int sumOfPoints = 0;
				CompetitorResultData competitorResultData = 
						competitorResultDataService.findByCompetitorAndMatchPractiScoreIds(competitor.getPractiScoreId(), match.getPractiScoreId());
				
				// Exclude competitors with no score card data. Will not be shown at all in statistics listing.
				if (competitorResultData.getScoreCards() == null || competitorResultData.getScoreCards().size() == 0) continue; 
				for (ScoreCard card : competitorResultData.getScoreCards().values()) {
					aHits += card.getaHits();
					cHits += card.getcHits();
					dHits += card.getdHits();
					misses += card.getMisses();
					proceduralPenalties += card.getProceduralPenalties();
					additionalPenalties += card.getAdditionalPenalties();
					noShootHits += card.getNoshootHits();
					sumOfPoints += card.getPoints();
					matchTime += card.getTime();
				}
				line.setaHits(aHits);
				line.setcHits(cHits);
				line.setdHits(dHits);
				line.setMisses(misses);
				line.setProceduralPenalties(proceduralPenalties);
				line.setAdditionalPenalties(additionalPenalties);
				line.setNoShootHits(noShootHits);
				line.setSumOfPoints(sumOfPoints);
				line.setMatchTime(matchTime);
				
				int totalShots = aHits + cHits + dHits + misses + noShootHits;
				line.setaHitPercentage((double) aHits / (double) totalShots * 100.0);
				
				// Get following data from competitor's MatchResultDataLine instance: position within division results,
				// total points and score percentage within division. 
				MatchResultDataLine matchDataLine = matchResultDataService.findMatchResultDataLinesByCompetitor(competitor);
				if (matchDataLine != null) {
					line.setDivisionRank(matchDataLine.getRank());
					line.setDivisionPoints(matchDataLine.getPoints());
					line.setDivisionScorePercentage(matchDataLine.getScorePercentage());
				}
				competitorStatisticsRepository.save(line);
			}
		}
	}
	
	@Transactional
	public void deleteByMatch(Match match) throws DatabaseException {
		competitorStatisticsRepository.deleteByMatch(match);
	}
}
