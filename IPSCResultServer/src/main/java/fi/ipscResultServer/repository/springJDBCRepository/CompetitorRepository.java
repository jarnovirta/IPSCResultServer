package fi.ipscResultServer.repository.springJDBCRepository;

import java.util.List;

import org.springframework.stereotype.Component;

import fi.ipscResultServer.domain.Competitor;

@Component
public interface CompetitorRepository {

	public Competitor getOne(Long id);
	
	public void save(List<Competitor> competitors);

	public List<Competitor> findByMatch(Long matchId);
	
	public Competitor findByPractiScoreReferences(String matchPractiScoreId, String competitorPractiScoreId);
	
	public void deleteByMatch(Long matchId);

	public List<String> getCategories(Long competitorId);
}
