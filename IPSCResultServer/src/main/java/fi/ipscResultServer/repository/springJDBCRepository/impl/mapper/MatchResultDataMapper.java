package fi.ipscResultServer.repository.springJDBCRepository.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fi.ipscResultServer.domain.resultData.MatchResultData;

public class MatchResultDataMapper implements RowMapper<MatchResultData> {

	@Override
	public MatchResultData mapRow(ResultSet rs, int rowNum) throws SQLException {
		MatchResultData data = new MatchResultData();
		data.setId(rs.getLong("ID"));
		data.setDivision(rs.getString("DIVISION"));
		return data;
	}
}
