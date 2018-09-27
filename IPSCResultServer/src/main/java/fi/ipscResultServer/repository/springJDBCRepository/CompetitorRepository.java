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

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.repository.springJDBCRepository.mapper.CompetitorMapper;
import fi.ipscResultServer.service.MatchService;

@Repository
public class CompetitorRepository {
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
	
	public void save(List<Competitor> competitors) {
		String query = "INSERT INTO competitor (disqualified, division, firstname, ipscalias, lastname"
				+ ", powerfactor, practiscoreid, shooternumber, squad, team, match_id)"
	      		+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
		jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Competitor comp = competitors.get(i);
				ps.setBoolean(1, comp.isDisqualified());
				ps.setString(2, comp.getDivision());
				ps.setString(3, comp.getFirstName());
				ps.setString(4, comp.getIpscAlias());
				ps.setString(5, comp.getLastName());
				ps.setInt(6, comp.getPowerFactor().ordinal());
				ps.setString(7, comp.getPractiScoreId());
				ps.setInt(8, comp.getShooterNumber());
				ps.setInt(9, comp.getSquad());
				ps.setString(10, comp.getTeam());
				ps.setLong(11, comp.getMatch().getId());
			}
			@Override
			public int getBatchSize() {
				return competitors.size();
			}
		});
	}
	
	public List<Competitor> findByMatch(Long matchId) {
		try {
			String query = "SELECT * FROM competitor WHERE match_id = ? ORDER BY ? DESC";
			List<Competitor> competitors = jdbcTemplate.query(query, new Object[] {matchId, "lastname"}, new CompetitorMapper());
			
			Match match = matchService.getOne(matchId);
			for (Competitor comp : competitors) comp.setMatch(match);
			return competitors;
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public void delete(List<Competitor> competitors) {
		if (competitors == null) return;
		
		logger.debug("Removing competitor references");
		removeReferencesToCompetitors(competitors);
		
		String query = "DELETE FROM competitor WHERE id = ?";
		jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setLong(1, competitors.get(i).getId());
			}
			@Override
			public int getBatchSize() {
				return competitors.size();
			}
		});
	}
	
	private void removeReferencesToCompetitors(List<Competitor> competitors) {
		String query = "UPDATE scorecard SET competitor_id = ? WHERE competitor_id = ?";
		jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setNull(1, java.sql.Types.BIGINT);
				ps.setLong(2, competitors.get(i).getId());
			}
			@Override
			public int getBatchSize() {
				return competitors.size();
			}
		});
	}
}
