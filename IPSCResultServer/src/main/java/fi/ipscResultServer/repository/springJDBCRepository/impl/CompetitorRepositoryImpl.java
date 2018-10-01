package fi.ipscResultServer.repository.springJDBCRepository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.repository.springJDBCRepository.CompetitorRepository;
import fi.ipscResultServer.repository.springJDBCRepository.impl.mapper.CompetitorMapper;

@Repository
public class CompetitorRepositoryImpl implements CompetitorRepository {
	@Autowired
	private DatabaseUtil dbUtil;
	
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
    public void init() {
        jdbcTemplate = dbUtil.getJdbcTemplate();
    }
	
	public Competitor getOne(Long id) {
		String sql = "SELECT * FROM competitor WHERE id = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] { id }, new CompetitorMapper());
	}
	public void save(final List<Competitor> competitors) {
		String insertCompetitorSql = "INSERT INTO competitor (disqualified, division, firstname, ipscalias, lastname"
				+ ", powerfactor, practiscoreid, shooternumber, squad, team, country, match_id)"
	      		+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
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
					ps.setString(11, competitor.getCountry());
					ps.setLong(12, competitor.getMatch().getId());
					
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
			String query = "SELECT * FROM competitor WHERE match_id = ? ORDER BY UPPER(TRIM(lastname)) ASC";
			return jdbcTemplate.query(query, new Object[] { matchId }, new CompetitorMapper());
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	public List<Competitor> findByMatchAndDivision(Long matchId, String division) {
		try {
			String query = "SELECT * FROM competitor WHERE match_id = ? AND division = ? ORDER BY UPPER(TRIM(lastname)) ASC";
			return jdbcTemplate.query(query, new Object[] { matchId, division }, new CompetitorMapper());
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
	
	public Competitor findByPractiScoreReferences(String matchPractiScoreId, String competitorPractiScoreId) {
		try {
			String sql = "SELECT * FROM competitor c "
					+ "INNER JOIN ipscmatch m ON c.match_id = m.id "
					+ "WHERE m.practiscoreid = ? AND c.practiscoreid = ?";
			return jdbcTemplate.queryForObject(sql, new Object[] { matchPractiScoreId, competitorPractiScoreId }, 
					new CompetitorMapper());
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
		
	}
		

	public List<String> getCategories(Long competitorId) {
		String sql = "SELECT categories FROM competitor_categories WHERE competitor_id = ?";
		return jdbcTemplate.queryForList(sql, new Object[] { competitorId }, String.class);
	}
	
	public Long getIdByPractiScoreReferences(String competitorPractiScoreId, String matchPractiScoreId) {
		String sql = "SELECT c.id FROM competitor c "
				+ "INNER JOIN ipscmatch m ON c.match_id = m.id "
				+ "WHERE m.practiscoreid = ? AND c.practiscoreid = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] { matchPractiScoreId, competitorPractiScoreId }, 
				Long.class);		
	}
}