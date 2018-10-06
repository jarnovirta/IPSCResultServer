package fi.ipscResultServer.repository.springJDBCRepository;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import fi.ipscResultServer.domain.Competitor;

@Component
public interface CompetitorRepository {

	public Competitor getOne(Long id);
	
	public void save(List<Competitor> competitors);

	public List<Competitor> findByMatch(Long matchId);
	
	public List<Competitor> findByMatchAndDivision(Long matchId, String division);
	
	public Competitor findByPractiScoreReferences(String matchPractiScoreId, String competitorPractiScoreId);
	
	public void deleteByMatch(Long matchId);
	
	public Long getIdByPractiScoreReferences(String competitorPractiScoreId, String matchPractiScoreId);
	
	public List<Object[]> getCategories(Set<Long> competitorIds);
	
}
