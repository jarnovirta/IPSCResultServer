package fi.ipsc_result_server.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipsc_result_server.domain.MatchScore;

@Service
public class MatchScoreService {
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Transactional
	public MatchScore save(MatchScore matchScore) {
		return entityManager.merge(matchScore);
	}
}
