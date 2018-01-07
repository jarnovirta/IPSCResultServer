package fi.ipsc_result_server.data;

import java.util.List;

import fi.ipsc_result_server.domain.Competitor;
import fi.ipsc_result_server.domain.Match;
import fi.ipsc_result_server.domain.StageScore;


public class CompetitorResultData {
	private Match match;
	private Competitor competitor;
	private List<StageScore> stageScores;
	public Match getMatch() {
		return match;
	}
	public void setMatch(Match match) {
		this.match = match;
	}
	public List<StageScore> getStageScores() {
		return stageScores;
	}
	public void setStageScores(List<StageScore> stageScores) {
		this.stageScores = stageScores;
	}
	public Competitor getCompetitor() {
		return competitor;
	}
	public void setCompetitor(Competitor competitor) {
		this.competitor = competitor;
	}
}
