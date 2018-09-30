package fi.ipscResultServer.repository.springJDBCRepository.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.repository.springJDBCRepository.StageRepository;
import fi.ipscResultServer.repository.springJDBCRepository.impl.mapper.StageMapper;

@Repository
public class StageRepositoryImpl implements StageRepository {
	@Autowired
	private DatabaseUtil dbUtil;
	
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
    public void init() {
        jdbcTemplate = dbUtil.getJdbcTemplate();
    }
	
	public Stage getOne(Long id) {
		try {
			String sql = "SELECT * FROM stage WHERE id = ?";
			return jdbcTemplate.queryForObject(sql, new Object[]{ id }, new StageMapper());
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	public void save(List<Stage> stages) {
		String query = "INSERT INTO stage (name, maxpoints, practiscoreid, stagenumber, match_id, stages_order)"
	      		+ " VALUES (?, ?, ?, ?, ?, ?);";
		
		jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Stage stage = stages.get(i);
				ps.setString(1, stage.getName());
				ps.setInt(2, stage.getMaxPoints());
				ps.setString(3, stage.getPractiScoreId());
				ps.setInt(4, stage.getStageNumber());
				ps.setLong(5, stage.getMatch().getId());
				ps.setInt(6, stage.getMatch().getStages().indexOf(stage));
			}
			@Override
			public int getBatchSize() {
				return stages.size();
			}
		});
	}
	public Stage findByPractiScoreReference(String matchPractiScoreId, String stagePractiScoreId) {
		try {
			String query = "SELECT * FROM stage s"
					+ " INNER JOIN ipscmatch m ON s.match_id = m.id"
					+ " WHERE s.practiscoreid = ? AND m.practiscoreid = ?;";
			return jdbcTemplate.queryForObject(query, new Object[] { stagePractiScoreId, 
					matchPractiScoreId }, new StageMapper());
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Stage> findByMatch(Long matchId) {
		String query = "SELECT * FROM stage WHERE match_id = ? ORDER BY stages_order";
		return jdbcTemplate.query(query, new Object[] { matchId }, new StageMapper());
	}
	@Transactional
	public void deleteByMatch(Long matchId) {
		String query = "DELETE FROM stage WHERE match_id = ?";
		jdbcTemplate.update(query, new Object[] { matchId }); 
	}
	public Long getIdByPractiScoreReference(String matchPractiScoreId, String stagePractiScoreId) {
		String sql = "SELECT s.id FROM stage s"
				+ " INNER JOIN ipscmatch m ON s.match_id = m.id"
				+ " WHERE s.practiscoreid = ? and m.practiscoreid = ?;";
		return jdbcTemplate.queryForObject(sql, new Object[] { stagePractiScoreId, matchPractiScoreId }, Long.class);
	}
}
