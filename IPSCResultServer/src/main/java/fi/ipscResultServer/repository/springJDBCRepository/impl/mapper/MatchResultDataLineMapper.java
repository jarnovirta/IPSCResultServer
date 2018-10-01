package fi.ipscResultServer.repository.springJDBCRepository.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fi.ipscResultServer.domain.resultData.MatchResultDataLine;

public class MatchResultDataLineMapper implements RowMapper<MatchResultDataLine> {

	@Override
	public MatchResultDataLine mapRow(ResultSet rs, int rowNum) throws SQLException {
		MatchResultDataLine line = new MatchResultDataLine();
		line.setPoints(rs.getDouble("points_sum"));
		line.setCompetitorId(rs.getLong("competitor_id"));
		line.setScoredStages(rs.getInt("scored_stages_count"));
		return line;
	}

}
