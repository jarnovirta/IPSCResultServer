package fi.ipscResultServer.service;

import org.springframework.stereotype.Component;

import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.domain.resultData.StageResultData;

@Component
public interface StageResultDataService {

	public StageResultData getStageResultListing(Stage stage, String division);
	
}
