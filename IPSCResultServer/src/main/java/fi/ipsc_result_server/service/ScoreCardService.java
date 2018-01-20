package fi.ipsc_result_server.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipsc_result_server.domain.IPSCDivision;
import fi.ipsc_result_server.domain.Match;
import fi.ipsc_result_server.domain.ScoreCard;
import fi.ipsc_result_server.domain.Stage;
import fi.ipsc_result_server.repository.ScoreCardRepository;

@Service
public class ScoreCardService {
	@Autowired
	ScoreCardRepository scoreCardRepository;
		
	@PersistenceContext
	EntityManager entityManager;
	
	final static Logger logger = Logger.getLogger(ScoreCardService.class);
	
	@Transactional
	public ScoreCard save(ScoreCard scoreCard) {
		return scoreCardRepository.save(scoreCard);
	}
	
	public ScoreCard findByCompetitorAndStage(String competitorId, String stageId) {
		try {
			String queryString = "SELECT s FROM ScoreCard s WHERE s.competitorId = :competitorId AND s.stageId = :stageId";
			TypedQuery<ScoreCard> query = entityManager.createQuery(queryString, ScoreCard.class);
			query.setParameter("competitorId", competitorId);
			query.setParameter("stageId", stageId);
			List<ScoreCard> scoreCards = query.getResultList();
			if (scoreCards != null && scoreCards.size() > 0) return scoreCards.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Transactional
	public void deleteInBatch(List<ScoreCard> scoreCards) {
		scoreCardRepository.deleteInBatch(scoreCards);
	}
	
	public List<ScoreCard> findByStageAndDivision(String stageId, IPSCDivision division) {
		try {
			String queryString = "SELECT s FROM ScoreCard s WHERE s.stage.id = :stageId AND s.competitor.division = :division";
			TypedQuery<ScoreCard> query = entityManager.createQuery(queryString, ScoreCard.class);
			query.setParameter("stageId", stageId);
			query.setParameter("division", division);
			return query.getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<ScoreCard> findByStage(String stageId) {
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
	
	// Delete ScoreCard. Needs to use properties other than id because ScoreCard data from PractiScore does not 
	// include id.
	@Transactional
	public void delete(ScoreCard scoreCard) {
		try {
			String queryString = "DELETE FROM ScoreCard s WHERE s.stage.match.id = :matchId AND s.stage.id = :stageId "
					+ "AND s.competitor.id = :competitorId AND s.modified <= :modified";
			Query query = entityManager.createQuery(queryString);
			query.setParameter("matchId", scoreCard.getStage().getMatch().getId());
			query.setParameter("stageId", scoreCard.getStage().getId());
			query.setParameter("competitorId", scoreCard.getCompetitor().getId());
			query.setParameter("modified", scoreCard.getModified(), TemporalType.TIMESTAMP);
			query.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Transactional
	public void deleteByMatch(Match match) {
		try {
			// Set reference to stage to null
			for (Stage stage : match.getStages()) {
				List<ScoreCard> cards = findByStage(stage.getId());
				if (cards != null) {
					for (ScoreCard card : cards) {
						card.setStage(null);
					}
				}
			}
			String queryString = "DELETE FROM ScoreCard s WHERE s.stage.match = :match";
			entityManager.createQuery(queryString).setParameter("match", match).executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
