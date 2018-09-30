package fi.ipscResultServer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.repository.springJDBCRepository.CompetitorRepository;
import fi.ipscResultServer.service.CompetitorService;

@Service
public class CompetitorServiceImpl implements CompetitorService {
	
	@Autowired
	private CompetitorRepository competitorRepository;
	
	public Competitor getOne(Long id) {
		Competitor competitor = competitorRepository.getOne(id);
		competitor.setCategories(competitorRepository.getCategories(id));
		return competitor;
	}
	
	@Transactional
	public void save(List<Competitor> competitors) {
		if (competitors == null) return;
		competitorRepository.save(competitors);
		
	}
	
	public List<Competitor> findByMatch(Long matchId) {
		return competitorRepository.findByMatch(matchId);
	}
	
	public Competitor findByPractiScoreReferences(String matchPractiScoreId, String competitorPractiScoreId) {
		return competitorRepository.findByPractiScoreReferences(matchPractiScoreId, competitorPractiScoreId);
	}
	
	@Transactional
	public void deleteByMatch(Long matchId) {
		competitorRepository.deleteByMatch(matchId);
	}
}
