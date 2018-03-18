package fi.ipscResultServer.repository.repositoryImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.resultData.MatchResultData;
import fi.ipscResultServer.domain.resultData.MatchResultDataLine;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.repository.MatchResultDataRepository;
import fi.ipscResultServer.repository.springJPARepository.SpringJPAMatchResultDataRepository;

@Repository
public class MatchResultDataRepositoryImpl implements MatchResultDataRepository {
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	SpringJPAMatchResultDataRepository springJPAMatchResultDataRepository;
	
	final static Logger logger = Logger.getLogger(MatchResultDataRepositoryImpl.class);
	
	public MatchResultData findByMatchAndDivision(String matchId, String division) throws DatabaseException {
		try {
			String queryString = "SELECT m FROM MatchResultData m WHERE m.match.id = :matchId AND m.division = :division";
			TypedQuery<MatchResultData> query = entityManager.createQuery(queryString, MatchResultData.class);
			query.setParameter("matchId", matchId);
			query.setParameter("division", division);
			List<MatchResultData> resultList = query.getResultList();
			if (resultList != null && resultList.size() > 0) {
				return resultList.get(0);
			}
			return null;
		}
		catch (Exception e) {
			logger.error(e);
			throw new DatabaseException(e);
		}
		
	}
	
	public MatchResultData findByMatch(String matchId) throws DatabaseException {
		List<MatchResultData> resultList = new ArrayList<MatchResultData>();
		try {
			String queryString = "SELECT m FROM MatchResultData m WHERE m.match.id = :matchId";
			TypedQuery<MatchResultData> query = entityManager.createQuery(queryString, MatchResultData.class); 
			query.setParameter("matchId", matchId);
			resultList = query.getResultList();
			if (resultList.size() > 0) return resultList.get(0);
		}
		catch (Exception e) {
			logger.error(e);
			throw new DatabaseException(e);
		}
		return null;
	}
		
	public MatchResultDataLine findMatchResultDataLinesByCompetitor(Competitor competitor) throws DatabaseException {
		try {
			String queryString = "SELECT m FROM MatchResultDataLine m WHERE m.competitor = :competitor AND m.matchResultData.division = :competitorDivision";
			TypedQuery<MatchResultDataLine> query = entityManager.createQuery(queryString, MatchResultDataLine.class);
			query.setParameter("competitor", competitor);
			query.setParameter("competitorDivision", competitor.getDivision());
			List<MatchResultDataLine> lines = query.getResultList();
			if (lines != null && lines.size() > 0) return lines.get(0);
			return null;
			} 
		catch (Exception e) {
			logger.error(e);
			throw new DatabaseException(e);
		}
	}
	
	public MatchResultData save(MatchResultData matchResultData) throws DatabaseException {
		try {
			return springJPAMatchResultDataRepository.save(matchResultData);
		}
		catch (Exception e) {
			logger.error(e);
			throw new DatabaseException(e);
		}		
	}

	public void delete(MatchResultData matchResultData) throws DatabaseException {
		try {
			springJPAMatchResultDataRepository.delete(matchResultData);
		}
		catch (Exception e) {
			logger.error(e);
			throw new DatabaseException(e);
		}
	}
}
