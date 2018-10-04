package fi.ipscResultServer.service;

import java.util.List;

import org.springframework.stereotype.Component;

import fi.ipscResultServer.domain.statistics.CompetitorStatistics;

@Component
public interface StatisticsService {
	
	public List<CompetitorStatistics> get(Long matchId, String division);
	
}
