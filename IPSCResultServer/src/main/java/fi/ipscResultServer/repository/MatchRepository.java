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
	public List<Match> getAdminPageMatchList();
	
	public List<Match> getAdminPageMatchListByUser(User user);
	
	public Match getOne(String id) throws DatabaseException;
	
	public void delete(Match match) throws DatabaseException;
	
	
}
