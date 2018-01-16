package fi.ipsc_result_server.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipsc_result_server.domain.Competitor;
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

		if (match.getCompetitors() != null) {
			int competitorNumber = 1;
			for (Competitor competitor : match.getCompetitors()) {
				competitor.setShooterNumber(competitorNumber++);
			}
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
