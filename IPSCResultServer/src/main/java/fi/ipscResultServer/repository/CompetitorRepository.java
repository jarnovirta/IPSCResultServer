package fi.ipscResultServer.repository;

import java.util.List;

import fi.ipscResultServer.domain.Competitor;

public interface CompetitorRepository {
	public Competitor save(Competitor competitor);
	
	public Competitor getOne(String id);
	
	public List<Competitor> findByMatch(String matchId);
}
