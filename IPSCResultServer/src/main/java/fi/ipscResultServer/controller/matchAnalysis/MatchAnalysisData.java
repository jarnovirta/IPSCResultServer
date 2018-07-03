package fi.ipscResultServer.controller.matchAnalysis;

import fi.ipscResultServer.domain.Match;

public class MatchAnalysisData {
	private Match match;
	
	private CompetitorMatchAnalysisData competitorData;
	private CompetitorMatchAnalysisData compareToCompetitorData;
	
	public MatchAnalysisData(Match match, CompetitorMatchAnalysisData competitorData, 
			CompetitorMatchAnalysisData compareToCompetitorData) {
		this.match = match;
		this.competitorData = competitorData;
		this.compareToCompetitorData = compareToCompetitorData;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public CompetitorMatchAnalysisData getCompetitorData() {
		return competitorData;
	}
	public void setCompetitorData(CompetitorMatchAnalysisData competitorData) {
		this.competitorData = competitorData;
	}
	public CompetitorMatchAnalysisData getCompareToCompetitorData() {
		return compareToCompetitorData;
	}
	public void setCompareToCompetitorData(CompetitorMatchAnalysisData compareToCompetitorData) {
		this.compareToCompetitorData = compareToCompetitorData;
	}
	
}
