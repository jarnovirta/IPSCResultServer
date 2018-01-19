package fi.ipsc_result_server.service.resultDataService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ipsc_result_server.domain.ScoreCard;
import fi.ipsc_result_server.domain.ResultData.CompetitorResultData;
import fi.ipsc_result_server.service.CompetitorService;
import fi.ipsc_result_server.service.MatchService;

@Service
public class CompetitorResultDataService {
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	CompetitorService competitorService;
	
	@Autowired
	MatchService matchService;
	
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
}
