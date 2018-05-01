package fi.ipscResultServer.service.resultDataService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.resultData.MatchResultData;
import fi.ipscResultServer.domain.resultData.MatchResultDataLine;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.repository.MatchResultDataRepository;

@Service
public class MatchResultDataService {

	@Autowired
	MatchResultDataRepository matchResultDataRepository;
		
	public MatchResultData findByMatchAndDivision(Long matchId, String division) 
			throws DatabaseException {
		return matchResultDataRepository.findByMatchAndDivision(matchId, division);
	}
	
	public List<MatchResultData> findByMatch(Match match) throws DatabaseException {
		return matchResultDataRepository.find(match);
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
		List<MatchResultData> resultDataList = findByMatch(match);
		for (MatchResultData resultData : resultDataList) {
			matchResultDataRepository.delete(resultData);
		}
	}
}
