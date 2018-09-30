package fi.ipscResultServer.service;

import java.util.List;

import org.springframework.stereotype.Component;

import fi.ipscResultServer.domain.Competitor;

@Component
public interface CompetitorService {
	
	public Competitor getOne(Long id);
	
	public void save(List<Competitor> competitors);
	
	public List<Competitor> findByMatch(Long matchId);
	
	public Competitor findByPractiScoreReferences(String matchPractiScoreId, String competitorPractiScoreId);
	
	public void deleteByMatch(Long matchId);
}
