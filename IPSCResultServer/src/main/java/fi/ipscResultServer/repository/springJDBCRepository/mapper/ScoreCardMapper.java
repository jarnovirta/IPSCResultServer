package fi.ipscResultServer.repository.springJDBCRepository.mapper;

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
		card.setTime(rs.getDouble("time"));
		card.setHitFactor(rs.getDouble("hitfactor"));
		
		// TODO: FIX
		card.setCompetitor(null);
		card.setStage(null);
		Calendar cal = Calendar.getInstance();
		cal.setTime(rs.getDate("modified"));
		card.setModified(cal);
		card.setPoints(rs.getInt("points"));
		card.setPopperMisses(rs.getInt("poppermisses"));
		card.setPopperHits(rs.getInt("popperhits"));
		card.setPopperNoshootHits(rs.getInt("poppernoshoothits"));
		card.setProceduralPenalties(rs.getInt("proceduralpenalties"));
		
		return card;
	}
}
