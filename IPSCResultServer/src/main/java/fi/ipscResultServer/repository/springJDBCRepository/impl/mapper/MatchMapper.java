package fi.ipscResultServer.repository.springJDBCRepository.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.MatchStatus;

@Component
public class MatchMapper implements RowMapper<Match> {

	@Override
	public Match mapRow(ResultSet rs, int rowNum) throws SQLException {
		Match match = new Match();
		match.setId(rs.getLong("id"));
		Calendar cal = Calendar.getInstance();
		cal.setTime(rs.getDate("date"));
		match.setDate(cal);
		match.setLevel(rs.getString("level"));
		match.setName(rs.getString("name"));
		match.setPractiScoreId(rs.getString("practiscoreid"));
		match.setStatus(MatchStatus.values()[rs.getInt("status")]);
		match.setUploadedByAdmin(rs.getBoolean("uploadedbyadmin"));
		match.setUserId(rs.getLong("user_id"));
		if (rs.wasNull()) match.setUserId(null);
		return match;
	}
}
