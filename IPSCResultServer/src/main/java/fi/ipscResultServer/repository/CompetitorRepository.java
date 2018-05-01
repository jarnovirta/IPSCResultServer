package fi.ipscResultServer.repository;

import java.util.List;

import fi.ipscResultServer.domain.Competitor;

public interface CompetitorRepository {
	public Competitor save(Competitor competitor);
	
	public Competitor getOne(Long id);
	
	public Competitor findByPractiScoreReferences(String practiScoreMatchId, String practiScoreCompetitorId);
	
	public List<Competitor> findByMatch(Long matchId);
	
	
}
