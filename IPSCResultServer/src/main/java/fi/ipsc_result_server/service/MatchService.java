package fi.ipsc_result_server.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipsc_result_server.domain.IPSCDivision;
import fi.ipsc_result_server.domain.Match;
import fi.ipsc_result_server.repository.CompetitorRepository;
import fi.ipsc_result_server.repository.MatchRepository;

@Service
public class MatchService {
	@Autowired
	private MatchRepository matchRepository;
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	CompetitorRepository competitorRepository;
	
	@Autowired
	ResultDataService resultDataService;
	
	@Transactional
	public Match save(Match match) {
		System.out.println("*** Merging match");
		if (match.getDivisionsWithResults() == null) {
			List<IPSCDivision> divisionsWithResults = new ArrayList<IPSCDivision>();
			divisionsWithResults.add(IPSCDivision.COMBINED);
			match.setDivisionsWithResults(divisionsWithResults);
		}
		return entityManager.merge(match);
	}
	
	public List<Match> findAll() {
		return matchRepository.findAll();
	}
	
	public Match getOne(String id) {
		return matchRepository.getOne(id);
	}
}
