package fi.ipsc_result_server.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		System.out.println("*** REMOVING OLD STAGE RESULT LISTING");
		removeOldStageResultListing(stage);
		System.out.println("*** REMOVE DONE");
		StageResultData stageResultData = new StageResultData(stage);
		stageResultData.setDataLines(new ArrayList<StageResultDataLine>());
		List<ScoreCard> scoreCards = scoreCardService.findScoreCardsByStage(stage.getId());
		Collections.sort(scoreCards);
		System.out.println("*** SORTED SCORE CARDS HFS: ");
		
		for (ScoreCard scoreCard : scoreCards) {
			System.out.println(scoreCard.getHitFactor());
			StageResultDataLine resultDataLine = new StageResultDataLine();
			resultDataLine.setScoreCard(scoreCard);
			resultDataLine.setCompetitor(scoreCard.getCompetitor());
			stageResultData.getDataLines().add(resultDataLine);
		}
		System.out.println("*** PERSISTING STAGE RESULT LISTING ");
		entityManager.persist(stageResultData);
	}
	@Transactional
	private void removeOldStageResultListing(Stage stage) {
		System.out.println("*** REMOVING OLD STAGE RESULT LISTINGS " );
		try {
			String queryString = "SELECT s FROM StageResultData s WHERE s.stage.match.id = :matchId AND s.stage.id = :stageId";
			TypedQuery<StageResultData> query = entityManager.createQuery(queryString, StageResultData.class);
			query.setParameter("matchId", stage.getMatch().getId());
			query.setParameter("stageId", stage.getId());
			List<StageResultData> oldStageResultListings = query.getResultList();
			
			if (oldStageResultListings != null) {
				System.out.println("**** Found " + oldStageResultListings.size() + " old cards to remove. Removing...");
				resultDataService.deleteStageResultListingsInBatch(oldStageResultListings);
				System.out.println("**** Done removing.");
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	@Transactional
	public void deleteStageResultListing(String stageId) {
		try {
			System.out.println("DELETING STAGE RESLUT LISTING FOR STAGE ID : " + stageId);
			String queryString = "SELECT s FROM StageResultData s WHERE s.stage.id = :stageId";
			TypedQuery<StageResultData> query = entityManager.createQuery(queryString, StageResultData.class);
			query.setParameter("stageId", stageId);
			List<StageResultData> resultData = query.getResultList();
			System.out.println("*** FOUND : " + resultData.size());
			for (StageResultData data : resultData) {
				stageResultDataLineRepository.deleteInBatch(data.getDataLines());
				stageResultDataRepository.delete(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
