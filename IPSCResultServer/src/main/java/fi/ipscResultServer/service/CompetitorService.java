package fi.ipscResultServer.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.controller.home.HomePageController;
import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.repository.CompetitorRepository;

@Service
public class CompetitorService {
	@Autowired
	private CompetitorRepository competitorRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	final static Logger logger = Logger.getLogger(HomePageController.class);
	
	public List<Competitor> findByMatch(Long matchId) throws DatabaseException {
		List<Competitor> competitors = competitorRepository.findByMatch(matchId);
		Collections.sort(competitors);
		setEncodedURL(competitors);
		return competitors;
	}
	
	@Transactional
	public Competitor save(Competitor competitor) throws DatabaseException {
		return competitorRepository.save(competitor);
	}
	
	public Competitor getOne(Long id) throws DatabaseException {
		return setEncodedURL(competitorRepository.getOne(id));
	}
	
	public Competitor findByPractiScoreReferences(String practiScoreMatchId, String practiScoreCompetitorId) {
		return setEncodedURL(competitorRepository.findByPractiScoreReferences(practiScoreMatchId, practiScoreCompetitorId));
	}

	public Competitor setEncodedURL(Competitor competitor) {
		try {
			competitor.setUrlEncodedPractiScoreId(URLEncoder.encode(competitor.getPractiScoreId(), "UTF-8"));
			return competitor;
		}
		catch (UnsupportedEncodingException e) {
			logger.error(e);
			return null;
		}
	}
	public void setEncodedURL(List<Competitor> competitors) {
		for (Competitor competitor : competitors) setEncodedURL(competitor);
	}
}
