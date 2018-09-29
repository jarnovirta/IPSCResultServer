package fi.ipscResultServer.repository.springJDBCRepository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fi.ipscResultServer.domain.resultData.MatchResultDataLine;

public class MatchResultDataLineMapper implements RowMapper<MatchResultDataLine> {

	@Override
	public MatchResultDataLine mapRow(ResultSet rs, int rowNum) throws SQLException {
		MatchResultDataLine line = new MatchResultDataLine();
		line.setId(rs.getLong("id"));
		line.setPoints(rs.getDouble("points"));
		line.setRank(rs.getInt("rank"));
		line.setScorePercentage(rs.getDouble("scorepercentage"));
		line.setScoredStages(rs.getInt("scoredstages"));
		line.setCompetitorId(rs.getLong("competitor_id"));
		return line;
	}

}
