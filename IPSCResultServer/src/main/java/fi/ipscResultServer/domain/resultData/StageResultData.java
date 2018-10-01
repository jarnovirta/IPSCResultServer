package fi.ipscResultServer.domain.resultData;

import java.util.List;

import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.domain.Stage;

public class StageResultData {
		
	private Stage stage;
		
	private String division;
	
	private List<ScoreCard> scoreCards;
	
	public StageResultData(Stage stage, String division, List<ScoreCard> scoreCards) { 
		this.stage = stage;
		this.division = division;
		this.scoreCards = scoreCards;
	}

	public Stage getStage() {
		return stage;
	}

	public String getDivision() {
		return division;
	}

	public List<ScoreCard> getScoreCards() {
		return scoreCards;
	}

}
