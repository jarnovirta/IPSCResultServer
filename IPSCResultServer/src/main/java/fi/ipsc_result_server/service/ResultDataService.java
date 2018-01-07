package fi.ipsc_result_server.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ipsc_result_server.data.CompetitorResultData;
import fi.ipsc_result_server.domain.Stage;
import fi.ipsc_result_server.domain.StageScore;

@Service
public class ResultDataService {
	@Autowired
	CompetitorService competitorService;
	
	@Autowired
	MatchService matchService;
	
	@Autowired
	ScoreCardService scoreCardService;
	
	public CompetitorResultData getCompetitorResultData(String competitorId, String matchId) {
		CompetitorResultData resultData = new CompetitorResultData();
		resultData.setCompetitor(competitorService.getOne(competitorId));
		resultData.setMatch(matchService.getOne(matchId));
		List<StageScore> stageScores = new ArrayList<StageScore>();
		for (Stage stage : resultData.getMatch().getStages()) {
			StageScore stageScore = new StageScore();
			stageScore.setStage(stage);
			stageScore.setScoreCards(scoreCardService.findCompetitorScoreCardsForStage(competitorId, stage.getId()));
			stageScores.add(stageScore);
		}
		resultData.setStageScores(stageScores);
		return resultData;
	}
}
