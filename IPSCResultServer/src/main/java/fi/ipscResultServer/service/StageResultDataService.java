package fi.ipscResultServer.service;

import org.springframework.stereotype.Component;

import fi.ipscResultServer.domain.resultData.StageResultData;

@Component
public interface StageResultDataService {

	public StageResultData getStageResultListing(String matchPractiScoreId, String stagePractiScoreId, String division);
	
}
