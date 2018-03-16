package fi.ipscResultServer.repository.repositoryImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.User;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.repository.MatchRepository;
import fi.ipscResultServer.repository.springJPARepository.SpringJPAMatchRepository;

@Repository
public class MatchRepositoryImpl implements MatchRepository {
	
	@Autowired
	SpringJPAMatchRepository springJPAMatchRepository;
	
	@PersistenceContext
	EntityManager entityManager;
	
	final static Logger logger = Logger.getLogger(MatchRepositoryImpl.class);
	
	public Match save(Match match) throws DatabaseException {
		try {
			return springJPAMatchRepository.save(match);
		}
		catch (Exception e) {
			logger.error(e);
			throw new DatabaseException(e);
		}
	}
	
	
	public List<Match> findAll() {
		return springJPAMatchRepository.findAll();
		
	}
	
	public List<Match> getAdminPageMatchListByUser(User user) {
		try {
			String queryString = "SELECT NEW fi.ipscResultServer.domain.Match(m.id, m.name, m.status, m.user, m.uploadedByAdmin) FROM Match m "
					+ "WHERE m.user = :user ORDER BY m.date DESC";
			TypedQuery<Match> query = entityManager.createQuery(queryString, Match.class);
			return query.setParameter("user", user).getResultList();
		} 
		catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	// Returns Match list with only necessary properties set for listing purposes
	// instead of full instances with a list of Stages etc.
	public List<Match> getAdminPageMatchList() {
		try {
			String queryString = "SELECT NEW fi.ipscResultServer.domain.Match(m.id, m.name, m.status, m.user, m.uploadedByAdmin) FROM Match m ORDER BY m.date DESC";
			TypedQuery<Match> query = entityManager.createQuery(queryString, Match.class);
			return query.getResultList();
			} catch (Exception e) {
				logger.error(e);
			}
			return null;
	}
	
	public Match getOne(String id) throws DatabaseException {
		try {
			return springJPAMatchRepository.getOne(id);
		}
		catch (Exception e) {
			logger.error(e);
			throw new DatabaseException(e);
		}
	}
	
	public void delete(Match match) throws DatabaseException {
		try {
			springJPAMatchRepository.delete(match);
		}
		catch (Exception e) {
			logger.error(e);
			throw new DatabaseException(e);
		}
	}
}
