package fi.ipscResultServer.controller.matchAnalysis;

import java.util.List;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.ScoreCard;

public class CompetitorMatchAnalysisData {
	
	private Competitor competitor;
	
	private List<ScoreCard> scoreCards;
	
	// Key: stage practiScoreId
	private List<ErrorCostTableLine> errorCostTableLines; 
	
	public CompetitorMatchAnalysisData(Competitor competitor, List<ScoreCard> scoreCards,
			List<ErrorCostTableLine> errorCostTableLines) {
		this.competitor = competitor;
		this.scoreCards = scoreCards;
		this.errorCostTableLines = errorCostTableLines;

	}


	public List<ErrorCostTableLine> getErrorCostTableLines() {
		return errorCostTableLines;
	}


	public void setErrorCostTableLines(List<ErrorCostTableLine> errorCostTableLines) {
		this.errorCostTableLines = errorCostTableLines;
	}


	public Competitor getCompetitor() {
		return competitor;
	}
	public void setCompetitor(Competitor competitor) {
		this.competitor = competitor;
	}

	public List<ScoreCard> getScoreCards() {
		return scoreCards;
	}

	public void setScoreCards(List<ScoreCard> scoreCards) {
		this.scoreCards = scoreCards;
	}
}
