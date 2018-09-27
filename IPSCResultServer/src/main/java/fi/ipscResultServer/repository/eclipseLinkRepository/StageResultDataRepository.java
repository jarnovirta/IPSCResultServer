package fi.ipscResultServer.repository.eclipseLinkRepository;

import java.util.List;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.domain.resultData.StageResultData;
import fi.ipscResultServer.domain.resultData.StageResultDataLine;
import fi.ipscResultServer.exception.DatabaseException;

public interface StageResultDataRepository {
	public StageResultData findByStageAndDivision(Long stageId, String division) throws DatabaseException;
	
	public List<StageResultData> findByStage(Stage stage) throws DatabaseException;
	
	public List<StageResultDataLine> findStageResultDataLinesByCompetitor(Competitor competitor) throws DatabaseException; 
	
	public List<StageResultDataLine> findStageResultDataLinesByCompetitorAndDivision(Competitor competitor, String division) 
			throws DatabaseException;
	
	public void delete(StageResultData stageResultData) throws DatabaseException; 
	
	public StageResultData save(StageResultData stageResultData) throws DatabaseException;
	
}
