package fi.ipsc_result_server.service.resultDataService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipsc_result_server.domain.Competitor;
import fi.ipsc_result_server.domain.IPSCDivision;
import fi.ipsc_result_server.domain.Match;
import fi.ipsc_result_server.domain.ScoreCard;
import fi.ipsc_result_server.domain.Stage;
import fi.ipsc_result_server.domain.ResultData.StageResultData;
import fi.ipsc_result_server.domain.ResultData.StageResultDataLine;
import fi.ipsc_result_server.repository.StageResultDataLineRepository;
import fi.ipsc_result_server.repository.StageResultDataRepository;
import fi.ipsc_result_server.service.ScoreCardService;

@Service
public class StageResultDataService {

	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	StageResultDataRepository stageResultDataRepository;
	
	@Autowired
	StageResultDataLineRepository stageResultDataLineRepository;
	
	@Autowired
	ScoreCardService scoreCardService;
	
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
	@Transactional
	public void generateStageResultsListing(Stage stage) {
		List<IPSCDivision> divisions = new ArrayList<IPSCDivision>();
		divisions.addAll(stage.getMatch().getDivisions());
		divisions.add(IPSCDivision.COMBINED);
		int divisionsWithResults = 0;
		for (IPSCDivision division : divisions) {
			StageResultData stageResultData = new StageResultData(stage, division);
			
			List<StageResultDataLine> dataLines = new ArrayList<StageResultDataLine>();
			List<ScoreCard> scoreCards;
			// Only generate data for combined divisions if at least 2 divisions have results. 
			if (division == IPSCDivision.COMBINED) {
				if (divisionsWithResults < 2) {
					continue;
				}
				else {
					scoreCards = scoreCardService.findByStage(stage.getId());
				}
			}
			else scoreCards = scoreCardService.findByStageAndDivision(stage.getId(), division);
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
			
			if (dataLines.size() > 0) { 
				divisionsWithResults++;
				stageResultData.setDataLines(dataLines);
				entityManager.persist(stageResultData);
			}
		}
	}

	@Transactional
	public void deleteInBatch(List<StageResultData> stageResultDataList) {
		for (StageResultData data : stageResultDataList) {
			entityManager.remove(data);
		}
	}
	
	@Transactional
	public void deleteByStage(Stage stage) {
		List<StageResultData> oldStageResultData = findByStage(stage);
		if (oldStageResultData != null) {
			deleteInBatch(oldStageResultData);
		}
	}
	@Transactional
	public void deleteByMatch(Match match) {
		for (Stage stage : match.getStages()) {
			deleteByStage(stage);
		}
	}
}
