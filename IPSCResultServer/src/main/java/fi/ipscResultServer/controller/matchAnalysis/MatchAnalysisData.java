package fi.ipscResultServer.controller.matchAnalysis;

import fi.ipscResultServer.domain.Match;

public class MatchAnalysisData {
	private Match match;
	
	private CompetitorMatchAnalysisData competitorMatchAnalysisData;
	private CompetitorMatchAnalysisData compareToCompetitorMatchAnalysisData;
	
	public MatchAnalysisData(Match match, CompetitorMatchAnalysisData competitorData, 
			CompetitorMatchAnalysisData compareToCompetitorData) {
		this.match = match;
		this.competitorMatchAnalysisData = competitorData;
		this.compareToCompetitorMatchAnalysisData = compareToCompetitorData;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public CompetitorMatchAnalysisData getCompetitorMatchAnalysisData() {
		return competitorMatchAnalysisData;
	}
	public void setCompetitorMatchAnalysisData(CompetitorMatchAnalysisData competitorData) {
		this.competitorMatchAnalysisData = competitorData;
	}
	public CompetitorMatchAnalysisData getCompareToCompetitorMatchAnalysisData() {
		return compareToCompetitorMatchAnalysisData;
	}
	public void setCompareToCompetitorMatchData(CompetitorMatchAnalysisData compareToCompetitorData) {
		this.compareToCompetitorMatchAnalysisData = compareToCompetitorData;
	}
	
}
