package fi.ipsc_result_server.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipsc_result_server.domain.ScoreCard;
import fi.ipsc_result_server.repository.ScoreCardRepository;

@Service
public class ScoreCardService {
	@Autowired
	ScoreCardRepository scoreCardRepository;
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Transactional
	public ScoreCard save(ScoreCard scoreCard) {
		return scoreCardRepository.save(scoreCard);
	}
	
	public List<ScoreCard> findCompetitorScoreCardsForStage(String competitorId, String stageId) {
		try {
			String queryString = "SELECT s FROM ScoreCard s WHERE s.competitorId = :competitorId AND s.stageId = :stageId";
			TypedQuery<ScoreCard> query = entityManager.createQuery(queryString, ScoreCard.class);
			query.setParameter("competitorId", competitorId);
			query.setParameter("stageId", stageId);
			return query.getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Transactional
	public void deleteInBatch(List<ScoreCard> scoreCards) {
		scoreCardRepository.deleteInBatch(scoreCards);
	}
	
	public List<ScoreCard> findScoreCardsByStage(String stageId) {
		try {
			String queryString = "SELECT s FROM ScoreCard s WHERE s.stage.id = :stageId";
			TypedQuery<ScoreCard> query = entityManager.createQuery(queryString, ScoreCard.class);
			query.setParameter("stageId", stageId);
			return query.getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
