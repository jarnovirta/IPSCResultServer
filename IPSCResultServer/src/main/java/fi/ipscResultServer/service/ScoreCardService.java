package fi.ipscResultServer.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.IPSCDivision;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.repository.ScoreCardRepository;

@Service
public class ScoreCardService {
	@Autowired
	ScoreCardRepository scoreCardRepository;

	
	final static Logger logger = Logger.getLogger(ScoreCardService.class);
	
	@Transactional
	public ScoreCard save(ScoreCard scoreCard) {
		return scoreCardRepository.save(scoreCard);
	}
	
	public ScoreCard findByCompetitorAndStage(String competitorId, String stageId) {
		return scoreCardRepository.findByCompetitorAndStage(competitorId, stageId);
	}
	public List<ScoreCard> findByCompetitorAndMatch(String competitorId, String matchId) {
		return scoreCardRepository.findByCompetitorAndMatch(competitorId, matchId);		
	}
	@Transactional
	public void deleteInBatch(List<ScoreCard> scoreCards) {
		scoreCardRepository.deleteInBatch(scoreCards);
	}
	
	public List<ScoreCard> findByStageAndDivision(String stageId, IPSCDivision division) {
		return scoreCardRepository.findByStageAndDivision(stageId, division);
	}
	
	public List<ScoreCard> findByStage(String stageId) {
		return scoreCardRepository.findByStage(stageId);
	}
	
	// Delete ScoreCard. Needs to use properties other than id because ScoreCard data from PractiScore does not 
	// include id.
	@Transactional
	public void delete(ScoreCard scoreCard) {
		scoreCardRepository.delete(scoreCard);
	}
	@Transactional
	public void deleteByMatch(Match match) {
		scoreCardRepository.deleteByMatch(match);
	}
}
