package fi.ipscResultServer.repository.eclipseLinkRepository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
import fi.ipscResultServer.repository.eclipseLinkRepository.ScoreCardRepository;
import fi.ipscResultServer.repository.eclipseLinkRepository.springJPARepository.SpringJPAScoreCardRepository;

@Repository
public class ScoreCardRepositoryImpl implements ScoreCardRepository {
	
	@Autowired
	SpringJPAScoreCardRepository springJPAScoreCardRepository;
	
	@PersistenceContext
	EntityManager entityManager;

	final static Logger logger = Logger.getLogger(ScoreCardRepositoryImpl.class);
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/ipsc_result_server?useUnicode=true&amp;characterEncoding=utf8";

	static final String USER = "root";
	static final String PASS = "";
	
	public void save(List<ScoreCard> scoreCards) throws DatabaseException {
		   Connection conn = null;
		   Statement stmt = null;
		   
		   try {
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      //STEP 4: Execute a query
		      System.out.println("Creating statement...");
		      stmt = conn.createStatement();
		      String sql;
		      sql = "INSERT INTO scorecard (ahits, time, hitfactor, competitorpractiscoreid, competitor_id, stage_id, modified"
		      		+ ", points, poppermisses, popperhits, poppernoshoothits, poppernonpenaltymisses)  VALUES ";
		      int counter = 1;
		      int feikkiid = 123856;
		      

		      for (ScoreCard card : scoreCards) {
		    	  if (counter++ > 1) sql += ", ";
		    	  sql += "(" + String.valueOf(card.getaHits())
		    			  + ", " + String.valueOf(card.getTime())
		    			  + ", " + String.valueOf(card.getHitFactor())
		    			  + ", '" + card.getCompetitorPractiScoreId() + "'"
		    			  + ", " + String.valueOf(card.getCompetitor().getId())
		    			  + ", " + String.valueOf(card.getStage().getId())
		    			  + ", '2018-07-28 12:44:09'"
		    			  + ", 228, 1, 4, 0, 0)";

		      }
		      sql += ";";
		      System.out.println("Statement: " + sql);
		      stmt.executeUpdate(sql);

		      stmt.close();
		      conn.close();
		   }
			catch (Exception e) {
				logger.error(e);
				throw new DatabaseException(e);
			}
		   finally {
			      try {
			         if(stmt!=null)
			            stmt.close();
			      } 
			      catch(SQLException se2){ }
			      
			      try {
			         if(conn!=null) conn.close();
			      }
			      catch(SQLException se){
			         se.printStackTrace();
			      }
		   }
		   System.out.println("Goodbye!");
}
	
	
	
	
	public List<ScoreCard> findByStageAndDivision(Long stageId, String division) 
			throws DatabaseException {
		try {
			String queryString = "SELECT s FROM ScoreCard s WHERE s.stage.id = :stageId AND s.competitor.division = :division ORDER BY s.hitFactor DESC";
			TypedQuery<ScoreCard> query = entityManager.createQuery(queryString, ScoreCard.class);
			query.setParameter("stageId", stageId);
			query.setParameter("division", division);
			return query.getResultList();
			
		} catch (Exception e) {
			logger.error(e);
			throw new DatabaseException(e);
		}
	}
	
	public List<ScoreCard> findByStage(Long stageId) throws DatabaseException {
		try {
			String queryString = "SELECT s FROM ScoreCard s WHERE s.stage.id = :stageId ORDER BY s.hitFactor DESC";
			TypedQuery<ScoreCard> query = entityManager.createQuery(queryString, ScoreCard.class);
			query.setParameter("stageId", stageId);
			return query.getResultList();
			
		} catch (Exception e) {
			logger.error(e);
			throw new DatabaseException(e);
		}
	}
	
	public List<ScoreCard> findByStage(String practiScoreId) throws DatabaseException {
		try {
			String queryString = "SELECT s FROM ScoreCard s WHERE s.stage.practiScoreId = :stageId ORDER BY s.hitFactor DESC";
			TypedQuery<ScoreCard> query = entityManager.createQuery(queryString, ScoreCard.class);
			query.setParameter("stageId", practiScoreId);
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
					+ "AND s.stage.match.practiScoreId = :matchPractiScoreId ORDER BY s.hitFactor DESC";
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
