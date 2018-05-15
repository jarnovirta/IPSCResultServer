package fi.ipscResultServer.service.resultDataService;

import java.util.ArrayList;
import java.util.List;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.PowerFactor;
import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.util.DataFormatUtils;

public class CompetitorErrorCostDataService {
	private static int cHitPointLoss;
	private static int dHitPointLoss;
	private static int proceduralAndNoShootPointLoss = 10;
	private static int missPointLoss = 15;
	private static int matchTotalPoints;
	
	public static List<ErrorCostTableLine> getErrorCostTableLines(Match match, Competitor competitor, 
			List<ScoreCard> scoreCards) {
		List<ErrorCostTableLine> errorCostTableLines = new ArrayList<ErrorCostTableLine>();
		
		if (competitor.getPowerFactor().equals(PowerFactor.MAJOR)) {
			cHitPointLoss = 1;
			dHitPointLoss = 3;
		}
		else if (competitor.getPowerFactor().equals(PowerFactor.MINOR)){
			cHitPointLoss = 2;
			dHitPointLoss = 4;
		}
		else {
			return errorCostTableLines;
		}
		matchTotalPoints = 0;
		for (ScoreCard card : scoreCards) {
			matchTotalPoints += card.getStage().getMaxPoints();
		}
		for (ScoreCard card : scoreCards) {
			errorCostTableLines.add(new ErrorCostTableLine(card));
			
		}
		for (Stage stage : match.getStages()) {
			matchTotalPoints += stage.getMaxPoints();
		}
		
		return errorCostTableLines;
	}
	
	public static class ErrorCostTableLine {
		private ScoreCard scoreCard;
		private double stageValuePercentage;
		private double aTime;
		private double cCost;
		private double dCost;
		private double proceduralPenaltyAndNoShootCost;
		private double missCost;
		
		private ErrorCostTableLine(ScoreCard scoreCard) {
			this.scoreCard = scoreCard;
			stageValuePercentage = DataFormatUtils.round((double) scoreCard.getStage().getMaxPoints() / (double) matchTotalPoints * 100, 0); 
			aTime = DataFormatUtils.round(scoreCard.getStage().getMaxPoints() / scoreCard.getHitFactor(), 2);
			cCost = DataFormatUtils.round(cHitPointLoss / scoreCard.getHitFactor(), 2);
			dCost = DataFormatUtils.round(dHitPointLoss / scoreCard.getHitFactor(), 2);
			proceduralPenaltyAndNoShootCost = DataFormatUtils.round(proceduralAndNoShootPointLoss / scoreCard.getHitFactor(), 2);
			missCost = DataFormatUtils.round(missPointLoss / scoreCard.getHitFactor(), 2);
			
		}

		public ScoreCard getScoreCard() {
			return scoreCard;
		}

		public double getStageValuePercentage() {
			return stageValuePercentage;
		}

		public double getaTime() {
			return aTime;
		}

		public double getcCost() {
			return cCost;
		}

		public double getdCost() {
			return dCost;
		}

		public double getProceduralPenaltyAndNoShootCost() {
			return proceduralPenaltyAndNoShootCost;
		}

		public double getMissCost() {
			return missCost;
		}
	}

}
