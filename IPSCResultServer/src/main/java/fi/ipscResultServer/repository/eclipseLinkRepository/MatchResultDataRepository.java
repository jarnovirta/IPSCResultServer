package fi.ipscResultServer.repository.eclipseLinkRepository;

import java.util.List;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.resultData.MatchResultData;
import fi.ipscResultServer.domain.resultData.MatchResultDataLine;
import fi.ipscResultServer.exception.DatabaseException;

public interface MatchResultDataRepository {
	public MatchResultData findByMatchAndDivision(Long matchId, String division) throws DatabaseException;
		
	public List<MatchResultData> find(Long matchId) throws DatabaseException;
		
	public MatchResultDataLine findMatchResultDataLinesByCompetitor(Competitor competitor) throws DatabaseException;
	
	public MatchResultData save(MatchResultData matchResultData) throws DatabaseException;
	
	public void deleteInBatch(List<MatchResultData> matchResultData) throws DatabaseException;
	
}
