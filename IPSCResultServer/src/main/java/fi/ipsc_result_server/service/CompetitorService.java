package fi.ipsc_result_server.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ipsc_result_server.domain.Competitor;
import fi.ipsc_result_server.repository.CompetitorRepository;

@Service
public class CompetitorService {
	@Autowired
	CompetitorRepository competitorRepository;
	
	public List<Competitor> findAll() {
		List<Competitor> competitors = competitorRepository.findAll();
		Collections.sort(competitors);
		return competitors;
	}
}
