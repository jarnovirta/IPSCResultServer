package fi.ipscResultServer.repository.repositoryImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.repository.ScoreCardRepository;
import fi.ipscResultServer.repository.springJPARepository.SpringJPAScoreCardRepository;

@Repository
public class ScoreCardRepositoryImpl implements ScoreCardRepository {
	
	@Autowired
	SpringJPAScoreCardRepository springJPAScoreCardRepository;
	
	@PersistenceContext
	EntityManager entityManager;

	final static Logger logger = Logger.getLogger(ScoreCardRepositoryImpl.class);
	
	public ScoreCard save(ScoreCard scoreCard) throws DatabaseException {
		try {
			return springJPAScoreCardRepository.save(scoreCard);
		}
		catch (Exception e) {
			logger.error(e);
			throw new DatabaseException(e);
		}
	}
	
	
	public List<ScoreCard> findByStageAndDivision(Stage stage, String division) 
			throws DatabaseException {
		try {
			String queryString = "SELECT s FROM ScoreCard s WHERE s.stage = :stage AND s.competitor.division = :division";
			TypedQuery<ScoreCard> query = entityManager.createQuery(queryString, ScoreCard.class);
			query.setParameter("stage", stage);
			query.setParameter("division", division);
			return query.getResultList();
			
		} catch (Exception e) {
			logger.error(e);
			throw new DatabaseException(e);
		}
	}
	
	public List<ScoreCard> findByStage(Long stageId) throws DatabaseException {
		try {
			String queryString = "SELECT s FROM ScoreCard s WHERE s.stage.id = :stageId";
			TypedQuery<ScoreCard> query = entityManager.createQuery(queryString, ScoreCard.class);
			query.setParameter("stageId", stageId);
			return query.getResultList();
			
		} catch (Exception e) {
			logger.error(e);
			throw new DatabaseException(e);
		}
	}
	
	public ScoreCard findByCompetitorAndStage(String competitorId, String stageId) 
			throws DatabaseException {
		try {
			String queryString = "SELECT s FROM ScoreCard s WHERE s.competitorId = :competitorId AND s.stageId = :stageId";
			TypedQuery<ScoreCard> query = entityManager.createQuery(queryString, ScoreCard.class);
			query.setParameter("competitorId", competitorId);
			query.setParameter("stageId", stageId);
			List<ScoreCard> scoreCards = query.getResultList();
			if (scoreCards != null && scoreCards.size() > 0) return scoreCards.get(0);
			return null;
		} catch (Exception e) {
			logger.error(e);
			throw new DatabaseException(e);
		}
	}
	
	public List<ScoreCard> findByCompetitorAndMatchPractiScoreIds(String competitorPractiScoreId, String matchPractiScoreId) 
			throws DatabaseException {
		try {
			String queryString = "SELECT s FROM ScoreCard s WHERE s.competitor.practiScoreId = :competitorPractiScoreId "
					+ "AND s.stage.match.practiScoreId = :matchPractiScoreId";
			TypedQuery<ScoreCard> query = entityManager.createQuery(queryString, ScoreCard.class);
			query.setParameter("competitorPractiScoreId", competitorPractiScoreId);
			query.setParameter("matchPractiScoreId", matchPractiScoreId);
			return query.getResultList();
			
		} catch (Exception e) {
			logger.error(e);
			throw new DatabaseException(e);
		}
	}
	public void deleteInBatch(List<ScoreCard> scoreCards) throws DatabaseException {
		try {
			
			springJPAScoreCardRepository.deleteInBatch(scoreCards);
		} catch (Exception e) {
			logger.error(e);
			throw new DatabaseException(e);
		}
	}

	
	public void delete(ScoreCard scoreCard) throws DatabaseException {
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
			logger.error(e);
			throw new DatabaseException(e);
		}
	}
	
	public void deleteByMatch(Match match) throws DatabaseException {
		try {
			// Set reference to stage to null
			for (Stage stage : match.getStages()) {
				List<ScoreCard> cards = findByStage(stage.getId());
				if (cards != null) {
					deleteInBatch(cards);
				}
			}
		} catch (Exception e) {
			logger.error(e);
			throw new DatabaseException(e);
		}
	}


	
}
