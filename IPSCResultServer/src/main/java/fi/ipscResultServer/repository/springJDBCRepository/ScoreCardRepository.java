package fi.ipscResultServer.repository.springJDBCRepository;

import java.util.List;

import org.springframework.stereotype.Component;

import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.domain.statistics.CompetitorStatistics;

@Component
public interface ScoreCardRepository {

	public void deleteByMatch(Long matchId);
	
	public void save(List<ScoreCard> cards);
	
	public List<ScoreCard> findByCompetitor(Long competitorId);
	
	public List<ScoreCard> findByStage(Long stageId);
	
	public List<ScoreCard> findByStageAndDivision(Long stageId, String division);
	
	public List<ScoreCard> findByMatch(Long matchId);
		
	public List<String> getDivisionsWithResults(Long matchId);
	
	public List<CompetitorStatistics> getStatistics(Long matchId, String division);
	
	public List<CompetitorStatistics> getStatistics(Long matchId);
	
}
