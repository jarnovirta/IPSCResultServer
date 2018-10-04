package fi.ipscResultServer.repository.springJDBCRepository;

import java.util.List;

import org.springframework.stereotype.Component;

import fi.ipscResultServer.domain.resultData.MatchResultDataLine;

@Component
public interface ResultDataRepository {
	
	public List<MatchResultDataLine> findResultDataLinesByMatchAndDivision(Long matchId, String division);
	
	public List<MatchResultDataLine> findResultDataLinesByMatch(Long matchId);
	
}

