package fi.ipscResultServer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.Constants;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.domain.resultData.MatchResultData;
import fi.ipscResultServer.domain.resultData.MatchResultDataLine;
import fi.ipscResultServer.domain.statistics.CompetitorStatistics;
import fi.ipscResultServer.repository.springJDBCRepository.StatisticsRepository;
import fi.ipscResultServer.service.CompetitorService;
import fi.ipscResultServer.service.MatchResultDataService;
import fi.ipscResultServer.service.MatchService;
import fi.ipscResultServer.service.ScoreCardService;
import fi.ipscResultServer.service.StatisticsService;

@Service
public class StatisticsServiceImpl implements StatisticsService {
	
	@Autowired
	private MatchResultDataService matchResultDataService;
	
	@Autowired
	private MatchService matchService;
	
	@Autowired
	private StatisticsRepository competitorStatisticsRepository;
	
	@Autowired
	private ScoreCardService scoreCardService;
	
	@Autowired
	private CompetitorService competitorService;
	
	private final static Logger LOGGER = Logger.getLogger(StatisticsServiceImpl.class);
	
	public List<CompetitorStatistics> findByMatchAndDivision(Long matchId, 
			String division) {
		List<CompetitorStatistics> stats = competitorStatisticsRepository.findByMatchAndDivision(matchId, division);
		fetchReferences(stats, matchId);
		return stats;
	}
	
	public List<CompetitorStatistics> findByMatch(Long matchId) {
		List<CompetitorStatistics> stats = competitorStatisticsRepository.findByMatch(matchId);
		fetchReferences(stats, matchId);
		return stats;
	}
	
	@Transactional
	public void generateCompetitorStatistics(Long matchId) {
//		// Delete old statistics
//		LOGGER.info("Generating statistics for match");
//		deleteByMatch(matchId);	
//		
//		List<CompetitorStatistics> competitorStatisticsList = new ArrayList<CompetitorStatistics>();
//		Match match = matchService.getOne(matchId, true);
//		List<ScoreCard> allScoreCards = scoreCardService.findByMatch(matchId, false);
//		
//		for (String division : match.getDivisionsWithResults()) {
//			MatchResultData matchResultData = matchResultDataService.findByMatchAndDivision(matchId, division);
//			
//			// Generate CompetitorStatisticsLine instances for competitors
//			for (Competitor competitor : match.getCompetitors()) {
//				if (!competitor.getDivision().equals(division)) continue;
//				CompetitorStatistics line = new CompetitorStatistics(competitor, match);
//				double matchTime = 0.0;
//				int aHits = 0;
//				int cHits = 0;
//				int dHits = 0;
//				int misses = 0;
//				int proceduralPenalties = 0;
//				int additionalPenalties = 0;
//				int noShootHits = 0;
//				int sumOfPoints = 0;
//				
//				List<ScoreCard> scoreCards = getScoreCardsForCompetitor(allScoreCards, competitor.getId()); 
//				
//				// Exclude competitors with no score card data. Will not be shown at all in statistics listing.
//				if (scoreCards.size() == 0) continue; 
//				for (ScoreCard card : scoreCards) {
//					aHits += card.getaHits();
//					cHits += card.getcHits();
//					dHits += card.getdHits();
//					misses += card.getMisses();
//					proceduralPenalties += card.getProceduralPenalties();
//					additionalPenalties += card.getAdditionalPenalties();
//					noShootHits += card.getNoshootHits();
//					sumOfPoints += card.getPoints();
//					matchTime += card.getTime();
//				}
//				line.setaHits(aHits);
//				line.setcHits(cHits);
//				line.setdHits(dHits);
//				line.setMisses(misses);
//				line.setProceduralPenalties(proceduralPenalties);
//				line.setAdditionalPenalties(additionalPenalties);
//				line.setNoShootHits(noShootHits);
//				line.setSumOfPoints(sumOfPoints);
//				line.setMatchTime(matchTime);
//				
//				int totalShots = aHits + cHits + dHits + misses + noShootHits;
//				line.setaHitPercentage((double) aHits / (double) totalShots * 100.0);
//				
//				// Get following data from competitor's MatchResultDataLine instance: position within division results,
//				// total points and score percentage within division. 
//				MatchResultDataLine matchDataLine = getMatchResultDataLineForCompetitor(matchResultData, competitor.getId());
//				if (matchDataLine != null) {
//					line.setDivisionRank(matchDataLine.getRank());
//					line.setDivisionPoints(matchDataLine.getPoints());
//					line.setDivisionScorePercentage(matchDataLine.getScorePercentage());
//				}
//				competitorStatisticsList.add(line);
//			}
//		}
//		competitorStatisticsRepository.save(competitorStatisticsList);
	}
	
	@Transactional
	public void deleteByMatch(Long matchId) {
		competitorStatisticsRepository.deleteByMatch(matchId);
	}
	
	private List<ScoreCard> getScoreCardsForCompetitor(List<ScoreCard> cards, Long competitorId) {
		List<ScoreCard> resultList = new ArrayList<ScoreCard>();
		for (ScoreCard card : cards) {
			if (card.getCompetitorId().equals(competitorId)) resultList.add(card);
		}
		return resultList;
	}
	
	private MatchResultDataLine getMatchResultDataLineForCompetitor(MatchResultData data, Long competitorId) {
		for (MatchResultDataLine line : data.getDataLines()) if (line.getCompetitorId().equals(competitorId)) return line;
		return null;
	}
	
	private void fetchReferences(List<CompetitorStatistics> stats, Long matchId) {
		List<Competitor> competitors = competitorService.findByMatchAndDivision(matchId, Constants.COMBINED_DIVISION);
		for (CompetitorStatistics stat : stats) {
			stat.setCompetitor(getCompetitorForStatisticsLine(competitors, stat.getCompetitorId()));
		}
	}
	
	private Competitor getCompetitorForStatisticsLine(List<Competitor> competitors, Long competitorId) {
		for (Competitor comp : competitors) if (comp.getId().equals(competitorId)) return comp;
		return null;
	}
}
