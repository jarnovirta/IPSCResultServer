package fi.ipscResultServer.controller.matchAnalysis;

import java.util.Map;

import fi.ipscResultServer.domain.Competitor;

public class CompetitorMatchAnalysisData {
	
	private Competitor competitor;
	
	// Key: stage practiScoreId
//	private Map<String, StageResultDataLine> stageResultDataLines;
	
	// Key: stage practiScoreId
	private Map<String, ErrorCostTableLine> errorCostTableLines; 
	
	private Map<Integer, Double> divisionStagePercentages;
	
	private Map<Integer, Double> combinedResultsStagePercentages;
	
//	public CompetitorMatchAnalysisData(Competitor competitor, Map<String, StageResultDataLine> stageResultDataLines,
//			Map<String, ErrorCostTableLine> errorCostTableLines,
//			Map<Integer, Double> divisionStagePercentages,
//			Map<Integer, Double> combinedResultsStagePercentages) {
//		this.competitor = competitor;
////		this.stageResultDataLines = stageResultDataLines;
//		this.errorCostTableLines = errorCostTableLines;
//		this.divisionStagePercentages = divisionStagePercentages;
//		this.combinedResultsStagePercentages = combinedResultsStagePercentages;
//	}

//	public Map<String, StageResultDataLine> getStageResultDataLines() {
//		return stageResultDataLines;
//	}
//	public void setStageResultDataLines(Map<String, StageResultDataLine> stageResultDataLines) {
//		this.stageResultDataLines = stageResultDataLines;
//	}
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
