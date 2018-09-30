package fi.ipscResultServer.repository.springJDBCRepository;

import java.util.List;

import org.springframework.stereotype.Component;

import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.MatchStatus;

@Component
public interface MatchRepository {
	
	public Match save(Match match);
	
	public Match getOne(Long matchId);
	
	public List<Match> findAll();
	
	public List<Match> findByUser(Long userId);
	
	public Long getIdByPractiScoreId(String practiScoreId);

	public List<Long> getAllIdsByPractiScoreId(String matchPractiScoreId);
	
	public void delete(Long id);
	
	public void setStatus(Long matchId, MatchStatus status);
	
}
