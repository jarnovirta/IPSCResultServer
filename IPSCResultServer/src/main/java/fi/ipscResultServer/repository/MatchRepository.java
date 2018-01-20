package fi.ipscResultServer.repository;

import java.util.List;

import fi.ipscResultServer.domain.Match;

public interface MatchRepository {
	public Match save(Match match);
	
	public List<Match> findAll();
	
	// Returns Match list with only necessary properties set for listing purposes
	// instead of full instances with a list of Stages etc.
	public List<Match> getAdminPageMatchList();
	
	public Match getOne(String id);
	
	public void delete(Match match);
	
	
}
