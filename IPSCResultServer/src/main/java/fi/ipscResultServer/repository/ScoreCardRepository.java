package fi.ipscResultServer.repository;

import java.util.List;

import fi.ipscResultServer.domain.IPSCDivision;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.ScoreCard;

public interface ScoreCardRepository {
	public ScoreCard save(ScoreCard scoreCard);
	
	public ScoreCard findByCompetitorAndStage(String competitorId, String stageId);

	public List<ScoreCard> findByStageAndDivision(String stageId, IPSCDivision division);
	
	public List<ScoreCard> findByCompetitorAndMatch(String competitorId, String matchId);
	
	public List<ScoreCard> findByStage(String stageId);
	
	public void deleteInBatch(List<ScoreCard> scoreCards);
	
	public void delete(ScoreCard scoreCard);

	public void deleteByMatch(Match match);
	
	
}
