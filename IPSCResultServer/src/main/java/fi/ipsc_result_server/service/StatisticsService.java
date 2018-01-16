package fi.ipsc_result_server.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipsc_result_server.domain.Competitor;
import fi.ipsc_result_server.domain.IPSCDivision;
import fi.ipsc_result_server.domain.Match;
import fi.ipsc_result_server.domain.ScoreCard;
import fi.ipsc_result_server.domain.ResultData.CompetitorResultData;
import fi.ipsc_result_server.domain.ResultData.MatchResultDataLine;
import fi.ipsc_result_server.domain.statistics.CompetitorStatistics;
import fi.ipsc_result_server.domain.statistics.CompetitorStatisticsLine;

@Service
public class StatisticsService {
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	ResultDataService resultDataService;
	
	final static Logger logger = Logger.getLogger(StatisticsService.class);
	
	public CompetitorStatistics getCompetitorStatistics(String matchId, IPSCDivision division) {
		try {
			
			String queryString = "SELECT c FROM CompetitorStatistics c WHERE c.match.id = :matchId AND c.division = :division";
			TypedQuery<CompetitorStatistics> query = entityManager.createQuery(queryString, CompetitorStatistics.class);
			query.setParameter("matchId", matchId);
			query.setParameter("division", division);
			List<CompetitorStatistics> statisticsList = query.getResultList();
			
			if (statisticsList.size() > 0) {
				CompetitorStatistics statistics = statisticsList.get(0);
				Collections.sort(statistics.getStatisticsLines());
				return statistics;
			}
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Transactional
	public void generateCompetitorStatistics(Match match) {
		logger.info("Deleting old match statistics..");
		deleteStatisticsForMatch(match);	
				
		for (IPSCDivision division : IPSCDivision.values()) {
			logger.info("Generating match statistics for " + division);
			CompetitorStatistics statistics = new CompetitorStatistics(match, division);
			List<CompetitorStatisticsLine> lines = new ArrayList<CompetitorStatisticsLine>();
			
			for (Competitor competitor : match.getCompetitors()) {
				if (competitor.getDivision() != division) continue;
				CompetitorStatisticsLine line = new CompetitorStatisticsLine(competitor);
				double matchTime = 0.0;
				int aHits = 0;
				int cHits = 0;
				int dHits = 0;
				int misses = 0;
				int proceduralPenalties = 0;
				int noShootHits = 0;
				int sumOfPoints = 0;
				CompetitorResultData competitorResultData = resultDataService.getCompetitorResultData(competitor.getId(), match.getId());
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
				
				MatchResultDataLine matchDataLine = resultDataService.getMatchResultDataLineForCompetitor(competitor);
				if (matchDataLine != null) {
					line.setDivisionRank(matchDataLine.getRank());
					line.setDivisionPoints(matchDataLine.getPoints());
					line.setDivisionScorePercentage(matchDataLine.getScorePercentage());
				}
				lines.add(line);
			}
			if (lines.size() == 0) continue; 
			statistics.setStatisticsLines(lines);
			logger.info("Saving match statistics");
			entityManager.persist(statistics);
		}
	}
	
	@Transactional
	public void deleteStatisticsForMatch(Match match) {
		try {
			String queryString = "DELETE FROM CompetitorStatistics c WHERE c.match = :match";
			Query query = entityManager.createQuery(queryString);
			query.setParameter("match", match);
			query.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<IPSCDivision> getAvailableDivisionsForStatistics(String matchId) {
		try {
			String queryString = "SELECT DISTINCT(c.division) FROM CompetitorStatistics c WHERE c.match.id = :matchId";
			TypedQuery<IPSCDivision> query = entityManager.createQuery(queryString, IPSCDivision.class); 
			query.setParameter("matchId", matchId);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
