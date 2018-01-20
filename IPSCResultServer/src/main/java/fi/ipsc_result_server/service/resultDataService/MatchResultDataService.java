package fi.ipsc_result_server.service.resultDataService;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipsc_result_server.domain.Competitor;
import fi.ipsc_result_server.domain.IPSCDivision;
import fi.ipsc_result_server.domain.Match;
import fi.ipsc_result_server.domain.ResultData.MatchResultData;
import fi.ipsc_result_server.domain.ResultData.MatchResultDataLine;

@Service
public class MatchResultDataService {
	@PersistenceContext
	EntityManager entityManager;
		
	public MatchResultData findByMatchAndDivision(String matchId, IPSCDivision division) {
		try {
			String queryString = "SELECT m FROM MatchResultData m WHERE m.match.id = :matchId AND m.division = :division";
			TypedQuery<MatchResultData> query = entityManager.createQuery(queryString, MatchResultData.class);
			query.setParameter("matchId", matchId);
			query.setParameter("division", division);
			List<MatchResultData> resultList = query.getResultList();
			if (resultList != null && resultList.size() > 0) {
				return resultList.get(0);
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	}
	
	public List<MatchResultData> findByMatch(String matchId) {
		try {
			String queryString = "SELECT m FROM MatchResultData m WHERE m.match.id = :matchId";
			TypedQuery<MatchResultData> query = entityManager.createQuery(queryString, MatchResultData.class); 
			query.setParameter("matchId", matchId);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public MatchResultDataLine findMatchResultDataLinesByCompetitor(Competitor competitor) {
		try {
			String queryString = "SELECT m FROM MatchResultDataLine m WHERE m.competitor = :competitor AND m.matchResultData.division = :competitorDivision";
			TypedQuery<MatchResultDataLine> query = entityManager.createQuery(queryString, MatchResultDataLine.class);
			query.setParameter("competitor", competitor);
			query.setParameter("competitorDivision", competitor.getDivision());
			List<MatchResultDataLine> lines = query.getResultList();
			if (lines != null && lines.size() > 0) return lines.get(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}
	
	@Transactional
	public MatchResultData save(MatchResultData matchResultData) {
		entityManager.persist(matchResultData);
		return matchResultData;
	}
	@Transactional
	public void deleteByMatch(Match match) {
		List<MatchResultData> oldMatchResultData = findByMatch(match.getId());
		if (oldMatchResultData != null) {
			for (MatchResultData matchResultData : oldMatchResultData) {
				entityManager.remove(matchResultData);
			}
		}
	}
}
