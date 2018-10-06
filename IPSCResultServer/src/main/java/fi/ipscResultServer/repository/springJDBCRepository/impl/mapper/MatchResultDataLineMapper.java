package fi.ipscResultServer.repository.springJDBCRepository.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fi.ipscResultServer.domain.resultData.MatchResultDataLine;

public class MatchResultDataLineMapper implements RowMapper<MatchResultDataLine> {

	@Override
	public MatchResultDataLine mapRow(ResultSet rs, int rowNum) throws SQLException {
		MatchResultDataLine line = new MatchResultDataLine();
		line.setPoints(rs.getDouble("POINTS_SUM"));
		line.setCompetitorId(rs.getLong("COMPETITOR_ID"));
		line.setScoredStages(rs.getInt("SCORED_STAGES_COUNT"));
		return line;
	}

}
