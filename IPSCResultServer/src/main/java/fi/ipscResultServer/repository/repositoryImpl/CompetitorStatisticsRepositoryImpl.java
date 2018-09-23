package fi.ipscResultServer.repository.repositoryImpl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fi.ipscResultServer.domain.statistics.CompetitorStatistics;
import fi.ipscResultServer.repository.CompetitorStatisticsRepository;
import fi.ipscResultServer.repository.springJPARepository.SpringJPACompetitorStatisticsRepository;

@Repository
public class CompetitorStatisticsRepositoryImpl implements CompetitorStatisticsRepository {
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	SpringJPACompetitorStatisticsRepository springJPACompetitorStatisticsRepository;
	
	final static Logger logger = Logger.getLogger(CompetitorStatisticsRepositoryImpl.class);
	
	public List<CompetitorStatistics> findCompetitorStatisticsByMatchAndDivision(Long matchId, String division) {
		try {
			String queryString = "SELECT c FROM CompetitorStatistics c WHERE c.match.id = :matchId AND c.competitor.division = :division";
			TypedQuery<CompetitorStatistics> query = entityManager.createQuery(queryString, CompetitorStatistics.class);
			query.setParameter("matchId", matchId);
			query.setParameter("division", division);
			List<CompetitorStatistics> statisticsList = query.getResultList();
			Collections.sort(statisticsList);
			return statisticsList;
						
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	
	public List<CompetitorStatistics> findCompetitorStatisticsByMatch(Long matchId) {
		try {
			String queryString = "SELECT c FROM CompetitorStatistics c WHERE c.match.id = :matchId";
			TypedQuery<CompetitorStatistics> query = entityManager.createQuery(queryString, CompetitorStatistics.class);
			query.setParameter("matchId", matchId);
			List<CompetitorStatistics> statisticsList = query.getResultList();
			Collections.sort(statisticsList);
			return statisticsList;
						
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	
	public CompetitorStatistics save(CompetitorStatistics competitorStatistics) {
		try {
			return springJPACompetitorStatisticsRepository.save(competitorStatistics);
		}
		catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	public void deleteInBatch(List<CompetitorStatistics> statistics) {
		try {
			springJPACompetitorStatisticsRepository.deleteInBatch(statistics);
		} catch (Exception e) {
			logger.error(e);
		}
	}
	public void deleteByMatch(Long matchId) {
		try {
			// Set entity references to null in CompetitorStatistics to be deleted
			String queryString = "SELECT c FROM CompetitorStatistics c WHERE c.match.id = :matchId";
			TypedQuery<CompetitorStatistics> query = entityManager.createQuery(queryString, CompetitorStatistics.class);
			List<CompetitorStatistics> statistics = query.setParameter("matchId", matchId).getResultList();
			for (CompetitorStatistics stats : statistics) {
				stats.setMatch(null);
				stats.setCompetitor(null);
			}
			deleteInBatch(statistics);
		} catch (Exception e) {
			logger.error(e);
		}
	}
}
