package fi.ipscResultServer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.Constants;
import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.repository.springJDBCRepository.ScoreCardRepository;
import fi.ipscResultServer.service.CompetitorService;
import fi.ipscResultServer.service.MatchService;
import fi.ipscResultServer.service.ScoreCardService;
import fi.ipscResultServer.service.StageService;

@Service
public class ScoreCardServiceImpl implements ScoreCardService {
	
	@Autowired
	private ScoreCardRepository scoreCardRepository;
	
	@Autowired
	private StageService stageService;
	
	@Autowired
	private CompetitorService competitorService;
	
	@Autowired 
	MatchService matchService;
	
	@Transactional
	public void save(List<ScoreCard> scoreCards) throws DatabaseException {
		scoreCardRepository.save(scoreCards);
	}
	
	public List<ScoreCard> findByMatch(Long matchId, boolean eager) {
		List<ScoreCard> cards = scoreCardRepository.findByMatch(matchId);
		if (eager) setReferencedInstances(cards);
		return cards;	
	}

	public List<ScoreCard> findByStageAndDivision(Long stageId, String division) {
		if (division.equals(Constants.COMBINED_DIVISION)) return scoreCardRepository.findByStage(stageId);
		else return scoreCardRepository.findByStageAndDivision(stageId, division);
	}

	@Transactional
	public void deleteByMatch(Long matchId) {
		scoreCardRepository.deleteByMatch(matchId);
	}
	
	public List<ScoreCard> findByCompetitor(Long competitorId, boolean eager) {
		List<ScoreCard> cards = scoreCardRepository.findByCompetitor(competitorId);
		if (eager) setReferencedInstances(cards);
		return cards;
	}
	
	public List<String> getDivisionsWithResults(Long matchId) {
		return scoreCardRepository.getDivisionsWithResults(matchId);
	}
	private void setReferencedInstances(ScoreCard card) {
		card.setStage(stageService.getOne(card.getStageId()));
		card.setCompetitor(competitorService.getOne(card.getCompetitorId()));
	}
	private void setReferencedInstances(List<ScoreCard> cards) {
		for (ScoreCard card : cards) setReferencedInstances(card);
	}
	
}
