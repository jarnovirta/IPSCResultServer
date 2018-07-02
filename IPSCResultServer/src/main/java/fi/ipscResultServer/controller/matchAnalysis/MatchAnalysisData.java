package fi.ipscResultServer.controller.matchAnalysis;

import java.util.List;

import fi.ipscResultServer.domain.resultData.CompetitorResultData;
import fi.ipscResultServer.domain.resultData.StageResultDataLine;

public class MatchAnalysisData {
	
	private CompetitorResultData competitorResultData;
	private List<StageResultDataLine> stageResultDataLines;
	
	
	public MatchAnalysisData(CompetitorResultData competitorResultData,
			List<StageResultDataLine> stageResultDataLines) {
		this.competitorResultData = competitorResultData;
		this.stageResultDataLines = stageResultDataLines;
		
		
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
}
