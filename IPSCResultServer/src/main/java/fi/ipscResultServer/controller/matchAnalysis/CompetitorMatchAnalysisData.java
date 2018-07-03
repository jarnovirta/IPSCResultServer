package fi.ipscResultServer.controller.matchAnalysis;

import java.util.Map;

import fi.ipscResultServer.domain.resultData.CompetitorResultData;
import fi.ipscResultServer.domain.resultData.StageResultDataLine;

public class CompetitorMatchAnalysisData {
	
	private CompetitorResultData competitorResultData;
	
	// Key: stage practiScoreId
	private Map<String, StageResultDataLine> stageResultDataLines;
	
	// Key: stage practiScoreId
	private Map<String, ErrorCostTableLine> errorCostTableLines; 
	
	public CompetitorMatchAnalysisData(CompetitorResultData competitorResultData,
			Map<String, StageResultDataLine> stageResultDataLines,
			Map<String, ErrorCostTableLine> errorCostTableLines) {
		this.competitorResultData = competitorResultData;
		this.stageResultDataLines = stageResultDataLines;
		this.errorCostTableLines = errorCostTableLines;
	}
	public CompetitorResultData getCompetitorResultData() {
		return competitorResultData;
	}
	public void setCompetitorResultData(CompetitorResultData competitorResultData) {
		this.competitorResultData = competitorResultData;
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
	
}
