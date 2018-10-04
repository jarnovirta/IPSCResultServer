package fi.ipscResultServer.repository.springJDBCRepository.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fi.ipscResultServer.domain.statistics.CompetitorStatistics;
import fi.ipscResultServer.repository.springJDBCRepository.StatisticsRepository;
import fi.ipscResultServer.repository.springJDBCRepository.impl.mapper.StatisticsMapper;

@Repository
public class StatisticsRepositoryImpl implements StatisticsRepository {
	@Autowired
	private DatabaseUtil dbUtil;
	
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
    public void init() {
        jdbcTemplate = dbUtil.getJdbcTemplate();
    }

	public void save(List<CompetitorStatistics> stats) {
		String query = "INSERT INTO competitorstatistics (ahitpercentage, ahits, additionalpenalties,"
				+ "chits, dhits, divisionpoints, divisionrank, divisionscorepercentage,"
				+ "matchtime, misses, noshoothits, proceduralpenalties, sumofpoints, "
				+ "competitor_id, match_id)"
	      		+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
		jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				CompetitorStatistics stat = stats.get(i);
				ps.setDouble(1, stat.getaHitPercentage());
				ps.setInt(2, stat.getaHits());
				ps.setInt(3, stat.getAdditionalPenalties());
				ps.setInt(4, stat.getcHits());
				ps.setInt(5, stat.getdHits());
				ps.setDouble(6, stat.getDivisionPoints());
				if (stat.getDivisionRank() == null) ps.setNull(7, java.sql.Types.NULL);
				else ps.setInt(7, stat.getDivisionRank());
				ps.setDouble(8, stat.getDivisionScorePercentage());
				ps.setDouble(9, stat.getMatchTime());
				ps.setInt(10, stat.getMisses());
				ps.setInt(11, stat.getNoShootHits());
				ps.setInt(12, stat.getProceduralPenalties());
				ps.setInt(13, stat.getSumOfPoints());
				ps.setLong(14, stat.getCompetitor().getId());
				ps.setLong(15, stat.getMatch().getId());
			}
			@Override
			public int getBatchSize() {
				return stats.size();
			}
		});
	}
	public List<CompetitorStatistics> findByMatch(Long matchId) {
		String sql = "SELECT * FROM competitorstatistics WHERE match_id = ?";
		return jdbcTemplate.query(sql, new Object[] { matchId }, new StatisticsMapper());
	}
	
	public List<CompetitorStatistics> findByMatchAndDivision(Long matchId, String division) {
		String sql = "SELECT * FROM competitorstatistics cs"
				+ " INNER JOIN competitor c ON cs.competitor_id = c.id"
				+ " WHERE cs.match_id = ? AND c.division = ?";
		return jdbcTemplate.query(sql, new Object[] { matchId, division }, new StatisticsMapper());
	}
	public void deleteByMatch(Long matchId) {
		String sql = "DELETE FROM competitorstatistics WHERE match_id = ?;";
		jdbcTemplate.update(sql, new Object[] { matchId });
	}
}
