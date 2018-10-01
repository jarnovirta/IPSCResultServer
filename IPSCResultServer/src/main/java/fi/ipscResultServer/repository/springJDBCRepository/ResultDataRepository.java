package fi.ipscResultServer.repository.springJDBCRepository;

import java.util.List;

import org.springframework.stereotype.Component;

import fi.ipscResultServer.domain.resultData.MatchResultData;
import fi.ipscResultServer.domain.resultData.MatchResultDataLine;

@Component
public interface ResultDataRepository {

	public void deleteByMatch(Long matchId);
	
	public void save(MatchResultData matchResultData);
	
	public MatchResultData findByMatchAndDivision(Long matchId, String division);
	
	public List<MatchResultDataLine> getDataLines(Long matchResultDataId);
	
	public MatchResultDataLine findLineByCompetitor(Long competitorId, String division);
	
	public List<MatchResultDataLine> findResultDataLinesByMatchAndDivision(Long matchId, String division);
	
	public List<MatchResultDataLine> findResultDataLinesByMatch(Long matchId);
	
}

