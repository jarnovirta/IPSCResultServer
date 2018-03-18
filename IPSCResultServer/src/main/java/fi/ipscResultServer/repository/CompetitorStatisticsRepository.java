package fi.ipscResultServer.repository;

import java.util.List;

import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.statistics.CompetitorStatistics;

public interface CompetitorStatisticsRepository {
	public List<CompetitorStatistics> findCompetitorStatisticsByMatchAndDivision(String matchId, String division);
	
	public List<CompetitorStatistics> findCompetitorStatisticsByMatch(String matchId);
	
	public CompetitorStatistics save(CompetitorStatistics competitorStatistics);
	
	public void deleteByMatch(Match match);
}
