package fi.ipscResultServer.service;

import java.util.List;

import org.springframework.stereotype.Component;

import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.MatchStatus;

@Component
public interface MatchService {
	
	public Match save(Match match);

	public Match getOne(Long matchId, boolean eager);
	
	public List<Match> findAll();
	
	public List<Match> findByUser(Long userId);

	public Long getIdByPractiScoreId(String practiScoreId);
	
	public void deleteByPractiScoreId(String matchPractiScoreId);
	
	public void delete(Long id);
	
	public void setStatus(Long matchId, MatchStatus newStatus);
}
