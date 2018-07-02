package fi.ipscResultServer.controller.matchAnalysis;

import java.util.List;

import fi.ipscResultServer.domain.resultData.CompetitorResultData;
import fi.ipscResultServer.domain.resultData.StageResultDataLine;

public class MatchAnalysisData {
	
	private CompetitorResultData competitorResultData;
	private List<StageResultDataLine> stageResultDataLines;
	private List<ErrorCostTableLine> errorCostTableLines; 
	
	public MatchAnalysisData(CompetitorResultData competitorResultData,
			List<StageResultDataLine> stageResultDataLines,
			List<ErrorCostTableLine> errorCostTableLines) {
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
	public List<StageResultDataLine> getStageResultDataLines() {
		return stageResultDataLines;
	}
	public void setStageResultDataLines(List<StageResultDataLine> stageResultDataLines) {
		this.stageResultDataLines = stageResultDataLines;
	}
	public List<ErrorCostTableLine> getErrorCostTableLines() {
		return errorCostTableLines;
	}
	public void setErrorCostTableLines(List<ErrorCostTableLine> errorCostTableLines) {
		this.errorCostTableLines = errorCostTableLines;
	}
}
