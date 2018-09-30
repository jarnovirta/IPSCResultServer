package fi.ipscResultServer.service;

import java.util.List;

import org.springframework.stereotype.Component;

import fi.ipscResultServer.domain.statistics.CompetitorStatistics;

@Component
public interface StatisticsService {
	
	public List<CompetitorStatistics> findByMatchAndDivision(Long matchId, 
			String division);
	
	public List<CompetitorStatistics> findByMatch(Long matchId);
	
	public void generateCompetitorStatistics(Long matchId);
	
	public void deleteByMatch(Long matchId);
	
}
