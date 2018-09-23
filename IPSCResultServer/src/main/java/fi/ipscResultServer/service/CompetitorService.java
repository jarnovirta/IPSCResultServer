package fi.ipscResultServer.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.repository.CompetitorRepository;

@Service
public class CompetitorService {
	@Autowired
	private CompetitorRepository competitorRepository;
	
	public List<Competitor> findByMatch(Long matchId) throws DatabaseException {
		List<Competitor> competitors = competitorRepository.findByMatch(matchId);
		if (competitors != null) Collections.sort(competitors);
		return competitors;
	}
	
	public Competitor save(Competitor competitor) throws DatabaseException {
		return competitorRepository.save(competitor);
	}
	
	public Competitor getOne(Long id) throws DatabaseException {
		return competitorRepository.getOne(id);
	}
	
	public Competitor findByPractiScoreReferences(String practiScoreMatchId, String practiScoreCompetitorId) {
		return competitorRepository.findByPractiScoreReferences(practiScoreMatchId, practiScoreCompetitorId);
	}
}
