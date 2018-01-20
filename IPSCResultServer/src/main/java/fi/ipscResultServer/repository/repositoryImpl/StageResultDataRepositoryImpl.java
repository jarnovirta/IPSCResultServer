package fi.ipscResultServer.repository.repositoryImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.IPSCDivision;
import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.domain.ResultData.StageResultData;
import fi.ipscResultServer.domain.ResultData.StageResultDataLine;
import fi.ipscResultServer.repository.StageResultDataRepository;
import fi.ipscResultServer.repository.springJPARepository.SpringJPAStageResultDataRepository;

@Repository
public class StageResultDataRepositoryImpl implements StageResultDataRepository {
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	SpringJPAStageResultDataRepository springJPAStageResultDataRepository;
	
	public StageResultData findByStageAndDivision(String stageId, IPSCDivision division) {
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		return resultData;
	}
	
	public List<StageResultData> findByStage(Stage stage) {
		try {
			String queryString = "SELECT s FROM StageResultData s WHERE s.stage = :stage";
			TypedQuery<StageResultData> query = entityManager.createQuery(queryString, StageResultData.class); 
			query.setParameter("stage", stage);
			return query.getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<StageResultDataLine> findStageResultDataLinesByCompetitor(Competitor competitor) {
		try {
			String queryString = "SELECT s FROM StageResultDataLine s WHERE s.competitor = :competitor AND s.stageResultData.division = :competitorDivision";
			TypedQuery<StageResultDataLine> query = entityManager.createQuery(queryString, StageResultDataLine.class);
			query.setParameter("competitor", competitor);
			query.setParameter("competitorDivision", competitor.getDivision());
			return query.getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}

	public void delete(StageResultData data) {
		springJPAStageResultDataRepository.delete(data);
	}
	
	public StageResultData save(StageResultData stageResultData) {
		return springJPAStageResultDataRepository.save(stageResultData);
	}
}
