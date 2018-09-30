package fi.ipscResultServer.service;

import java.util.List;

import org.springframework.stereotype.Component;

import fi.ipscResultServer.domain.Stage;

@Component
public interface StageService {
	public Stage getOne(Long id);
	
	public List<Stage> save(List<Stage> stages);
	
	public void deleteByMatch(Long matchId);
	
	public List<Stage> findByMatch(Long matchId);
	
	public Long getIdByPractiScoreReference(String matchPractiScoreId, String stagePractiScoreId);
	
}
