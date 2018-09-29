package fi.ipscResultServer.repository.springJDBCRepository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fi.ipscResultServer.domain.resultData.MatchResultData;

public class MatchResultDataMapper implements RowMapper<MatchResultData> {

	@Override
	public MatchResultData mapRow(ResultSet rs, int rowNum) throws SQLException {
		MatchResultData data = new MatchResultData();
		data.setId(rs.getLong("id"));
		data.setMatchId(rs.getLong("match_id"));
		data.setDivision(rs.getString("division"));
		return data;
	}
}
