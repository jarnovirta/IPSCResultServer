package fi.ipsc_result_server.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipsc_result_server.domain.MatchScore;
import fi.ipsc_result_server.domain.ScoreCard;
import fi.ipsc_result_server.domain.Stage;
import fi.ipsc_result_server.domain.StageScore;

@Service
public class MatchScoreService {
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	StageService stageService;
	
	@Autowired
	CompetitorService competitorService;

	@Autowired
	ScoreCardService scoreCardService;
	
	@Transactional
	public void save(MatchScore matchScore) {
		for (StageScore stageScore : matchScore.getStageScores()) {
			Stage stage = stageService.getOne(stageScore.getStageId());
			for (ScoreCard scoreCard : stageScore.getScoreCards()) {
				scoreCard.setStage(stage);
				scoreCard.setStageId(stage.getId());
				scoreCard.setCompetitor(competitorService.getOne(scoreCard.getCompetitorId()));
				scoreCard.setaHits(scoreCard.getaHits() + scoreCard.getPopperHits());
				scoreCard.setMisses(scoreCard.getMisses() + scoreCard.getPopperMisses());
				scoreCardService.save(scoreCard);
			}
		}
	}
}
