package fi.ipscResultServer.repository;

import java.util.List;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.IPSCDivision;
import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.domain.ResultData.StageResultData;
import fi.ipscResultServer.domain.ResultData.StageResultDataLine;
import fi.ipscResultServer.exception.DatabaseException;

public interface StageResultDataRepository {
	public StageResultData findByStageAndDivision(String stageId, IPSCDivision division) throws DatabaseException;
	
	public List<StageResultData> findByStage(Stage stage) throws DatabaseException;
	
	public List<StageResultDataLine> findStageResultDataLinesByCompetitor(Competitor competitor) throws DatabaseException; 
	
	public void delete(StageResultData stageResultData) throws DatabaseException; 
	
	public StageResultData save(StageResultData stageResultData) throws DatabaseException;
	
}
