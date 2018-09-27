package fi.ipscResultServer.repository.eclipseLinkRepository.impl;

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
import fi.ipscResultServer.repository.eclipseLinkRepository.MatchResultDataRepository;
import fi.ipscResultServer.repository.eclipseLinkRepository.springJPARepository.SpringJPAMatchResultDataLineRepository;
import fi.ipscResultServer.repository.eclipseLinkRepository.springJPARepository.SpringJPAMatchResultDataRepository;

@Repository
public class MatchResultDataRepositoryImpl implements MatchResultDataRepository {
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	SpringJPAMatchResultDataRepository springJPAMatchResultDataRepository;
	
	@Autowired
	SpringJPAMatchResultDataLineRepository springJPAMatchResultDataLineRepository;
	
	final static Logger logger = Logger.getLogger(MatchResultDataRepositoryImpl.class);
	
	public MatchResultData findByMatchAndDivision(Long matchId, String division) throws DatabaseException {
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
	
	public List<MatchResultData> find(Long matchId) throws DatabaseException {
		try {
			String queryString = "SELECT m FROM MatchResultData m WHERE m.match.id = :matchId";
			TypedQuery<MatchResultData> query = entityManager.createQuery(queryString, MatchResultData.class); 
			query.setParameter("matchId", matchId);
			return query.getResultList();
		}
		catch (Exception e) {
			logger.error(e);
			throw new DatabaseException(e);
		}

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
	
	public void deleteInBatch(List<MatchResultData> matchResultData) throws DatabaseException {
		try {
			List<MatchResultDataLine> lines = new ArrayList<MatchResultDataLine>();
			for (MatchResultData data : matchResultData) {
				lines.addAll(data.getDataLines());
			}
			springJPAMatchResultDataLineRepository.deleteInBatch(lines);
			springJPAMatchResultDataRepository.deleteInBatch(matchResultData);
		}
		catch (Exception e) {
			logger.error(e);
			throw new DatabaseException(e);
		}
	}
}
