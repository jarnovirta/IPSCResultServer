package fi.ipscResultServer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.repository.springJDBCRepository.CompetitorRepository;

@Service
public class CompetitorService {
	@Autowired
	private CompetitorRepository competitorRepository;
	
	@Transactional
	public Competitor save(Competitor competitor) throws DatabaseException {
		
		return null;
	}
	
	public Competitor getOne(Long id) {

		return null;
	}
	@Transactional
	public void save(List<Competitor> competitors) {
		if (competitors == null) return;
		competitorRepository.save(competitors);
		
	}
	
	public List<Competitor> findByMatch(Long matchId) {
		return competitorRepository.findByMatch(matchId);
	}
	@Transactional
	public void deleteByMatch(Long matchId) {
		competitorRepository.deleteByMatch(matchId);
	}
}
