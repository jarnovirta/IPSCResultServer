package fi.ipsc_result_server.service;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipsc_result_server.domain.Competitor;
import fi.ipsc_result_server.repository.CompetitorRepository;

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
		return entityManager.merge(competitor);
	}
	
	public Competitor getOne(String id) {
		return competitorRepository.getOne(id);
	}
}
