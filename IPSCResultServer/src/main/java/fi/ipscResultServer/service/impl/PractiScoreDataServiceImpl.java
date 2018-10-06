package fi.ipscResultServer.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import fi.ipscResultServer.service.CompetitorService;
import fi.ipscResultServer.service.MatchService;
import fi.ipscResultServer.service.PractiScoreDataService;
import fi.ipscResultServer.service.ScoreCardService;

@Service
public class PractiScoreDataServiceImpl implements PractiScoreDataService {
	@Autowired
	MatchService matchService;
	
	@Autowired
	private ScoreCardService scoreCardService;
	
	@Autowired
	private CompetitorService competitorService;
	
	
	public void save(PractiScoreMatchData matchData) throws DatabaseException {
		Match match = matchData.getMatch();
		matchService.deleteByPractiScoreId(match.getPractiScoreId());
		match = matchService.save(match);
		List<ScoreCard> cards = prepareStageScoresForSave(matchData.getMatchScore().getStageScores(), match);
		scoreCardService.save(cards);
	}
	
	private List<ScoreCard> prepareStageScoresForSave(List<PractiScoreStageScore> stageScores, Match match) {
		
		List<ScoreCard> resultScoreCardsList = new ArrayList<ScoreCard>();
		if (match.getDivisions() == null) match.setDivisions(new ArrayList<String>());
		if (match.getDivisionsWithResults() == null) match.setDivisionsWithResults(new ArrayList<String>());
		
		for (PractiScoreStageScore stageScore : stageScores) {
			Stage stage = getStageByPractiScoreId(match.getStages(), stageScore.getStagePractiScoreId());
			List<ScoreCard> scoreCards = stageScore.getScoreCards();
			setReferencesInScoreCards(scoreCards, stage, match);
			scoreCards = removeCardsForInvalidStagesAndCompetitors(scoreCards);
			setHitsDataInScoreCards(scoreCards);
			setDnfForCompetitors(scoreCards);
			Collections.sort(scoreCards);
			for (String division : match.getDivisions()) {
				setStageResultDataInScoreCards(scoreCards, stage, division);
			}
			if (match.getDivisions().size() > 1) {
				setStageResultDataInScoreCards(scoreCards, stage, Constants.COMBINED_DIVISION); 
			}
					
			resultScoreCardsList.addAll(scoreCards);
		}
		return resultScoreCardsList;
	}	
	
	private void setHitsDataInScoreCards(List<ScoreCard> cards) {
		for (ScoreCard scoreCard : cards) scoreCard.setHitsAndPoints();
	}
	
	public void setDnfForCompetitors(List<ScoreCard> cards) {
		for (ScoreCard scoreCard : cards) {
			if (scoreCard.isDnf()) competitorService.setDnf(scoreCard.getCompetitor().getId());
		}
	}
	
	public void setStageResultDataInScoreCards(List<ScoreCard> cards, Stage stage, String division) {
		double topHitFactor = -1;
		double topPoints = -1;
		for (ScoreCard scoreCard : cards) {
			// Skip competitors in other divisions unless setting result data for combined division. Also skip disqualified competitors
			if (scoreCard.getCompetitor().isDisqualified()
					|| (!division.equals(Constants.COMBINED_DIVISION) 
					&& !scoreCard.getCompetitor().getDivision().equals(division))) continue;
			
			// Set stage result points, percentage and rank
			if (topHitFactor == -1 || scoreCard.getHitFactor() > topHitFactor) topHitFactor = scoreCard.getHitFactor();

			double points;
			if (topHitFactor > 0) points = (scoreCard.getHitFactor() / topHitFactor) * stage.getMaxPoints();
			else points = stage.getMaxPoints();
			if (points == -1 || points > topPoints) topPoints = points;
			
			double scorePercentage = -1;
			if (topPoints > 0) scorePercentage = points / topPoints * 100;
			
			if (division.equals(Constants.COMBINED_DIVISION)) {
				scoreCard.setCombinedDivisionStagePoints(points);
				scoreCard.setCombinedDivisionScorePercentage(scorePercentage);
			}
			else {
				scoreCard.setStagePoints(points);
				scoreCard.setScorePercentage(scorePercentage);
			}
		}
	}
	
	private void setReferencesInScoreCards(List<ScoreCard> cards, Stage stage, Match match) {
		for (ScoreCard card : cards) {
			card.setStage(stage);
			card.setCompetitor(getCompetitorByPractiScoreId(match.getCompetitors(), card.getCompetitorPractiScoreId()));
			card.setStage(stage);
		}
	}
	
	private Stage getStageByPractiScoreId(List<Stage> stages, String practiScroreId) {
		for (Stage stage : stages) if (stage.getPractiScoreId().equals(practiScroreId)) return stage;
		return null;
	}
	
	private Competitor getCompetitorByPractiScoreId(List<Competitor> competitors, String id) {
		for (Competitor comp : competitors) if (comp.getPractiScoreId().equals(id)) return comp;
		return null;
	}
	private List<ScoreCard> removeCardsForInvalidStagesAndCompetitors(List<ScoreCard> cards) {
		List<ScoreCard> resultCards = new ArrayList<ScoreCard>();
		for (ScoreCard card : cards) {
			// Remove cards if competitor or stage has been deleted

			if (card.getCompetitor() != null && !card.getCompetitor().isDeleted() && card.getStage() != null) {
				resultCards.add(card);
			}
		}
		return resultCards;
	}
}
