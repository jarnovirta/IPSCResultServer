package fi.ipsc_result_server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.ipsc_result_server.domain.Match;
import fi.ipsc_result_server.repository.MatchRepository;

@Service
public class MatchService {
	@Autowired
	private MatchRepository matchRepository;
	
	public Match save(Match match) {
		return matchRepository.save(match);
	}
}
