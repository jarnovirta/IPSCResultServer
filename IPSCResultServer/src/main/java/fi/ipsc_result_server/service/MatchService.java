package fi.ipsc_result_server.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipsc_result_server.domain.Competitor;
import fi.ipsc_result_server.domain.Match;
import fi.ipsc_result_server.repository.CompetitorRepository;
import fi.ipsc_result_server.repository.MatchRepository;
import fi.ipsc_result_server.service.resultDataService.MatchResultDataService;

@Service
public class MatchService {
	@Autowired
	private MatchRepository matchRepository;
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	CompetitorRepository competitorRepository;
	
	@Autowired
	MatchResultDataService matchResultDataService;
	
	final static Logger logger = Logger.getLogger(MatchService.class);
	
	@Transactional
	public Match save(Match match) {
		if (match.getCompetitors() != null) {
			int competitorNumber = 1;
			for (Competitor competitor : match.getCompetitors()) {
				competitor.setShooterNumber(competitorNumber++);
			}
		}
		return entityManager.merge(match);
	}
	
	public List<Match> findAll() {
		return matchRepository.findAll();
	}
	
	public List<Match> getAdminPageMatchList() {
		try {
			String queryString = "SELECT NEW fi.ipsc_result_server.domain.Match(m.id, m.name, m.status) FROM Match m ORDER BY m.date DESC";
			TypedQuery<Match> query = entityManager.createQuery(queryString, Match.class);
			return query.getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	}
	public Match getOne(String id) {
		return matchRepository.getOne(id);
	}
	
	@Transactional
	public void delete(String matchId) {
		logger.info("Deleting result data");
		try {
			logger.info("Deleting result data");
			matchResultDataService.deleteResultDataForMatch(getOne(matchId));
			logger.info("Deleting match");
			String queryString = "DELETE FROM Match m WHERE m.id = :matchId";
			entityManager.createQuery(queryString).executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}
