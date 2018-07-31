package fi.ipscResultServer.controller.matchAnalysis;

import java.util.Map;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.resultData.CompetitorResultData;
import fi.ipscResultServer.domain.resultData.StageResultDataLine;

public class CompetitorMatchAnalysisData {
	
	private Competitor competitor;
	
	private Match match; 
	
	// Key: stage practiScoreId
	private Map<String, StageResultDataLine> stageResultDataLines;
	
	// Key: stage practiScoreId
	private Map<String, ErrorCostTableLine> errorCostTableLines; 
	
	// Competitor result percentages per stage for competitor's division
	private Map<Integer, Double> stagePercentages;
	
	// Competitor result percentages per stage for combined division
	private Map<Integer, Double> combinedDivStagePercentages;
	
	private Map<Integer, Double> divisionStagePercentages;
	
	private Map<Integer, Double> combinedResultsStagePercentages;
	
	public CompetitorMatchAnalysisData(Match match, Competitor competitor, CompetitorResultData competitorResultData,
			Map<String, StageResultDataLine> stageResultDataLines,
			Map<String, ErrorCostTableLine> errorCostTableLines,
			Map<Integer, Double> divisionStagePercentages,
			Map<Integer, Double> combinedResultsStagePercentages) {
		this.match = match;
		this.competitor = competitor;
		this.stageResultDataLines = stageResultDataLines;
		this.errorCostTableLines = errorCostTableLines;
		this.divisionStagePercentages = divisionStagePercentages;
		this.combinedResultsStagePercentages = combinedResultsStagePercentages;
	}

	public Map<String, StageResultDataLine> getStageResultDataLines() {
		return stageResultDataLines;
	}
	public void setStageResultDataLines(Map<String, StageResultDataLine> stageResultDataLines) {
		this.stageResultDataLines = stageResultDataLines;
	}
	public Map<String, ErrorCostTableLine> getErrorCostTableLines() {
		return errorCostTableLines;
	}
	public void setErrorCostTableLines(Map<String, ErrorCostTableLine> errorCostTableLines) {
		this.errorCostTableLines = errorCostTableLines;
	}
	public Competitor getCompetitor() {
		return competitor;
	}
	public void setCompetitor(Competitor competitor) {
		this.competitor = competitor;
	}
	public Match getMatch() {
		return match;
	}
	public void setMatch(Match match) {
		this.match = match;
	}

	public Map<Integer, Double> getStagePercentages() {
		return stagePercentages;
	}

	public void setStagePercentages(Map<Integer, Double> stagePercentages) {
		this.stagePercentages = stagePercentages;
	}

	public Map<Integer, Double> getCombinedDivStagePercentages() {
		return combinedDivStagePercentages;
	}

	public void setCombinedDivStagePercentages(Map<Integer, Double> combinedDivStagePercentages) {
		this.combinedDivStagePercentages = combinedDivStagePercentages;
	}

	public Map<Integer, Double> getDivisionStagePercentages() {
		return divisionStagePercentages;
	}

	public void setDivisionStagePercentages(Map<Integer, Double> divisionStagePercentages) {
		this.divisionStagePercentages = divisionStagePercentages;
	}

	public Map<Integer, Double> getCombinedResultsStagePercentages() {
		return combinedResultsStagePercentages;
	}

	public void setCombinedResultsStagePercentages(Map<Integer, Double> combinedResultsStagePercentages) {
		this.combinedResultsStagePercentages = combinedResultsStagePercentages;
	}
	
}
