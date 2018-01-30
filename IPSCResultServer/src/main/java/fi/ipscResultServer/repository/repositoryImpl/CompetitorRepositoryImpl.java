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
	
	public List<Competitor> findByMatch(String matchId) {
		try {
			String queryString = "SELECT m.competitors FROM Match m WHERE m.id = :matchId";
			TypedQuery<Competitor> query = entityManager.createQuery(queryString, Competitor.class);
			query.setParameter("matchId", matchId);
			return query.getResultList();
			} catch (Exception e) {
				logger.error(e);
			}
			return null;
	}
	public Competitor getOne(String id) {
		return springJPACompetitorRepository.getOne(id);
	}
	
	public Competitor save(Competitor competitor) {
		return springJPACompetitorRepository.save(competitor);
	}
}
