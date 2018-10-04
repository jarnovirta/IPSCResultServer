package fi.ipscResultServer.service;

import org.springframework.stereotype.Component;

import fi.ipscResultServer.domain.resultData.MatchResultData;

@Component
public interface MatchResultDataService {
	
	public MatchResultData findByMatchAndDivision(String matchPractiScoreId, String division);

}
