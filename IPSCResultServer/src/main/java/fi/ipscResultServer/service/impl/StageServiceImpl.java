package fi.ipscResultServer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.repository.springJDBCRepository.StageRepository;
import fi.ipscResultServer.service.StageService;

@Service
public class StageServiceImpl implements StageService {

	@Autowired
	private StageRepository stageRepository;
	
	public Stage getOne(Long id) {
		Stage stage = stageRepository.getOne(id);
		
		return stage;
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
	public Long getIdByPractiScoreReference(String matchPractiScoreId, String stagePractiScoreId) {
		return stageRepository.getIdByPractiScoreReference(matchPractiScoreId, stagePractiScoreId);
	}
}
