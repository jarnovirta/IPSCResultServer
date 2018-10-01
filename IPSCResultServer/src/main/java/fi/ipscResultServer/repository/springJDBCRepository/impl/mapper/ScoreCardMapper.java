package fi.ipscResultServer.repository.springJDBCRepository.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import fi.ipscResultServer.domain.ScoreCard;

@Component
public class ScoreCardMapper implements RowMapper<ScoreCard> {
	public ScoreCard mapRow(ResultSet rs, int rowNum) throws SQLException {
		ScoreCard card = new ScoreCard();
		card.setId(rs.getLong("id"));
		card.setaHits(rs.getInt("ahits"));
		card.setcHits(rs.getInt("chits"));
		card.setdHits(rs.getInt("dhits"));
		card.setMisses(rs.getInt("misses"));
		card.setNoshootHits(rs.getInt("noshoothits"));
		card.setProceduralPenalties(rs.getInt("proceduralpenalties"));
		card.setPopperHits(rs.getInt("popperhits"));
		card.setPopperMisses(rs.getInt("poppermisses"));
		card.setPopperNoshootHits(rs.getInt("poppernoshoothits"));
		card.setTime(rs.getDouble("time"));
		card.setHitFactor(rs.getDouble("hitfactor"));
		Calendar cal = Calendar.getInstance();
		cal.setTime(rs.getTimestamp("modified"));
		card.setModified(cal);
		card.setPoints(rs.getInt("points"));
		card.setStageId(rs.getLong("stage_id"));
		card.setCompetitorId(rs.getLong("competitor_id"));
		card.setStagePoints(rs.getDouble("stagepoints"));
		card.setCombinedDivisionStagePoints(rs.getDouble("combineddivisionstagepoints"));
		card.setStageRank(rowNum + 1);
		return card;
	}
}