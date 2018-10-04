package fi.ipscResultServer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.statistics.CompetitorStatistics;
import fi.ipscResultServer.service.CompetitorService;
import fi.ipscResultServer.service.ScoreCardService;
import fi.ipscResultServer.service.StatisticsService;

@Service
public class StatisticsServiceImpl implements StatisticsService {
	
	@Autowired
	private ScoreCardService scoreCardService;
	
	@Autowired
	private CompetitorService competitorService;
	
	public List<CompetitorStatistics> get(Long matchId, 
			String division) {
		List<CompetitorStatistics> stats = scoreCardService.getStatistics(matchId, division);	
		List<Competitor> competitors = competitorService.findByMatchAndDivision(matchId, division);
		
		int rank = 1;
		double topPoints = 0;
		for (CompetitorStatistics stat : stats) {
			stat.setCompetitor(getCompetitorById(competitors, stat.getCompetitorId()));
			if (rank == 1) topPoints = stat.getDivisionPoints();
			stat.setDivisionRank(rank++);
			if (topPoints > 0) {
				double percentage = stat.getDivisionPoints() / topPoints * 100; 
				stat.setDivisionScorePercentage(percentage);
			}
		}
		return stats;
	}
	private Competitor getCompetitorById(List<Competitor> competitors, Long competitorId) {
		for (Competitor comp : competitors) if (comp.getId().equals(competitorId)) return comp;
		return null;
	}
}
