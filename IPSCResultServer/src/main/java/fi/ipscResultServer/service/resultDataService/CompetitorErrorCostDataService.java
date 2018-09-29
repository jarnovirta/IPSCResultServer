package fi.ipscResultServer.service.resultDataService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fi.ipscResultServer.controller.matchAnalysis.ErrorCostTableLine;
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
	
	public static Map<String, ErrorCostTableLine> getErrorCostTableLines(Match match, Competitor competitor, 
			List<ScoreCard> scoreCards) {
		
		List<ScoreCard> nonNullCards = new ArrayList<ScoreCard>();
		for (ScoreCard card : scoreCards) {
			if (card != null) {
				nonNullCards.add(card);
			}
		}		
		scoreCards = nonNullCards;
		
		Map<String, ErrorCostTableLine> errorCostTableLines = new HashMap<String, ErrorCostTableLine>();
		
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
			ErrorCostTableLine line = new ErrorCostTableLine();
			line.setScoreCard(card);
			line.setStageValuePercentage(new Double(DataFormatUtils.round((double) card.getStage().getMaxPoints() / (double) matchTotalPoints * 100, 0)).intValue());
			if (card.getHitFactor() > 0) {
				line.setaTime(DataFormatUtils.round(card.getStage().getMaxPoints() / card.getHitFactor(), 2));
				line.setcCost(DataFormatUtils.round(cHitPointLoss / card.getHitFactor(), 2));
				line.setdCost(DataFormatUtils.round(dHitPointLoss / card.getHitFactor(), 2));
				line.setProceduralPenaltyAndNoShootCost(DataFormatUtils.round(proceduralAndNoShootPointLoss / card.getHitFactor(), 2));
				line.setMissCost(DataFormatUtils.round(missPointLoss / card.getHitFactor(), 2));
			}
			errorCostTableLines.put(card.getStage().getPractiScoreId(), line);
		}
		for (Stage stage : match.getStages()) {
			matchTotalPoints += stage.getMaxPoints();
		}
		
		return errorCostTableLines;
	}
}
