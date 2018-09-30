package fi.ipscResultServer.service;

import java.util.List;

import org.springframework.stereotype.Component;

import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.exception.DatabaseException;

@Component
public interface ScoreCardService {
	
	public void save(List<ScoreCard> scoreCards) throws DatabaseException;
	
	public List<ScoreCard> findByMatch(Long matchId, boolean eager);
	
	public List<ScoreCard> findByStage(Long stageId);
	
	public List<ScoreCard> findByStageAndDivision(Long stageId, String division);
	
	public void deleteByMatch(Long matchId);
	
	public List<ScoreCard> findByCompetitor(Long competitorId);
	
	public List<String> getDivisionsWithResults(Long matchId);
		
}
