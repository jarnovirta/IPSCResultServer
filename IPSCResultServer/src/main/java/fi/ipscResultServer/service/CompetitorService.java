package fi.ipscResultServer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.repository.springJDBCRepository.CompetitorRepository;

@Service
public class CompetitorService {
	@Autowired
	private CompetitorRepository competitorRepository;
	
	@Autowired
	private MatchService matchService;
	
	public Competitor getOne(Long id) {
		Competitor competitor = competitorRepository.getOne(id);
		competitor.setMatch(matchService.getOne(competitor.getMatchId()));
		competitor.setCategories(competitorRepository.getCategories(id));
		return competitor;
	}
	
	public Competitor lazyGetOne(Long id) {
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
	
	public Long getIdByPractiScoreReferences(String competitorPractiScoreId, String matchPractiScoreId) {
		return competitorRepository.getIdByPractiScoreReferences(competitorPractiScoreId, matchPractiScoreId);
	}
}
