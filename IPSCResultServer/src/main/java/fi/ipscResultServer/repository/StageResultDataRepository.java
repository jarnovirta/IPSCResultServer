package fi.ipscResultServer.repository;

import java.util.List;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.IPSCDivision;
import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.domain.ResultData.StageResultData;
import fi.ipscResultServer.domain.ResultData.StageResultDataLine;

public interface StageResultDataRepository {
	public StageResultData findByStageAndDivision(String stageId, IPSCDivision division);
	
	public List<StageResultData> findByStage(Stage stage);
	
	public List<StageResultDataLine> findStageResultDataLinesByCompetitor(Competitor competitor);
	
	public void delete(StageResultData stageResultData); 
	
	public StageResultData save(StageResultData stageResultData);
	
}
