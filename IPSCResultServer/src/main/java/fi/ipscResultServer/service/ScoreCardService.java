package fi.ipscResultServer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.repository.springJDBCRepository.ScoreCardRepository;

@Service
public class ScoreCardService {
	
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
	
	public ScoreCard findByCompetitorAndStage(String competitorId, String stageId) throws DatabaseException  {
		return null;
	}
	
	public List<ScoreCard> findByMatch(Long matchId) {
		List<ScoreCard> cards = scoreCardRepository.findByMatch(matchId);
		setReferencedInstances(cards);
		return cards;	
	}
	
	@Transactional
	public void deleteInBatch(List<Long> scoreCardIds) throws DatabaseException {
		scoreCardRepository.delete(scoreCardIds);
	}
	
	public List<ScoreCard> findByStage(Long stageId) {
		List<ScoreCard> cards = scoreCardRepository.findByStage(stageId);
		return cards;
	}
	public List<ScoreCard> findByStageAndDivision(Long stageId, String division) {
		List<ScoreCard> cards = scoreCardRepository.findByStageAndDivision(stageId, division);
		return cards;
	}
	
	// Delete ScoreCard. Needs to use properties other than id because ScoreCard data from PractiScore does not 
	// include id.
	@Transactional
	public void delete(ScoreCard scoreCard) throws DatabaseException {
		
	}
	@Transactional
	public void deleteByMatch(Long matchId) {
		scoreCardRepository.deleteByMatch(matchId);
	}
	
	public List<ScoreCard> findByCompetitor(Long competitorId) {
		List<ScoreCard> cards = scoreCardRepository.findByCompetitor(competitorId);
		setReferencedInstances(cards);
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
