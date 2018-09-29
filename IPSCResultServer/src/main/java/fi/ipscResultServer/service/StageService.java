package fi.ipscResultServer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.repository.springJDBCRepository.StageRepository;

@Service
public class StageService {

	@Autowired
	private StageRepository stageRepository;
	
	public Stage getOne(Long id) {
		Stage stage = stageRepository.getOne(id);
		return stage;
	}
	
	public Stage getIdByPractiScoreReferences(String practiScoreMatchId, String practiScoreStageId) throws DatabaseException {
		return null;
	}
	@Transactional
	public List<Stage> save(List<Stage> stages) {
		stageRepository.save(stages);
		List<Stage> savedStages = new ArrayList<Stage>();
		for (Stage stage : stages) {
			savedStages.add(stageRepository.findByPractiScoreReference(stage.getMatch().getPractiScoreId(), stage.getPractiScoreId()));		
		}
		return savedStages;
	}
	@Transactional
	public void deleteByMatch(Long matchId) {
		stageRepository.deleteByMatch(matchId);
	}
	public List<Stage> findByMatch(Long matchId) {
		return stageRepository.findByMatch(matchId);
	}
}
