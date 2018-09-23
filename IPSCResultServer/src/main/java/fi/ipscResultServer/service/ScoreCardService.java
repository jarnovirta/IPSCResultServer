package fi.ipscResultServer.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.repository.ScoreCardRepository;

@Service
public class ScoreCardService {
	@Autowired
	private ScoreCardRepository scoreCardRepository;

	
	final static Logger logger = Logger.getLogger(ScoreCardService.class);
	
	@Transactional
	public List<ScoreCard> save(List<ScoreCard> scoreCard) throws DatabaseException {
		return scoreCardRepository.save(scoreCard);
	}
	
	public ScoreCard findByCompetitorAndStage(String competitorId, String stageId) throws DatabaseException  {
		return scoreCardRepository.findByCompetitorAndStage(competitorId, stageId);
	}
	public List<ScoreCard> findByCompetitorAndMatchPractiScoreIds(String competitorPractiScoreId, 
			String matchPractiScoreId) throws DatabaseException {
		return scoreCardRepository.findByCompetitorAndMatchPractiScoreIds(competitorPractiScoreId, matchPractiScoreId);		
	}
	@Transactional
	public void deleteInBatch(List<ScoreCard> scoreCards) throws DatabaseException {
		scoreCardRepository.deleteInBatch(scoreCards);
	}
	
	public List<ScoreCard> findByStageAndDivision(Long stageId, String division) throws DatabaseException {
		return scoreCardRepository.findByStageAndDivision(stageId, division);
	}
	
	public List<ScoreCard> findByStage(Long stageId) throws DatabaseException {
		return scoreCardRepository.findByStage(stageId);
	}
	
	// Delete ScoreCard. Needs to use properties other than id because ScoreCard data from PractiScore does not 
	// include id.
	@Transactional
	public void delete(ScoreCard scoreCard) throws DatabaseException {
		scoreCardRepository.delete(scoreCard);
	}
	@Transactional
	public void deleteByMatch(Match match) throws DatabaseException {
		scoreCardRepository.deleteByMatch(match);
	}
}
