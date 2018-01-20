package fi.ipscResultServer.service;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.repository.CompetitorRepository;

@Service
public class CompetitorService {
	@Autowired
	CompetitorRepository competitorRepository;
	
	@PersistenceContext
	EntityManager entityManager;
	
	public List<Competitor> findAll() {
		List<Competitor> competitors = competitorRepository.findAll();
		Collections.sort(competitors);
		return competitors;
	}
	
	@Transactional
	public Competitor save(Competitor competitor) {
		return competitorRepository.save(competitor);
	}
	
	public Competitor getOne(String id) {
		return competitorRepository.getOne(id);
	}
}
