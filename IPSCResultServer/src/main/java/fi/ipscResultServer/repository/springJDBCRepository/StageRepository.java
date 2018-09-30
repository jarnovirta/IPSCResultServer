package fi.ipscResultServer.repository.springJDBCRepository;

import java.util.List;

import org.springframework.stereotype.Component;

import fi.ipscResultServer.domain.Stage;

@Component
public interface StageRepository {
	
	public Stage getOne(Long id);
	
	public void save(List<Stage> stages);
	
	public Stage findByPractiScoreReference(String matchPractiScoreId, String stagePractiScoreId);
	
	public List<Stage> findByMatch(Long matchId);
	
	public void deleteByMatch(Long matchId);
	
	public Long getIdByPractiScoreReference(String matchPractiScoreId, String stagePractiScoreId);
	
}
