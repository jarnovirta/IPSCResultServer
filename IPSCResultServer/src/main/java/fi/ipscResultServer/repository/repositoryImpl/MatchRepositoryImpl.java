package fi.ipscResultServer.repository.repositoryImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.repository.MatchRepository;
import fi.ipscResultServer.repository.springJPARepository.SpringJPAMatchRepository;

@Repository
public class MatchRepositoryImpl implements MatchRepository {
	
	@Autowired
	SpringJPAMatchRepository springJPAMatchRepository;
	
	@PersistenceContext
	EntityManager entityManager;
	
	public Match save(Match match) {
		return springJPAMatchRepository.save(match);
	}
	
	
	public List<Match> findAll() {
		return springJPAMatchRepository.findAll();
	}
	// Returns Match list with only necessary properties set for listing purposes
	// instead of full instances with a list of Stages etc.
	public List<Match> getAdminPageMatchList() {
		try {
			String queryString = "SELECT NEW fi.ipscResultServer.domain.Match(m.id, m.name, m.status) FROM Match m ORDER BY m.date DESC";
			TypedQuery<Match> query = entityManager.createQuery(queryString, Match.class);
			return query.getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	}
	
	public Match getOne(String id) {
		return springJPAMatchRepository.getOne(id);
	}
	
	public void delete(Match match) {
		springJPAMatchRepository.delete(match);
	}
}
