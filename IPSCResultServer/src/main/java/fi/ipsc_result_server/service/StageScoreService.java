package fi.ipsc_result_server.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import fi.ipsc_result_server.domain.StageScore;

@Service
public class StageScoreService {
	
	@PersistenceContext
	EntityManager entityManager;
	
	public List<StageScore> findStageScoresForCompetitor(String matchId, String competitorId) {
		List<StageScore> stageScores = null;
		
		return stageScores;
	}
	
}
