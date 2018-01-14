package fi.ipsc_result_server.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipsc_result_server.domain.ScoreCard;
import fi.ipsc_result_server.domain.Stage;
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
			
			String queryString = "SELECT s FROM ScoreCard s WHERE s.competitor.id = :competitorId AND s.stage.match.id = :matchId";
			TypedQuery<ScoreCard> query = entityManager.createQuery(queryString, ScoreCard.class);
			query.setParameter("competitorId", competitorId);
			query.setParameter("matchId", matchId);
			List<ScoreCard> resultList = query.getResultList();
			Map<String, ScoreCard> scoreCards = new HashMap<String, ScoreCard>();
			for (ScoreCard card : resultList) {
				scoreCards.put(card.getStage().getId(), card);
			}
	
			CompetitorResultData resultData = new CompetitorResultData();
			resultData.setScoreCards(scoreCards);
			resultData.setCompetitor(competitorService.getOne(competitorId));
			resultData.setMatch(matchService.getOne(matchId));
			
			return resultData;
			
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		
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
