package fi.ipscResultServer.repository;

import fi.ipscResultServer.domain.IPSCDivision;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.statistics.CompetitorStatistics;

public interface CompetitorStatisticsRepository {
	public CompetitorStatistics findCompetitorStatisticsByMatchAndDivision(String matchId, IPSCDivision division);
	
	public CompetitorStatistics save(CompetitorStatistics competitorStatistics);
	
	public void deleteByMatch(Match match);
}
