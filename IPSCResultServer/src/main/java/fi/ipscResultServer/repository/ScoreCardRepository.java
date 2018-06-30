package fi.ipscResultServer.repository;

import java.util.List;

import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.exception.DatabaseException;

public interface ScoreCardRepository {
	public ScoreCard save(ScoreCard scoreCard) throws DatabaseException;
	
	public ScoreCard findByCompetitorAndStage(String competitorId, String stageId) throws DatabaseException;

	public List<ScoreCard> findByStageAndDivision(Stage stage, String division) throws DatabaseException;
	
	public List<ScoreCard> findByCompetitorAndMatchPractiScoreIds(String competitorPractiScoreId, 
			String matchPractiScoreId) throws DatabaseException;
	
	public List<ScoreCard> findByStage(Long stageId) throws DatabaseException;
	
	public void deleteInBatch(List<ScoreCard> scoreCards) throws DatabaseException;
	
	public void delete(ScoreCard scoreCard) throws DatabaseException;

	public void deleteByMatch(Match match) throws DatabaseException;
	
	
}
