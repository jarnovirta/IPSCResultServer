package fi.ipscResultServer.service.resultDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.IPSCDivision;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.ResultData.MatchResultData;
import fi.ipscResultServer.domain.ResultData.MatchResultDataLine;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.repository.MatchResultDataRepository;

@Service
public class MatchResultDataService {

	@Autowired
	MatchResultDataRepository matchResultDataRepository;
		
	public MatchResultData findByMatchAndDivision(String matchId, IPSCDivision division) 
			throws DatabaseException {
		return matchResultDataRepository.findByMatchAndDivision(matchId, division);
	}
	
	public MatchResultData findByMatch(String matchId) throws DatabaseException {
		return matchResultDataRepository.findByMatch(matchId);
	}
	
	public MatchResultDataLine findMatchResultDataLinesByCompetitor(Competitor competitor) 
			throws DatabaseException {
		return matchResultDataRepository.findMatchResultDataLinesByCompetitor(competitor);
	}
	
	@Transactional
	public MatchResultData save(MatchResultData matchResultData) throws DatabaseException {
		return matchResultDataRepository.save(matchResultData);
		
	}
	@Transactional
	public void deleteByMatch(Match match) throws DatabaseException {
		MatchResultData resultData = findByMatch(match.getId());
		if (resultData != null) matchResultDataRepository.delete(resultData); 
	}
}
