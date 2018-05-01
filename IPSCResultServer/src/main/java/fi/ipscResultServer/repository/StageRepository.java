package fi.ipscResultServer.repository;

import fi.ipscResultServer.domain.Stage;

public interface StageRepository {

	public Stage getOne(Long id);
	
	public Stage findByPractiScoreId(String practiScoreMatchId, String practiScoreStageId);
	
}
