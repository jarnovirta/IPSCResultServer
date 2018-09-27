package fi.ipscResultServer.repository.springJDBCRepository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.Stage;
import fi.ipscResultServer.repository.springJDBCRepository.mapper.StageMapper;
import fi.ipscResultServer.service.MatchService;

@Repository
public class StageRepository {
	@Autowired
	private DatabaseUtil dbUtil;
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private MatchService matchService;
	
	final static Logger logger = Logger.getLogger(StageRepository.class);
	
	@PostConstruct
    public void init() {
        jdbcTemplate = dbUtil.getJdbcTemplate();
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
			Match match = matchService.findByPractiScoreId(matchPractiScoreId);
			String query = "SELECT * FROM stage WHERE practiscoreid = ? AND match_id = ?;";
			return jdbcTemplate.queryForObject(query, new Object[] { stagePractiScoreId, match.getId() }, new StageMapper());
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
	public void delete(List<Stage> stages) {
		if (stages == null) return;

		removeReferencesToStages(stages);
		
		String query = "DELETE FROM stage WHERE id = ?";
		jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setLong(1, stages.get(i).getId());
			}
			@Override
			public int getBatchSize() {
				return stages.size();
			}
		});
	}
	private void removeReferencesToStages(List<Stage> stages) {
		
		String query = "UPDATE scorecard SET stage_id = ? WHERE stage_id = ?";
		jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setNull(1, java.sql.Types.BIGINT);
				ps.setLong(2, stages.get(i).getId());
			}
			@Override
			public int getBatchSize() {
				return stages.size();
			}
		});
	}
}
