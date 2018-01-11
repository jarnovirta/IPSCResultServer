package fi.ipsc_result_server.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ipsc_result_server.domain.ResultData.CompetitorResultData;
import fi.ipsc_result_server.domain.ResultData.MatchResultData;
import fi.ipsc_result_server.domain.ResultData.StageResultData;
import fi.ipsc_result_server.repository.StageResultDataRepository;

@Service
public class ResultDataService {
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	CompetitorService competitorService;
	
	@Autowired
	MatchService matchService;
	
	@Autowired
	ScoreCardService scoreCardService;
	
	@Autowired
	StageResultDataRepository stageResultDataRepository;
	
	public CompetitorResultData getCompetitorResultData(String competitorId, String matchId) {
		try {
			String queryString = "SELECT c FROM CompetitorResultData c WHERE s.competitor.id = :competitorId AND s.match.id = :matchId";
			TypedQuery<CompetitorResultData> query = entityManager.createQuery(queryString, CompetitorResultData.class);
			query.setParameter("competitorId", competitorId);
			query.setParameter("matchId", matchId);
			List<CompetitorResultData> resultList = query.getResultList();
			if (resultList != null && resultList.size() > 0) {
				return resultList.get(0);
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
//		CompetitorResultData resultData = new CompetitorResultData();
//		resultData.setCompetitor(competitorService.getOne(competitorId));
//		resultData.setMatch(matchService.getOne(matchId));
//		List<ScoreCard> scoreCards = new ArrayList<ScoreCard>();
//		for (Stage stage : resultData.getMatch().getStages()) {
//			StageScore stageScore = new StageScore();
//			stageScore.setStage(stage);
//			stageScore.setScoreCards(scoreCardService.findCompetitorScoreCardsForStage(competitorId, stage.getId()));
//			stageScores.add(stageScore);
//		}
//		resultData.setStageScores(stageScores);
//		return resultData;
	}
	
	@Transactional
	public void deleteStageResultListingsInBatch(List<StageResultData> stageResultData) {
		stageResultDataRepository.deleteInBatch(stageResultData);
	}
	
	public MatchResultData findResultDataForMatch(String matchId) {
		try {
			String queryString = "SELECT m FROM MatchResultData m WHERE m.match.id = :matchId";
			TypedQuery<MatchResultData> query = entityManager.createQuery(queryString, MatchResultData.class);
			query.setParameter("matchId", matchId);
			List<MatchResultData> resultList = query.getResultList();
			if (resultList != null && resultList.size() > 0) {
				return resultList.get(0);
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	}
	
	public StageResultData findResultDataForStage(String stageId) {
		try {
			String queryString = "SELECT s FROM StageResultData s WHERE s.stage.id = :stageId";
			TypedQuery<StageResultData> query = entityManager.createQuery(queryString, StageResultData.class);
			query.setParameter("matchId", stageId);
			List<StageResultData> resultList = query.getResultList();
			if (resultList != null && resultList.size() > 0) {
				return resultList.get(0);
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	}
}
