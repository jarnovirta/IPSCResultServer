package fi.ipscResultServer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.repository.springJDBCRepository.CompetitorRepository;

@Service
public class CompetitorService {
	@Autowired
	private CompetitorRepository competitorRepository;
	
	public Competitor save(Competitor competitor) throws DatabaseException {
		
		return null;
	}
	
	public Competitor getOne(Long id) {

		return null;
	}
	
	public Competitor findByPractiScoreReferences(String practiScoreMatchId, String practiScoreCompetitorId) {
		return null;
	}
	
	public void save(List<Competitor> competitors) {
		if (competitors == null) return;
		competitorRepository.save(competitors);
		
	}
	
	public List<Competitor> findByMatch(Long matchId) {
		return competitorRepository.findByMatch(matchId);
	}
	public void delete(List<Competitor> competitors) {
		competitorRepository.delete(competitors);
	}
}
