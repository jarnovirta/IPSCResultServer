package fi.ipscResultServer.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.MatchStatus;
import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.domain.User;
import fi.ipscResultServer.exception.DatabaseException;
import fi.ipscResultServer.repository.springJDBCRepository.MatchRepository;

@Service
public class MatchService {
	
	@Autowired
	private MatchRepository matchRepository;
	
	@Autowired
	private StageService stageService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CompetitorService competitorService;
	
	private final static Logger LOGGER = Logger.getLogger(MatchService.class);

	@Transactional
	public Match save(Match match) {
		prepareStagesForSave(match);
		
		boolean admin = userService.isCurrentUserAdmin();
		if (admin == true) match.setUploadedByAdmin(true);
		else match.setUser(userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));	
		
		match = matchRepository.save(match);
		
		// Save competitors and get saved instances with IDs
		prepareCompetitorsForSave(match);
		competitorService.save(match.getCompetitors());
		match.setCompetitors(competitorService.findByMatch(match.getId()));
		
		// Save stages and get saved instances with IDs
		stageService.save(match.getStages());
		match.setStages(stageService.findByMatch(match.getId()));
		
		
		return match;
	}

	private void prepareCompetitorsForSave(Match match) {
		if (match.getCompetitors() != null) {
			List<Competitor> deletedCompetitors = new ArrayList<Competitor>();
			int competitorNumber = 1;
			
			for (Competitor competitor : match.getCompetitors()) {
				competitor.setShooterNumber(competitorNumber++);
				competitor.setMatch(match);

				// Remove deleted competitors
				if (competitor.isDeleted() == true) {
					deletedCompetitors.add(competitor);
				}
			}
			match.getCompetitors().removeAll(deletedCompetitors);
		}
	}

	public void prepareStagesForSave(Match match) {
		if (match.getStages() != null) {
			List<Stage> removeStages = new ArrayList<Stage>();
			for (Stage stage : match.getStages()) {
				stage.setMatch(match);
				if (stage.isDeleted() == true || (stage.getScoreType() != null && stage.getScoreType().equals("Chrono"))) {
					removeStages.add(stage);
				}
			}
			match.getStages().removeAll(removeStages);
		}
	}
	
	public List<Match> findAll() {
//		return matchRepository.findAll();
		return null;
	}
	
	public Competitor findByPractiScoreId(String id, Match match) {
		return null;
	}
	
	// Returns Match list with only necessary properties set for listing purposes
	// instead of full instances with a list of Stages etc.
	public List<Match> getFullMatchList() {
//		return matchRepository.getFullMatchList();
		return null;
	}
	
	public List<Match> getMatchListForUser(User user) {

		return null;
	}
	public Match getOne(Long id) {
		return matchRepository.getOne(id);
	}
	
	public Match findByPractiScoreId(String practiScoreId) throws DatabaseException {
		return matchRepository.findByPractiScoreId(practiScoreId);
		
	}
	@Transactional
	public void delete(Match match) {
		LOGGER.debug("*** DELETING MATCH ");
		System.out.println("Deleting competitors");
		competitorService.delete(match.getCompetitors());
		System.out.println("Deleting stages");
		stageService.delete(match.getStages());
		
	}
	@Transactional
	public void setMatchStatus(Long matchId, MatchStatus newStatus) throws DatabaseException {
//		Match match = getOne(matchId);
//		match.setStatus(newStatus);
	}
}
