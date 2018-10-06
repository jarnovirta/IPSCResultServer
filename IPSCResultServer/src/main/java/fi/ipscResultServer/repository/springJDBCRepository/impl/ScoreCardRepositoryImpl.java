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
import fi.ipscResultServer.service.MatchService;
import fi.ipscResultServer.service.ScoreCardService;
import fi.ipscResultServer.service.StageService;
import fi.ipscResultServer.service.impl.PractiScoreDataServiceImpl;

@Repository
public class ScoreCardRepositoryImpl implements ScoreCardRepository {
	@Autowired
	private DatabaseUtil dbUtil;
	
	private JdbcTemplate jdbcTemplate;
	
	private final String getStatsSql = "SELECT sc.competitor_id, SUM(sc.ahits) as ahitssum, SUM(sc.chits) AS chitssum"
			+ ", SUM(sc.dhits) AS dhitssum, SUM(sc.misses) AS missessum, SUM(sc.noshoothits) AS nshitssum"
			+ ", SUM(sc.proceduralpenalties) AS procsum, SUM(sc.additionalpenalties) AS adpensum"
			+ ", SUM(sc.time) AS timesum, SUM(sc.hitfactor) AS hfsum, SUM(sc.points) AS pointssum"
			+ ", SUM(sc.stagepoints) AS stagepointssum"
			+ " FROM scorecard sc"
			+ " INNER JOIN competitor c ON sc.competitor_id = c.id"
			+ " INNER JOIN stage s ON sc.stage_id = s.id";

	
	@PostConstruct
    public void init() {
        jdbcTemplate = dbUtil.getJdbcTemplate();
    }
	
	
	
	
	@Autowired
	MatchService matchService; 
	
	@Autowired
	PractiScoreDataServiceImpl psDataService;
	
	@Autowired
	StageService stageService;
	
	@Autowired
	ScoreCardService cardService;
	
	public void deleteByMatch(Long matchId) {
		String query = "DELETE sc FROM scorecard sc INNER JOIN stage s ON sc.stage_id = s.id WHERE s.match_id = ?";
		jdbcTemplate.update(query, new Object[] { matchId });
	}
	
	public void save(List<ScoreCard> cards) {
		String query = "INSERT INTO scorecard (ahits, additionalpenalties, chits, dhits, time, hitfactor, misses, noshoothits"
				+ ", proceduralpenalties, stagepoints, competitor_id, stage_id, modified"
	      		+ ", points, combineddivisionstagepoints, scorepercentage, combineddivisionscorepercentage)"
	      		+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
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
			}
			@Override
			public int getBatchSize() {
				return cards.size();
			}
		});
	}
	public List<ScoreCard> findByCompetitor(Long competitorId) {
		String sql = "SELECT * FROM scorecard WHERE competitor_id = ? ORDER BY hitfactor DESC";
		return jdbcTemplate.query(sql, new Object[] { competitorId }, new ScoreCardMapper());
	}
	public List<ScoreCard> findByStage(Long stageId) {
		String sql = "SELECT * FROM scorecard sc"
				+ " INNER JOIN competitor c on sc.competitor_id = c.id"
				+ " WHERE stage_id = ? AND c.disqualified = 0"
				+ " ORDER BY hitfactor DESC";
		return jdbcTemplate.query(sql, new Object[] { stageId }, new ScoreCardMapper());
	}
	public List<ScoreCard> findByStageAndDivision(Long stageId, String division) {
		String sql = "SELECT * FROM scorecard s"
				+ " INNER JOIN competitor c ON s.competitor_id = c.id"
				+ " WHERE s.stage_id = ? AND c.division = ? AND c.disqualified = 0"
				+ " ORDER BY s.hitfactor DESC";
		return jdbcTemplate.query(sql, new Object[] { stageId, division }, new ScoreCardMapper());
	}
	public List<ScoreCard> findByMatch(Long matchId) {
		String sql = "SELECT * FROM scorecard sc "
				+ " INNER JOIN stage s ON sc.stage_id = s.id"
				+ " INNER JOIN ipscmatch m ON s.match_id = m.id"
				+ " WHERE m.id = ?"
				+ " ORDER BY sc.hitfactor DESC";
		return jdbcTemplate.query(sql, new Object[] { matchId }, new ScoreCardMapper());
	}
	
	public List<String> getDivisionsWithResults(Long matchId) {
		String sql = "SELECT DISTINCT(c.division) FROM competitor c"
				+ " INNER JOIN scorecard sc ON sc.competitor_id = c.id"
				+ " INNER JOIN stage s ON sc.stage_id = s.id"
				+ " INNER JOIN ipscmatch m ON s.match_id = m.id"
				+ " WHERE m.id = ? AND c.disqualified = 0"
				+ " ORDER BY c.division ASC";
		return jdbcTemplate.queryForList(sql, new Object[] { matchId }, String.class);
	}
	
	public List<CompetitorStatistics> getStatistics(Long matchId, String division) {
		String sql = getStatsSql 				
				+ " WHERE s.match_id = ? AND c.division = ?"
				+ " GROUP BY sc.competitor_id"
				+ " ORDER BY stagepointssum DESC";
		
		return jdbcTemplate.query(sql, new Object[] { matchId, division}, new StatisticsMapper());	
				
	}
	
	public List<CompetitorStatistics> getStatistics(Long matchId) {
		String sql = getStatsSql 				
				+ " WHERE s.match_id = ?"
				+ " GROUP BY sc.competitor_id"
				+ " ORDER BY stagepointssum DESC";
		return jdbcTemplate.query(sql, new Object[] { matchId }, new StatisticsMapper());
	}
	
	
}