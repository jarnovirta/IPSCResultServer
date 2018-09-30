package fi.ipscResultServer.service;

import org.springframework.stereotype.Component;

import fi.ipscResultServer.domain.resultData.CompetitorResultData;

@Component
public interface CompetitorResultDataService {

	public CompetitorResultData getCompetitorResultData(Long competitorId);
	
}
