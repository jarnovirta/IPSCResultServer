package fi.ipscResultServer.repository;

import java.util.List;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.resultData.MatchResultData;
import fi.ipscResultServer.domain.resultData.MatchResultDataLine;
import fi.ipscResultServer.exception.DatabaseException;

public interface MatchResultDataRepository {
	public MatchResultData findByMatchAndDivision(String matchId, String division) throws DatabaseException;
		
	public List<MatchResultData> findByMatch(String matchId) throws DatabaseException;
		
	public MatchResultDataLine findMatchResultDataLinesByCompetitor(Competitor competitor) throws DatabaseException;
	
	public MatchResultData save(MatchResultData matchResultData) throws DatabaseException;
	
	public void delete(MatchResultData matchResultData) throws DatabaseException;
	
}
