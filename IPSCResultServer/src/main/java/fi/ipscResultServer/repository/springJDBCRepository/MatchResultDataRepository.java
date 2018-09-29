package fi.ipscResultServer.repository.springJDBCRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fi.ipscResultServer.domain.resultData.MatchResultData;
import fi.ipscResultServer.domain.resultData.MatchResultDataLine;
import fi.ipscResultServer.repository.springJDBCRepository.mapper.MatchResultDataLineMapper;
import fi.ipscResultServer.repository.springJDBCRepository.mapper.MatchResultDataMapper;

@Repository
public class MatchResultDataRepository {
	@Autowired
	private DatabaseUtil dbUtil;
	
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
    public void init() {
        jdbcTemplate = dbUtil.getJdbcTemplate();
    }
	
	public void deleteByMatch(Long matchId) {
		String sql = "DELETE mrdl FROM matchresultdataline mrdl"
				+ " INNER JOIN matchresultdata mrd ON mrdl.matchresultdata_id = mrd.id"
				+ " INNER JOIN ipscmatch m ON mrd.match_id = m.id"
				+ " WHERE m.id = ?";
		jdbcTemplate.update(sql, new Object[] { matchId });
		sql = "DELETE mrd FROM matchresultdata mrd"
				+ " INNER JOIN ipscmatch m ON mrd.match_id = m.id"
				+ " WHERE m.id = ?";
		jdbcTemplate.update(sql, new Object[] { matchId });
	}
	
	public void save(MatchResultData matchResultData) {
		try {
		String resultDataInsert = "INSERT INTO matchresultdata (division, match_id)"
				+ " VALUES (?, ?);";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(resultDataInsert, new String[] { "id" });
				ps.setString(1, matchResultData.getDivision());
				ps.setLong(2, matchResultData.getMatch().getId());
				return ps;
			}
		}, keyHolder);
		matchResultData.setId(keyHolder.getKey().longValue());
		
		String dataLinesInsert = "INSERT INTO matchresultdataline (points, rank,"
				+ " scorepercentage, scoredstages, competitor_id, matchresultdata_id)"
				+ " VALUES (?, ?, ?, ?, ?, ?);";
		
		List<MatchResultDataLine> lines = matchResultData.getDataLines(); 
		jdbcTemplate.batchUpdate(dataLinesInsert, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				MatchResultDataLine line = lines.get(i);
				ps.setDouble(1, line.getPoints());
				ps.setInt(2, line.getRank());
				ps.setDouble(3, line.getScorePercentage());
				ps.setInt(4, line.getScoredStages());
				ps.setLong(5, line.getCompetitor().getId());
				ps.setLong(6, line.getMatchResultData().getId());
			}
			@Override
			public int getBatchSize() {
				return lines.size();
			}
		});
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public MatchResultData findByMatchAndDivision(Long matchId, String division) {
		String sql = "SELECT * FROM matchresultdata WHERE match_id = ? AND division = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] { matchId, division }, new MatchResultDataMapper());
	}
	public List<MatchResultDataLine> getDataLines(Long matchResultDataId) {
		String sql = "SELECT * FROM matchresultdataline WHERE matchresultdata_id = ? ORDER BY rank ASC";
		return jdbcTemplate.query(sql, new Object[] { matchResultDataId }, new MatchResultDataLineMapper());
	}
}
