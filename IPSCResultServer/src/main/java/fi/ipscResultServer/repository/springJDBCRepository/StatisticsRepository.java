package fi.ipscResultServer.repository.springJDBCRepository;

import java.util.List;

import org.springframework.stereotype.Component;

import fi.ipscResultServer.domain.statistics.CompetitorStatistics;

@Component
public interface StatisticsRepository {
	
	public void save(List<CompetitorStatistics> stats);
	
	public List<CompetitorStatistics> findByMatch(Long matchId);
	
	public List<CompetitorStatistics> findByMatchAndDivision(Long matchId, String division);
	
	public void deleteByMatch(Long matchId);
	
}
