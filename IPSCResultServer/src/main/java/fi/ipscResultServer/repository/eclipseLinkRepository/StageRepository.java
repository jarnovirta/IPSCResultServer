package fi.ipscResultServer.repository.eclipseLinkRepository;

import fi.ipscResultServer.domain.Stage;

public interface StageRepository {

	public Stage getOne(Long id);
	
	public Stage findByPractiScoreId(String practiScoreMatchId, String practiScoreStageId);
	
}
