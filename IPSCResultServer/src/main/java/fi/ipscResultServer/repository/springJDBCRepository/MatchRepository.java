package fi.ipscResultServer.repository.springJDBCRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.repository.springJDBCRepository.mapper.MatchMapper;
import fi.ipscResultServer.service.UserService;

@Repository
public class MatchRepository {
	@Autowired
	private DatabaseUtil dbUtil;
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private UserService userService;
	
	@PostConstruct
    public void init() {
        jdbcTemplate = dbUtil.getJdbcTemplate();
    }
	@Transactional("JDBCTransaction")
	public Match save(Match match) {
		String query = "INSERT INTO ipscmatch (date, level, name, practiscoreid, "
				+ "status, uploadedbyadmin, user_id) VALUES (?, ?, ?, ?, ?, ?, ?);";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(query, new String[] { "id" });
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
		return match;
	}
	public Match getOne(Long matchId) {
		String query = "SELECT * FROM ipscmatch WHERE id = ?";
		Match match = jdbcTemplate.queryForObject(query, new Object[] { matchId }, new MatchMapper());
		setUser(match);
		return match;
	}
	public Match findByPractiScoreId(String practiScoreId) {
		try {
			String query = "SELECT * FROM ipscmatch WHERE practiscoreid = ?";
			Match match = jdbcTemplate.queryForObject(query, new Object[] { practiScoreId }, new MatchMapper());
			setUser(match);
			return match;
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	private void setUser(Match match) {
		if (match != null && match.getUserId() != null) {
			match.setUser(userService.getOne(match.getUserId()));
		}
	}
}
