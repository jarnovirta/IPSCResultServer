package fi.ipscResultServer.service;

import java.util.List;

import org.springframework.stereotype.Component;

import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.domain.statistics.CompetitorStatistics;
import fi.ipscResultServer.exception.DatabaseException;

@Component
public interface ScoreCardService {
	
	public void save(List<ScoreCard> scoreCards) throws DatabaseException;
	
	public List<ScoreCard> findByMatch(Long matchId, boolean eager);
	
	public List<ScoreCard> findByStageAndDivision(Long stageId, String division);
	
	public void deleteByMatch(Long matchId);
	
	public List<CompetitorStatistics> getStatistics(Long matchId, String division);
	
	public List<ScoreCard> findByCompetitor(Long competitorId, boolean eager);
	
	public List<String> getDivisionsWithResults(Long matchId);
		
}
