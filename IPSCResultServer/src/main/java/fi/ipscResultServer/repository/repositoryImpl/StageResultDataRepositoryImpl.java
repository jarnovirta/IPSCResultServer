package fi.ipscResultServer.repository.repositoryImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.domain.resultData.StageResultData;
import fi.ipscResultServer.domain.resultData.StageResultDataLine;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.repository.StageResultDataRepository;
import fi.ipscResultServer.repository.springJPARepository.SpringJPAStageResultDataRepository;

@Repository
public class StageResultDataRepositoryImpl implements StageResultDataRepository {
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	SpringJPAStageResultDataRepository springJPAStageResultDataRepository;
	
	final static Logger logger = Logger.getLogger(StageResultDataRepositoryImpl.class);
	
	public StageResultData findByStageAndDivision(String stageId, String division) 
			throws DatabaseException {
		StageResultData resultData = new StageResultData();
		try {
			String queryString = "SELECT s FROM StageResultData s WHERE s.stage.id = :stageId AND s.division = :division";
			TypedQuery<StageResultData> query = entityManager.createQuery(queryString, StageResultData.class);
			query.setParameter("stageId", stageId);
			query.setParameter("division", division);
			List<StageResultData> resultList = query.getResultList();
			if (resultList != null && resultList.size() > 0) {
				resultData = resultList.get(0);
			}
		}
		catch (Exception e) {
			logger.error(e);
			throw new DatabaseException(e);
		}
		return resultData;
	}
	
	public List<StageResultData> findByStage(Stage stage) throws DatabaseException {
		try {
			String queryString = "SELECT s FROM StageResultData s WHERE s.stage = :stage";
			TypedQuery<StageResultData> query = entityManager.createQuery(queryString, StageResultData.class); 
			query.setParameter("stage", stage);
			return query.getResultList();
			
		}
		catch (Exception e) {
			logger.error(e);
			throw new DatabaseException(e);
		}
	}
	
	public List<StageResultDataLine> findStageResultDataLinesByCompetitor(Competitor competitor) 
			throws DatabaseException {
		try {
			String queryString = "SELECT s FROM StageResultDataLine s WHERE s.competitor = :competitor AND s.stageResultData.division = :competitorDivision";
			TypedQuery<StageResultDataLine> query = entityManager.createQuery(queryString, StageResultDataLine.class);
			query.setParameter("competitor", competitor);
			query.setParameter("competitorDivision", competitor.getDivision());
			return query.getResultList();
		}
		catch (Exception e) {
			logger.error(e);
			throw new DatabaseException(e);
		}
	}

	public void delete(StageResultData data) throws DatabaseException {
		try {
			springJPAStageResultDataRepository.delete(data);
		}
		catch (Exception e) {
			logger.error(e);
			throw new DatabaseException(e);
		}		
	}
	
	public StageResultData save(StageResultData stageResultData) throws DatabaseException {
		try {
			return springJPAStageResultDataRepository.save(stageResultData);
		}
		catch (Exception e) {
			logger.error(e);
			throw new DatabaseException(e);
		}
	}
}
