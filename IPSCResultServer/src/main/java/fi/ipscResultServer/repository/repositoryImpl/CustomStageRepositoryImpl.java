package fi.ipscResultServer.repository.repositoryImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.repository.StageRepository;
import fi.ipscResultServer.repository.springJPARepository.SpringJPAStageRepository;
import fi.ipscResultServer.service.practiScoreDataService.MatchScoreService;

@Repository
public class CustomStageRepositoryImpl implements StageRepository {

	@Autowired
	private SpringJPAStageRepository springStageRepository;
	
	@PersistenceContext
	EntityManager entityManager;
	
	final static Logger logger = Logger.getLogger(MatchScoreService.class);
	
	public Stage getOne(Long id) {
		return springStageRepository.getOne(id);
	}
	
	public Stage findByPractiScoreId(String practiScoreMatchId, String practiScoreStageId) {
		try {
			String queryString = "SELECT s FROM Stage s WHERE s.match.practiScoreId = :practiScoreMatchId"
					+ " AND s.practiScoreId = :practiScoreStageId";
			TypedQuery<Stage> query = entityManager.createQuery(queryString, Stage.class);
			query.setParameter("practiScoreMatchId", practiScoreMatchId);
			query.setParameter("practiScoreStageId", practiScoreStageId);
			return query.getSingleResult();
		} 
		catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
}
