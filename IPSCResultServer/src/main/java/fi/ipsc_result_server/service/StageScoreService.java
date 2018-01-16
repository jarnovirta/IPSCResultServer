package fi.ipsc_result_server.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipsc_result_server.domain.IPSCDivision;
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
	
	final static Logger logger = Logger.getLogger(StageScoreService.class);
	
	public List<StageScore> findStageScoresForCompetitor(String matchId, String competitorId) {
		List<StageScore> stageScores = null;
		
		return stageScores;
	}
	
	@Transactional
	public void generateStageResultsListing(Stage stage) {
		List<IPSCDivision> divisions = new ArrayList<IPSCDivision>();
		divisions.add(IPSCDivision.COMBINED);
		divisions.addAll(stage.getMatch().getDivisions());
		
		for (IPSCDivision division : divisions) {
			StageResultData stageResultData = new StageResultData(stage, division);
			
			List<StageResultDataLine> dataLines = new ArrayList<StageResultDataLine>();
			List<ScoreCard> scoreCards;
			if (division == IPSCDivision.COMBINED) scoreCards = scoreCardService.findScoreCardsByStage(stage.getId()); 
			else scoreCards = scoreCardService.findScoreCardsByStageAndDivision(stage.getId(), division);
			Collections.sort(scoreCards);
			
			double topHitFactor = -1.0;
			double topPoints = -1.0;
			int rank = 1;
			for (ScoreCard scoreCard : scoreCards) {
				// Discard results for DQ'ed competitor
				if (scoreCard.getCompetitor().isDisqualified()) continue;
				
				if (rank == 1) topHitFactor = scoreCard.getHitFactor();
				StageResultDataLine resultDataLine = new StageResultDataLine();
				resultDataLine.setStageRank(rank);
				resultDataLine.setStageResultData(stageResultData);
				resultDataLine.setScoreCard(scoreCard);
				resultDataLine.setCompetitor(scoreCard.getCompetitor());
				resultDataLine.setStagePoints((scoreCard.getHitFactor() / topHitFactor) * stage.getMaxPoints()); 
				scoreCard.setStageRank(rank);
				entityManager.merge(scoreCard);
				if (rank == 1) topPoints = resultDataLine.getStagePoints();
				resultDataLine.setStageScorePercentage(resultDataLine.getStagePoints() / topPoints * 100);
				dataLines.add(resultDataLine);
				
				if (!stage.getMatch().getDivisionsWithResults().contains(scoreCard.getCompetitor().getDivision())) {
					stage.getMatch().getDivisionsWithResults().add(scoreCard.getCompetitor().getDivision());
				}
				rank++;
			}
			
			stageResultData.setDataLines(dataLines);
			entityManager.persist(stageResultData);
		}
				
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
	public void deleteInBatch(List<StageResultData> stageResultDataList) {
		for (StageResultData data : stageResultDataList) {
			entityManager.remove(data);
		}
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
}
