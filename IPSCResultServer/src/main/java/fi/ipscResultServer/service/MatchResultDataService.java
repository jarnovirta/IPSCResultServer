package fi.ipscResultServer.service;

import org.springframework.stereotype.Component;

import fi.ipscResultServer.domain.resultData.MatchResultData;

@Component
public interface MatchResultDataService {
	
	public MatchResultData findByMatchAndDivision(Long matchId, String division);

	public void save(MatchResultData matchResultData);
	
	public void deleteByMatch(Long matchId);
	
	public MatchResultData generateMatchResultListing(Long matchId);
	
}
