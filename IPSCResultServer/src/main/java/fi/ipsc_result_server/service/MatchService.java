package fi.ipsc_result_server.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipsc_result_server.domain.Competitor;
import fi.ipsc_result_server.domain.Match;
import fi.ipsc_result_server.repository.CompetitorRepository;
import fi.ipsc_result_server.repository.MatchRepository;

@Service
public class MatchService {
	@Autowired
	private MatchRepository matchRepository;
	
	@Autowired
	private CompetitorService competitorService;
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	CompetitorRepository competitorRepository;
	
	@Transactional
	public Match save(Match match) {
		List<Competitor> savedCompetitors = new ArrayList<Competitor>();
		for (Competitor competitor : match.getCompetitors()) {
			savedCompetitors.add(competitorService.save(competitor));
		}
		match.setCompetitors(savedCompetitors);

		System.out.println("Merging match");
		return entityManager.merge(match);
	}
	
	public List<Match> findAll() {
		return matchRepository.findAll();
	}
	
	public Match findOne(String id) {
		return matchRepository.findOne(id);
	}
}
