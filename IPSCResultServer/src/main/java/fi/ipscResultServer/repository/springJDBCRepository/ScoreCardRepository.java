package fi.ipscResultServer.repository.springJDBCRepository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fi.ipscResultServer.domain.ScoreCard;
import fi.ipscResultServer.repository.springJDBCRepository.mapper.ScoreCardMapper;

@Repository
public class ScoreCardRepository {
	@Autowired
	private DatabaseUtil dbUtil;
	
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
    public void init() {
        jdbcTemplate = dbUtil.getJdbcTemplate();
    }
	
	public List<ScoreCard> findAll() {
		String query = "SELECT * FROM scorecard";
		return jdbcTemplate.query(query, new ScoreCardMapper());
	}
	public void delete(List<Long> ids) {
		String query = "DELETE FROM scorecard WHERE id = ?";
		jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setLong(1, ids.get(i));
			}
			@Override
			public int getBatchSize() {
				return ids.size();
			}
		});
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
				ps.setDate(14, new java.sql.Date(card.getModified().getTimeInMillis()));
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
}
