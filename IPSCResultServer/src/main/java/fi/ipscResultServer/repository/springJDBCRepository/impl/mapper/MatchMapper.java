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
		match.setId(rs.getLong("ID"));
		Calendar cal = Calendar.getInstance();
		cal.setTime(rs.getDate("DATE"));
		match.setDate(cal);
		match.setLevel(rs.getString("LEVEL"));
		match.setName(rs.getString("NAME"));
		match.setPractiScoreId(rs.getString("PRACTISCOREID"));
		match.setStatus(MatchStatus.values()[rs.getInt("STATUS")]);
		match.setUploadedByAdmin(rs.getBoolean("UPLOADEDBYADMIN"));
		match.setUserId(rs.getLong("USER_ID"));
		if (rs.wasNull()) match.setUserId(null);
		return match;
	}
}
