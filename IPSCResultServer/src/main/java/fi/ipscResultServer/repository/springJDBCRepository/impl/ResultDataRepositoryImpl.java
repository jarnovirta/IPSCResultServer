package fi.ipscResultServer.repository.springJDBCRepository.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fi.ipscResultServer.domain.resultData.MatchResultDataLine;
import fi.ipscResultServer.repository.springJDBCRepository.ResultDataRepository;
import fi.ipscResultServer.repository.springJDBCRepository.impl.mapper.MatchResultDataLineMapper;

@Repository
public class ResultDataRepositoryImpl implements ResultDataRepository {
	@Autowired
	private DatabaseUtil dbUtil;
	
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
    public void init() {
        jdbcTemplate = dbUtil.getJdbcTemplate();
    }

	public List<MatchResultDataLine> findResultDataLinesByMatchAndDivision(Long matchId, String division) {
		try {
			String sql = "SELECT c.id AS competitor_id, SUM(sc.stagepoints) AS points_sum, COUNT(sc.id) AS scored_stages_count"
					+ " FROM SCORECARD sc"
					+ " LEFT JOIN COMPETITOR c ON sc.COMPETITOR_ID = c.ID"
					+ " INNER JOIN STAGE s ON sc.STAGE_ID = s.ID"
					+ " WHERE c.DIVISION = ?"
					+ " AND c.DISQUALIFIED = 0" 
					+ " AND s.MATCH_ID = ? "
					+ " GROUP BY c.ID "
					+ " ORDER BY points_sum DESC";
			return jdbcTemplate.query(sql, new Object[] { division, matchId }, new MatchResultDataLineMapper());
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	public List<MatchResultDataLine> findResultDataLinesByMatch(Long matchId) {
		try {
			String sql = "SELECT c.id AS competitor_id, SUM(sc.combineddivisionstagepoints) AS points_sum, COUNT(sc.id) AS scored_stages_count"
					+ " FROM SCORECARD sc"
					+ " LEFT JOIN COMPETITOR c ON sc.COMPETITOR_ID = c.ID"
					+ " INNER JOIN STAGE s ON sc.STAGE_ID = s.ID"
					+ " WHERE c.DISQUALIFIED = 0" 
					+ " AND s.MATCH_ID = ? "
					+ " GROUP BY c.ID "
					+ " ORDER BY points_sum DESC";
			return jdbcTemplate.query(sql, new Object[] { matchId }, new MatchResultDataLineMapper());
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	
	}
}
