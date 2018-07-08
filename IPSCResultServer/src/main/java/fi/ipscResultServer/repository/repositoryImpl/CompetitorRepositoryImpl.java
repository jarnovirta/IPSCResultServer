package fi.ipscResultServer.repository.repositoryImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.repository.CompetitorRepository;
import fi.ipscResultServer.repository.springJPARepository.SpringJPACompetitorRepository;

@Repository
public class CompetitorRepositoryImpl implements CompetitorRepository {

	@Autowired
	SpringJPACompetitorRepository springJPACompetitorRepository;
	
	@PersistenceContext
	EntityManager entityManager;
	
	final static Logger logger = Logger.getLogger(MatchRepositoryImpl.class);
	
	public List<Competitor> findByMatch(Long matchId) {
		try {
			String queryString = "SELECT m.competitors FROM Match m WHERE m.id = :matchId";
			TypedQuery<Competitor> query = entityManager.createQuery(queryString, Competitor.class);
			query.setParameter("matchId", matchId);
			List<Competitor> comps = query.getResultList();
			System.out.println("Repo found " + comps.size());
			System.out.println("Repo found " + comps.size());
			return query.getResultList();
			} catch (Exception e) {
				logger.error(e);
			}
			return null;
	}
	public Competitor getOne(Long id) {
		try {
			return springJPACompetitorRepository.getOne(id);
		}
		catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	public Competitor findByPractiScoreReferences(String practiScoreMatchId, String practiScoreCompetitorId) {
		try {
			String queryString = "SELECT c FROM Competitor c WHERE c.match.practiScoreId = :practiScoreMatchId"
					+ " AND c.practiScoreId = :practiScoreCompetitorId";
			TypedQuery<Competitor> query = entityManager.createQuery(queryString, Competitor.class);
			query.setParameter("practiScoreMatchId", practiScoreMatchId);
			query.setParameter("practiScoreCompetitorId", practiScoreCompetitorId);
			List<Competitor> competitors = query.getResultList();
			if (competitors.size() > 0) return competitors.get(0);
			return null;
			} catch (Exception e) {
				logger.error(e);
			}
			return null;
	}
	public Competitor save(Competitor competitor) {
		try {
			return springJPACompetitorRepository.save(competitor);
		}
		catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
}
