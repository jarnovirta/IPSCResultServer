package fi.ipscResultServer.repository;

import java.util.List;

import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.User;
import fi.ipscResultServer.exception.DatabaseException;

public interface MatchRepository {
	public Match save(Match match) throws DatabaseException;
	
	public List<Match> findAll();
	
	// Returns Match list with only necessary properties set for listing purposes
	// instead of full instances with a list of Stages etc.
	public List<Match> getFullMatchList();
	
	public List<Match> getMatchListForUser(User user);
	
	public Match getOne(Long id) throws DatabaseException;
	
	public Match findByPractiScoreId(String practiScoreId);
	
	public void delete(Match match) throws DatabaseException;
	
	
}
