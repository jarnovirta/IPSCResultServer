package fi.ipscResultServer.repository.springJDBCRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.repository.springJDBCRepository.mapper.CompetitorMapper;
import fi.ipscResultServer.service.MatchService;

@Repository
public class CompetitorRepository {
	@Autowired
	private DatabaseUtil dbUtil;
	
	private JdbcTemplate jdbcTemplate;
	
	private final static Logger LOGGER = Logger.getLogger(MatchService.class);
	
	
	@PostConstruct
    public void init() {
        jdbcTemplate = dbUtil.getJdbcTemplate();
    }
	
	public void save(final List<Competitor> competitors) {
		String insertCompetitorSql = "INSERT INTO competitor (disqualified, division, firstname, ipscalias, lastname"
				+ ", powerfactor, practiscoreid, shooternumber, squad, team, match_id)"
	      		+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
		String insertCompetitorCategoriesSql = "INSERT INTO competitor_categories (competitor_id, categories)"
				+ " VALUES (?, ?);";
		
		for (Competitor competitor : competitors) {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(insertCompetitorSql, new String[] { "id" });
					ps.setBoolean(1, competitor.isDisqualified());
					ps.setString(2, competitor.getDivision());
					ps.setString(3, competitor.getFirstName());
					ps.setString(4, competitor.getIpscAlias());
					ps.setString(5, competitor.getLastName());
					ps.setInt(6, competitor.getPowerFactor().ordinal());
					ps.setString(7, competitor.getPractiScoreId());
					ps.setInt(8, competitor.getShooterNumber());
					ps.setInt(9, competitor.getSquad());
					ps.setString(10, competitor.getTeam());
					ps.setLong(11, competitor.getMatch().getId());
					
					return ps;
				}
			}, keyHolder);
			competitor.setId(keyHolder.getKey().longValue());

			// Insert competitor categories data
			if (competitor.getCategories() != null) {
				for (String cat : competitor.getCategories()) {
					jdbcTemplate.update(insertCompetitorCategoriesSql, new Object[] { competitor.getId(), cat });
				}
			}
		}
	}

	public List<Competitor> findByMatch(Long matchId) {
		try {
			String query = "SELECT * FROM competitor WHERE match_id = ? ORDER BY ? DESC";
			return jdbcTemplate.query(query, new Object[] {matchId, "lastname"}, new CompetitorMapper());
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public void deleteByMatch(Long matchId) {
		String query = "DELETE cc FROM competitor_categories cc INNER JOIN competitor c ON cc.competitor_id = c.id WHERE c.match_id = ?;";
		jdbcTemplate.update(query, new Object[] { matchId });
		query = "DELETE FROM competitor WHERE match_id = ?;";
		jdbcTemplate.update(query, new Object[] { matchId });
	}
}