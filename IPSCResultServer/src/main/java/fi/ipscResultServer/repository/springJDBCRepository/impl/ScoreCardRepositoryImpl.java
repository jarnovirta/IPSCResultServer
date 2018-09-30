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
import fi.ipscResultServer.repository.springJDBCRepository.ScoreCardRepository;
import fi.ipscResultServer.repository.springJDBCRepository.impl.mapper.ScoreCardMapper;

@Repository
public class ScoreCardRepositoryImpl implements ScoreCardRepository {
	@Autowired
	private DatabaseUtil dbUtil;
	
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
    public void init() {
        jdbcTemplate = dbUtil.getJdbcTemplate();
    }
	
	public void deleteByMatch(Long matchId) {
		String query = "DELETE sc FROM scorecard sc INNER JOIN stage s ON sc.stage_id = s.id WHERE s.match_id = ?";
		jdbcTemplate.update(query, new Object[] { matchId });
	}
	
	public void save(List<ScoreCard> cards) {
		String query = "INSERT INTO scorecard (ahits, additionalpenalties, chits, dhits, time, hitfactor, misses, noshoothits"
				+ ", proceduralpenalties, stagepoints, stagerank, competitor_id, stage_id, modified"
	      		+ ", points, poppermisses, popperhits, poppernoshoothits)"
	      		+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
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
				ps.setInt(10, card.getStagePoints());
				ps.setInt(11, card.getStageRank());
				ps.setLong(12, card.getCompetitor().getId());
				ps.setLong(13, card.getStage().getId());
				ps.setTimestamp(14, new java.sql.Timestamp(card.getModified().getTimeInMillis()));
				ps.setInt(15, card.getPoints());
				ps.setInt(16, card.getPopperMisses());
				ps.setInt(17, card.getPopperHits());
				ps.setInt(18,  card.getPopperNoshootHits());
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
		String sql = "SELECT * FROM scorecard WHERE stage_id = ? ORDER BY hitfactor DESC";
		return jdbcTemplate.query(sql, new Object[] { stageId }, new ScoreCardMapper());
	}
	public List<ScoreCard> findByStageAndDivision(Long stageId, String division) {
		String sql = "SELECT * FROM scorecard s"
				+ " INNER JOIN competitor c ON s.competitor_id = c.id"
				+ " WHERE s.stage_id = ? AND c.division = ? ORDER BY s.hitfactor DESC";
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
				+ " WHERE m.id = ? ORDER BY c.division ASC";
		return jdbcTemplate.queryForList(sql, new Object[] { matchId }, String.class);
	}
}
