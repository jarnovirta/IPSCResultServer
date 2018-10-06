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
		card.setId(rs.getLong("ID"));
		card.setaHits(rs.getInt("AHITS"));
		card.setcHits(rs.getInt("CHITS"));
		card.setdHits(rs.getInt("DHITS"));
		card.setMisses(rs.getInt("MISSES"));
		card.setNoshootHits(rs.getInt("NOSHOOTHITS"));
		card.setProceduralPenalties(rs.getInt("PROCEDURALPENALTIES"));
		card.setTime(rs.getDouble("TIME"));
		card.setHitFactor(rs.getDouble("HITFACTOR"));
		Calendar cal = Calendar.getInstance();
		cal.setTime(rs.getTimestamp("MODIFIED"));
		card.setModified(cal);
		card.setPoints(rs.getInt("POINTS"));
		card.setStageId(rs.getLong("STAGE_ID"));
		card.setCompetitorId(rs.getLong("COMPETITOR_ID"));
		card.setStagePoints(rs.getDouble("STAGEPOINTS"));
		card.setCombinedDivisionStagePoints(rs.getDouble("COMBINEDDIVISIONSTAGEPOINTS"));
		card.setScorePercentage(rs.getDouble("SCOREPERCENTAGE"));
		card.setCombinedDivisionScorePercentage(rs.getDouble("COMBINEDDIVISIONSCOREPERCENTAGE"));
		card.setStageRank(rs.getInt("STAGERANK"));
		card.setDnf(rs.getBoolean("DNF"));
		if (rs.wasNull()) card.setDnf(false);
		return card;
	}
}