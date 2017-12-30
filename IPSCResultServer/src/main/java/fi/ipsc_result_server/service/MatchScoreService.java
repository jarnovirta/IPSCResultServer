package fi.ipsc_result_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ipsc_result_server.domain.MatchScore;
import fi.ipsc_result_server.repository.MatchScoreRepository;

@Service
public class MatchScoreService {
	@Autowired
	MatchScoreRepository matchScoreRepository;
	public MatchScore save(MatchScore matchScore) {
		return matchScoreRepository.save(matchScore);
	}
}
