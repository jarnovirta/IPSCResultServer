package fi.ipscResultServer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.Constants;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.MatchStatus;
import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.repository.springJDBCRepository.MatchRepository;
import fi.ipscResultServer.service.CompetitorService;
import fi.ipscResultServer.service.MatchService;
import fi.ipscResultServer.service.ScoreCardService;
import fi.ipscResultServer.service.StageService;
import fi.ipscResultServer.service.UserService;

@Service
public class MatchServiceImpl implements MatchService {
	
	@Autowired
	private MatchRepository matchRepository;
	
	@Autowired
	private StageService stageService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CompetitorService competitorService;
	
	@Autowired
	private ScoreCardService scoreCardService;

	@Transactional
	public Match save(Match match) {
		prepareStagesForSave(match);
		
		boolean admin = userService.isCurrentUserAdmin();
		if (admin == true) match.setUploadedByAdmin(true);
		else match.setUser(userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));	
		
		match = matchRepository.save(match);
		if (match.getUser() != null) match.setUser(userService.saveOrUpdate(match.getUser()));
		
		// Save competitors and get saved instances with IDs
		prepareCompetitorsForSave(match);
		competitorService.save(match.getCompetitors());
		match.setCompetitors(competitorService.findByMatchAndDivision(match.getId(), Constants.COMBINED_DIVISION));
		
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
		return matchRepository.findAll();
	}
	
	public List<Match> findByUser(Long userId) {
		return matchRepository.findByUser(userId);
	}
	public Match getOne(Long id, boolean eager) {
		Match match = matchRepository.getOne(id);
		match.setDivisionsWithResults(scoreCardService.getDivisionsWithResults(id));
		if (match.getDivisionsWithResults().size() > 1) match.getDivisionsWithResults().add(Constants.COMBINED_DIVISION);
		if (eager) {
			match.setCompetitors(competitorService.findByMatchAndDivision(match.getId(), Constants.COMBINED_DIVISION));
			match.setStages(stageService.findByMatch(match.getId()));
			match.setDivisions(getDivisions(id));
			if (match.getUserId() != null) match.setUser(userService.getOne(match.getUserId()));
		}
		return match;
	}

	public List<String> getDivisions(Long matchId) {
		return matchRepository.getDivisions(matchId);
	}
	public Long getIdByPractiScoreId(String practiScoreId) {
		return matchRepository.getIdByPractiScoreId(practiScoreId);
	}
	
	@Transactional
	public void deleteByPractiScoreId(String matchPractiScoreId) {
		List<Long> ids = matchRepository.getAllIdsByPractiScoreId(matchPractiScoreId);
		if (ids != null) {
			for (Long id : ids) {
				delete(id);
			}
		}
	}
	
	@Transactional
	public void delete(Long id) {
		scoreCardService.deleteByMatch(id);
		competitorService.deleteByMatch(id);
		stageService.deleteByMatch(id);
		matchRepository.delete(id);
	}
	@Transactional
	public void setStatus(Long matchId, MatchStatus newStatus) {
		matchRepository.setStatus(matchId, newStatus);
	}
}
