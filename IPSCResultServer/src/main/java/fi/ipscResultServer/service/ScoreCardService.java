package fi.ipscResultServer.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.repository.springJDBCRepository.ScoreCardRepository;

@Service
public class ScoreCardService {
	
	@Autowired
	private ScoreCardRepository scoreCardRepository;
	
	private final static Logger LOGGER = Logger.getLogger(ScoreCardService.class);
	
	@Transactional
	public void save(List<ScoreCard> scoreCards) throws DatabaseException {
		scoreCardRepository.save(scoreCards);
	}
	
	public ScoreCard findByCompetitorAndStage(String competitorId, String stageId) throws DatabaseException  {
		return null;
	}
	public List<ScoreCard> findByCompetitorAndMatchPractiScoreIds(String competitorPractiScoreId, 
			String matchPractiScoreId) throws DatabaseException {
		return null;		
	}
	@Transactional
	public void deleteInBatch(List<Long> scoreCardIds) throws DatabaseException {
		scoreCardRepository.delete(scoreCardIds);
	}
	
	public List<ScoreCard> findByStageAndDivision(Long stageId, String division) throws DatabaseException {
		return null;
	}
	
	public List<ScoreCard> findByStage(Long stageId) throws DatabaseException {
		return null;
	}
	
	// Delete ScoreCard. Needs to use properties other than id because ScoreCard data from PractiScore does not 
	// include id.
	@Transactional
	public void delete(ScoreCard scoreCard) throws DatabaseException {
		
	}
	@Transactional
	public void deleteByMatch(Match match) throws DatabaseException {
		
	}
}
