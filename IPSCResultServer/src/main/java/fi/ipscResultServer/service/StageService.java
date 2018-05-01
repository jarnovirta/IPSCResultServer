package fi.ipscResultServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.repository.StageRepository;

@Service
public class StageService {

	@Autowired
	private StageRepository stageRepository;
	
	public Stage getOne(Long id) throws DatabaseException {
		return stageRepository.getOne(id);
	}
	
	public Stage findByPractiScoreId(String practiScoreMatchId, String practiScoreStageId) throws DatabaseException {
		return stageRepository.findByPractiScoreId(practiScoreMatchId,  practiScoreStageId);
	}
}
