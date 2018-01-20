package fi.ipscResultServer.repository;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.IPSCDivision;
import fi.ipscResultServer.domain.ResultData.MatchResultData;
import fi.ipscResultServer.domain.ResultData.MatchResultDataLine;

public interface MatchResultDataRepository {
	public MatchResultData findByMatchAndDivision(String matchId, IPSCDivision division);
		
	public MatchResultData findByMatch(String matchId);
		
	public MatchResultDataLine findMatchResultDataLinesByCompetitor(Competitor competitor);
	
	public MatchResultData save(MatchResultData matchResultData);
	
	public void delete(MatchResultData matchResultData);
	
}
