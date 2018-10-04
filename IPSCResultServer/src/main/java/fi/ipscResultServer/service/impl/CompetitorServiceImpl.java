package fi.ipscResultServer.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.Constants;
import fi.ipscResultServer.repository.springJDBCRepository.CompetitorRepository;
import fi.ipscResultServer.service.CompetitorService;

@Service
public class CompetitorServiceImpl implements CompetitorService {
	
	@Autowired
	private CompetitorRepository competitorRepository;
	
	public Competitor getOne(Long id) {
		Competitor competitor = competitorRepository.getOne(id);
		return setCategories(competitor);
	}
	
	@Transactional
	public void save(List<Competitor> competitors) {
		if (competitors == null) return;
		competitorRepository.save(competitors);
	}
	
	public List<Competitor> findByMatchAndDivision(Long matchId, String division) {
		if (division.equals(Constants.COMBINED_DIVISION)) {
			return setCategories(competitorRepository.findByMatch(matchId));
		}
		return setCategories(competitorRepository.findByMatchAndDivision(matchId, division));
	}
	
	public Competitor findByPractiScoreReferences(String matchPractiScoreId, String competitorPractiScoreId) {
		return setCategories(competitorRepository.findByPractiScoreReferences(
				matchPractiScoreId, competitorPractiScoreId));
	}
	
	@Transactional
	public void deleteByMatch(Long matchId) {
		competitorRepository.deleteByMatch(matchId);
	}
	
	public Long getIdByPractiScoreReferences(String competitorPractiScoreId, String matchPractiScoreId) {
		return competitorRepository.getIdByPractiScoreReferences(competitorPractiScoreId, matchPractiScoreId);
	}
	
	@Transactional
	public void setDnf(Long competitorId) {
		competitorRepository.setDnf(competitorId);
	}
	
	private List<Competitor> setCategories(List<Competitor> competitors) {
		Set<Long> idList = new HashSet<Long>();
		for (Competitor comp : competitors) {
			idList.add(comp.getId());
		}
		List<Object[]> categories = competitorRepository.getCategories(idList);
		for (Object[] categoryArray : categories) {
			for (Competitor comp : competitors) {
				if (comp.getCategories() == null) {
					comp.setCategories(new ArrayList<String>());
				}
				if (((Long) categoryArray[0]).equals(comp.getId())) {
					comp.getCategories().add((String) categoryArray[1]); 
				}
				
			}
		}
		return competitors;
	}
	private Competitor setCategories(Competitor competitor) {
		List<Competitor> compList = new ArrayList<Competitor>();
		compList.add(competitor);
		return setCategories(compList).get(0);
	}
}
