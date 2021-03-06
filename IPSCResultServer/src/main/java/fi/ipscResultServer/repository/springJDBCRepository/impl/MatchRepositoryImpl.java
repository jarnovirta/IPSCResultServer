package fi.ipscResultServer.repository.springJDBCRepository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.MatchStatus;
import fi.ipscResultServer.repository.springJDBCRepository.MatchRepository;
import fi.ipscResultServer.repository.springJDBCRepository.impl.mapper.MatchMapper;

@Repository
public class MatchRepositoryImpl implements MatchRepository{
	@Autowired
	private DatabaseUtil dbUtil;
	
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
    public void init() {
        jdbcTemplate = dbUtil.getJdbcTemplate();
    }
	public Match save(Match match) {
		String matchTableQuery = "INSERT INTO ipscmatch (DATE, LEVEL, NAME, PRACTISCOREID, "
				+ "STATUS, UPLOADEDBYADMIN, USER_ID) VALUES (?, ?, ?, ?, ?, ?, ?);";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(matchTableQuery, new String[] { "ID" });
				ps.setDate(1, new java.sql.Date(match.getDate().getTimeInMillis()));
				ps.setString(2, match.getLevel());
				ps.setString(3, match.getName());
				ps.setString(4, match.getPractiScoreId());
				ps.setInt(5, match.getStatus().ordinal());
				ps.setBoolean(6, match.isUploadedByAdmin());
				if (match.getUser() == null ) {
					ps.setNull(7, java.sql.Types.NULL);
				}
				else ps.setLong(7, match.getUser().getId());
				
				return ps;
			}
		}, keyHolder);
		match.setId(keyHolder.getKey().longValue());
		
		List<String> divisions = match.getDivisions();
		String matchDivisionsTableQuery = "INSERT INTO match_divisions (MATCH_ID, DIVISIONS)"
				+ " VALUES (?, ?);";
		jdbcTemplate.batchUpdate(matchDivisionsTableQuery, new BatchPreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement ps, int row) throws SQLException {
				ps.setLong(1, match.getId());
				ps.setString(2, divisions.get(row));
			}

			@Override
			public int getBatchSize() {
				return divisions.size();
			}
		});

		return match;
	}
	public Match getOne(Long matchId) {
		try {
			String query = "SELECT * FROM ipscmatch WHERE ID = ?";
			Match match = jdbcTemplate.queryForObject(query, new Object[] { matchId }, new MatchMapper());
			return match;
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	public List<Match> findAll() {
		String query = "SELECT * FROM ipscmatch ORDER BY DATE DESC";
		return jdbcTemplate.query(query, new MatchMapper());
	}
	
	public List<Match> findByUser(Long userId) {
		try {
			String sql = "SELECT * FROM ipscmatch WHERE USER_ID = ?";
			return jdbcTemplate.query(sql, new Object[] { userId }, new MatchMapper());
		}
		catch (EmptyResultDataAccessException e) {
			return new ArrayList<Match>();
		}
	}
	public Long getIdByPractiScoreId(String practiScoreId) {
		try {
			String query = "SELECT id FROM ipscmatch WHERE PRACTISCOREID = ?";
			return jdbcTemplate.queryForObject(query, new Object[] { practiScoreId }, Long.class);
			
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public List<Long> getAllIdsByPractiScoreId(String matchPractiScoreId) {
		try {
			String sql = "SELECT id FROM ipscmatch WHERE PRACTISCOREID = ?";
			return jdbcTemplate.queryForList(sql, new Object[] { matchPractiScoreId }, Long.class);
		}
		catch (DataAccessException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public void delete(Long id) {
		String query = "DELETE FROM match_divisions WHERE MATCH_ID = ?;";
		jdbcTemplate.update(query, new Object[] { id});
		query = "DELETE FROM ipscmatch WHERE ID = ?";
		jdbcTemplate.update(query, new Object[] { id});
	}
	
	public void setStatus(Long matchId, MatchStatus status) {
		String sql = "UPDATE ipscmatch SET STATUS = ? WHERE ID = ?";
		jdbcTemplate.update(sql, new Object[] { status.ordinal(), matchId });
	}
	
	public List<String> getDivisions(Long matchId) {
		String sql = "SELECT divisions FROM match_divisions WHERE MATCH_ID = ?";
		return jdbcTemplate.queryForList(sql, new Object[] { matchId }, String.class);
		
	}
}
