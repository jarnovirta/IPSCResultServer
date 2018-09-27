package fi.ipscResultServer.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.Constants;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.domain.practiScore.PractiScoreMatchData;
import fi.ipscResultServer.domain.practiScore.PractiScoreStageScore;
import fi.ipscResultServer.exception.DatabaseException;

@Service
public class PractiScoreMatchDataService {
	@Autowired
	MatchService matchService;
	
	@Autowired
	private ScoreCardService scoreCardService;
	
	final static Logger LOGGER = Logger.getLogger(PractiScoreMatchDataService.class);
	
	public void save(PractiScoreMatchData matchData) throws DatabaseException {
		Match match = matchData.getMatch();
		
		// Delete old match data
		Match oldMatch = matchService.findByPractiScoreId(match.getPractiScoreId());
		if (oldMatch != null) {
			matchService.delete(oldMatch);
		}
		
		// Save match
		match = matchService.save(match);

		// Save result data
		prepareStageScoresForSave(matchData.getMatchScore().getStageScores(), match);
		scoreCardService.save(getMatchScoreCards(matchData));
				
		LOGGER.info("**** MATCH SAVE DONE ****");
	}
	
	private List<ScoreCard> getMatchScoreCards(PractiScoreMatchData matchData) {
		List<ScoreCard> scoreCards = new ArrayList<ScoreCard>();
		for (PractiScoreStageScore ss : matchData.getMatchScore().getStageScores()) {
			scoreCards.addAll(ss.getScoreCards());
		}
	return scoreCards;
	}
	private void prepareStageScoresForSave(List<PractiScoreStageScore> stageScores, Match match) {
		if (match.getDivisions() == null) match.setDivisions(new ArrayList<String>());
		if (match.getDivisionsWithResults() == null) match.setDivisionsWithResults(new ArrayList<String>());
		
		for (PractiScoreStageScore stageScore : stageScores) {
			Stage stage = null;
			for (Stage savedStage : match.getStages()) {
				if (savedStage.getPractiScoreId().equals(stageScore.getStagePractiScoreId())) {
					stage = savedStage;
				}
			}
					
			List<ScoreCard> cardsToRemove = new ArrayList<ScoreCard>();
			
			for (ScoreCard scoreCard : stageScore.getScoreCards()) {
				// Set competitor
				Competitor competitor = null;
				for (Competitor comp : match.getCompetitors()) {
					if (comp.getPractiScoreId().equals(scoreCard.getCompetitorPractiScoreId())) competitor = comp;
				}
				
				// Remove cards if competitor or stage has been deleted
				if (competitor == null || competitor.isDeleted() || stage == null) {
					cardsToRemove.add(scoreCard);
					continue;
				}
				
				scoreCard.setCompetitor(competitor);
				scoreCard.setHitsAndPoints();
				scoreCard.setStage(stage);
				
				String scoreCardDivision = scoreCard.getCompetitor().getDivision();

				if (!match.getDivisionsWithResults().contains(scoreCardDivision))  {
					match.getDivisionsWithResults().add(scoreCardDivision);
				}
			}
			stageScore.getScoreCards().removeAll(cardsToRemove);
			
			// If match has results for more than one division, add IPSCDivision combined to match for result listing purposes.
			if (match.getDivisionsWithResults().size() > 1 && !match.getDivisionsWithResults().contains(Constants.COMBINED_DIVISION)) {
				match.getDivisionsWithResults().add(Constants.COMBINED_DIVISION);
			}
		}
	}
}
