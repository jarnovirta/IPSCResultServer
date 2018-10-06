package fi.ipscResultServer.repository.springJDBCRepository.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.domain.statistics.CompetitorStatistics;
import fi.ipscResultServer.repository.springJDBCRepository.ScoreCardRepository;
import fi.ipscResultServer.repository.springJDBCRepository.impl.mapper.ScoreCardMapper;
import fi.ipscResultServer.repository.springJDBCRepository.impl.mapper.StatisticsMapper;

@Repository
public class ScoreCardRepositoryImpl implements ScoreCardRepository {
	@Autowired
	private DatabaseUtil dbUtil;
	
	private JdbcTemplate jdbcTemplate;
	
	private final String getStatsSql = "SELECT sc.COMPETITOR_ID, SUM(sc.AHITS) as ahitssum, SUM(sc.CHITS) AS chitssum"
			+ ", SUM(sc.DHITS) AS dhitssum, SUM(sc.MISSES) AS missessum, SUM(sc.NOSHOOTHITS) AS nshitssum"
			+ ", SUM(sc.PROCEDURALPENALTIES) AS procsum, SUM(sc.ADDITIONALPENALTIES) AS adpensum"
			+ ", SUM(sc.TIME) AS timesum, SUM(sc.HITFACTOR) AS hfsum, SUM(sc.POINTS) AS pointssum"
			+ ", SUM(sc.STAGEPOINTS) AS stagepointssum"
			+ " FROM scorecard sc"
			+ " INNER JOIN competitor c ON sc.COMPETITOR_ID = c.ID"
			+ " INNER JOIN stage s ON sc.STAGE_ID = s.ID";

	
	@PostConstruct
    public void init() {
        jdbcTemplate = dbUtil.getJdbcTemplate();
    }
	
	public void deleteByMatch(Long matchId) {
		String query = "DELETE sc FROM scorecard sc INNER JOIN stage s ON sc.STAGE_ID = s.ID WHERE s.MATCH_ID = ?";
		jdbcTemplate.update(query, new Object[] { matchId });
	}
	
	public void save(List<ScoreCard> cards) {
		String query = "INSERT INTO scorecard (AHITS, ADDITIONALPENALTIES, CHITS, DHITS, TIME, HITFACTOR, MISSES, NOSHOOTHITS"
				+ ", PROCEDURALPENALTIES, STAGEPOINTS, COMPETITOR_ID, STAGE_ID, MODIFIED"
	      		+ ", POINTS, COMBINEDDIVISIONSTAGEPOINTS, SCOREPERCENTAGE, COMBINEDDIVISIONSCOREPERCENTAGE, STAGERANK, DNF)"
	      		+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
		jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ScoreCard card = cards.get(i);
				ps.setInt(1, card.getaHits());
				ps.setInt(2, card.getAdditionalPenalties());
				ps.setInt(3, card.getcHits());
				ps.setInt(4, card.getdHits());
				ps.setDouble(5,  card.getTime());
				ps.setDouble(6, card.getHitFactor());
				ps.setInt(7, card.getMisses());
				ps.setInt(8, card.getNoshootHits());
				ps.setInt(9, card.getProceduralPenalties());
				ps.setDouble(10, card.getStagePoints());
				ps.setLong(11, card.getCompetitor().getId());
				ps.setLong(12, card.getStage().getId());
				ps.setTimestamp(13, new java.sql.Timestamp(card.getModified().getTimeInMillis()));
				ps.setInt(14, card.getPoints());
				ps.setDouble(15, card.getCombinedDivisionStagePoints());
				ps.setDouble(16, card.getScorePercentage());
				ps.setDouble(17, card.getCombinedDivisionScorePercentage());
				ps.setInt(18, card.getStageRank());
				ps.setBoolean(19, card.isDnf());
			}
			@Override
			public int getBatchSize() {
				return cards.size();
			}
		});
	}
	public List<ScoreCard> findByCompetitor(Long competitorId) {
		String sql = "SELECT * FROM scorecard WHERE COMPETITOR_ID = ? ORDER BY HITFACTOR DESC";
		return jdbcTemplate.query(sql, new Object[] { competitorId }, new ScoreCardMapper());
	}
	public List<ScoreCard> findByStage(Long stageId) {
		String sql = "SELECT * FROM scorecard sc"
				+ " INNER JOIN competitor c on sc.COMPETITOR_ID = c.ID"
				+ " WHERE STAGE_ID = ? AND c.DISQUALIFIED = 0"
				+ " ORDER BY HITFACTOR DESC";
		return jdbcTemplate.query(sql, new Object[] { stageId }, new ScoreCardMapper());
	}
	public List<ScoreCard> findByStageAndDivision(Long stageId, String division) {
		String sql = "SELECT * FROM scorecard s"
				+ " INNER JOIN competitor c ON s.COMPETITOR_ID = c.ID"
				+ " WHERE s.STAGE_ID = ? AND c.DIVISION = ? AND c.DISQUALIFIED = 0"
				+ " ORDER BY s.HITFACTOR DESC";
		return jdbcTemplate.query(sql, new Object[] { stageId, division }, new ScoreCardMapper());
	}
	public List<ScoreCard> findByMatch(Long matchId) {
		String sql = "SELECT * FROM scorecard sc "
				+ " INNER JOIN stage s ON sc.STAGE_ID = s.ID"
				+ " INNER JOIN ipscmatch m ON s.MATCH_ID = m.ID"
				+ " WHERE m.ID = ?"
				+ " ORDER BY sc.HITFACTOR DESC";
		return jdbcTemplate.query(sql, new Object[] { matchId }, new ScoreCardMapper());
	}
	
	public List<String> getDivisionsWithResults(Long matchId) {
		String sql = "SELECT DISTINCT(c.DIVISION) FROM competitor c"
				+ " INNER JOIN scorecard sc ON sc.COMPETITOR_ID = c.ID"
				+ " INNER JOIN stage s ON sc.STAGE_ID = s.ID"
				+ " INNER JOIN ipscmatch m ON s.MATCH_ID = m.ID"
				+ " WHERE m.id = ? AND c.DISQUALIFIED = 0"
				+ " ORDER BY c.DIVISION ASC";
		return jdbcTemplate.queryForList(sql, new Object[] { matchId }, String.class);
	}
	
	public List<CompetitorStatistics> getStatistics(Long matchId, String division) {
		String sql = getStatsSql 				
				+ " WHERE s.MATCH_ID = ? AND c.DIVISION = ?"
				+ " GROUP BY sc.COMPETITOR_ID"
				+ " ORDER BY STAGEPOINTSSUM DESC";
		
		return jdbcTemplate.query(sql, new Object[] { matchId, division}, new StatisticsMapper());	
				
	}
	
	public List<CompetitorStatistics> getStatistics(Long matchId) {
		String sql = getStatsSql 				
				+ " WHERE s.MATCH_ID = ?"
				+ " GROUP BY sc.COMPETITOR_ID"
				+ " ORDER BY STAGEPOINTSSUM DESC";
		return jdbcTemplate.query(sql, new Object[] { matchId }, new StatisticsMapper());
	}
	
	
}