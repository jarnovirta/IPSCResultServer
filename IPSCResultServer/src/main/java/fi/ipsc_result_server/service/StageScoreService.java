package fi.ipsc_result_server.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipsc_result_server.domain.ScoreCard;
import fi.ipsc_result_server.domain.Stage;
import fi.ipsc_result_server.domain.StageScore;
import fi.ipsc_result_server.domain.ResultData.StageResultData;
import fi.ipsc_result_server.domain.ResultData.StageResultDataLine;
import fi.ipsc_result_server.repository.StageResultDataLineRepository;
import fi.ipsc_result_server.repository.StageResultDataRepository;

@Service
public class StageScoreService {
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	ResultDataService resultDataService;
	
	@Autowired
	ScoreCardService scoreCardService;
	
	@Autowired
	StageResultDataRepository stageResultDataRepository;
	
	@Autowired
	StageResultDataLineRepository stageResultDataLineRepository;
	
	public List<StageScore> findStageScoresForCompetitor(String matchId, String competitorId) {
		List<StageScore> stageScores = null;
		
		return stageScores;
	}
	
	@Transactional
	public void generateStageResultsListing(Stage stage) {
		StageResultData stageResultData = new StageResultData(stage);
		
		List<StageResultDataLine> dataLines = new ArrayList<StageResultDataLine>();
		List<ScoreCard> scoreCards = scoreCardService.findScoreCardsByStage(stage.getId());
		Collections.sort(scoreCards);
		
		for (ScoreCard scoreCard : scoreCards) {
			StageResultDataLine resultDataLine = new StageResultDataLine();
			resultDataLine.setStageResultData(stageResultData);
			resultDataLine.setScoreCard(scoreCard);
			resultDataLine.setCompetitor(scoreCard.getCompetitor());
			dataLines.add(resultDataLine);
		}
		stageResultData.setDataLines(dataLines);
		entityManager.persist(stageResultData);
		
	}
	@Transactional
	private void removeOldStageResultListing(Stage stage) {
		try {
			String queryString = "SELECT s FROM StageResultData s WHERE s.stage.match.id = :matchId AND s.stage.id = :stageId";
			TypedQuery<StageResultData> query = entityManager.createQuery(queryString, StageResultData.class);
			query.setParameter("matchId", stage.getMatch().getId());
			query.setParameter("stageId", stage.getId());
			List<StageResultData> oldStageResultListings = query.getResultList();
			
			if (oldStageResultListings != null) {
				resultDataService.deleteStageResultListingsInBatch(oldStageResultListings);
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	@Transactional
	public void delete(String stageId) {
		try {
			String queryString = "SELECT s FROM StageResultData s WHERE s.stage.id = :stageId";
			TypedQuery<StageResultData> query = entityManager.createQuery(queryString, StageResultData.class);
			query.setParameter("stageId", stageId);
			List<StageResultData> resultData = query.getResultList();
			for (StageResultData data : resultData) {
				stageResultDataLineRepository.deleteInBatch(data.getDataLines());
				stageResultDataRepository.delete(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Transactional
	public void delete(StageResultData stageResultData) {
		entityManager.remove(stageResultData);
			
	}
	
	public StageResultData findByStage(Stage stage) {
			StageResultData resultData = null;
			try {
				String queryString = "SELECT s FROM StageResultData s WHERE s.stage = :stage";
				TypedQuery<StageResultData> query = entityManager.createQuery(queryString, StageResultData.class); 
				query.setParameter("stage", stage);
				List<StageResultData> resultList = query.getResultList();
				if (resultList != null && resultList.size() > 0) {
					resultData = resultList.get(0);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resultData;
	}
}
