package fi.ipscResultServer.repository.eclipseLinkRepository;

import java.util.List;

import fi.ipscResultServer.domain.statistics.CompetitorStatistics;

public interface CompetitorStatisticsRepository {
	public List<CompetitorStatistics> findCompetitorStatisticsByMatchAndDivision(Long matchId, String division);
	
	public List<CompetitorStatistics> findCompetitorStatisticsByMatch(Long matchId);
	
	public CompetitorStatistics save(CompetitorStatistics competitorStatistics);
	
	public void deleteByMatch(Long matchId);
}
