package fi.ipscResultServer.repository.repositoryImpl;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fi.ipscResultServer.domain.IPSCDivision;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.statistics.CompetitorStatistics;
import fi.ipscResultServer.domain.statistics.CompetitorStatisticsLine;
import fi.ipscResultServer.repository.CompetitorStatisticsRepository;
import fi.ipscResultServer.repository.springJPARepository.SpringJPACompetitorStatisticsRepository;

@Repository
public class CompetitorStatisticsRepositoryImpl implements CompetitorStatisticsRepository {
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	SpringJPACompetitorStatisticsRepository springJPACompetitorStatisticsRepository;
	
	public CompetitorStatistics findCompetitorStatisticsByMatchAndDivision(String matchId, IPSCDivision division) {
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
	
	public CompetitorStatistics save(CompetitorStatistics competitorStatistics) {
		return springJPACompetitorStatisticsRepository.save(competitorStatistics);
	}
	
	public void deleteByMatch(Match match) {
		try {
			// Set entity references to null in CompetitorStatistics to be deleted
			String queryString = "SELECT c FROM CompetitorStatistics c WHERE c.match = :match";
			TypedQuery<CompetitorStatistics> query = entityManager.createQuery(queryString, CompetitorStatistics.class);
			List<CompetitorStatistics> statistics = query.setParameter("match", match).getResultList();
			for (CompetitorStatistics stats : statistics) {
				stats.setMatch(null);
				for (CompetitorStatisticsLine line : stats.getStatisticsLines()) {
					line.setCompetitor(null);
				}
				entityManager.remove(stats);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
