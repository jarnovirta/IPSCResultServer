package fi.ipscResultServer.repository.springJDBCRepository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fi.ipscResultServer.domain.statistics.CompetitorStatistics;

public class CompetitorStatisticsMapper implements RowMapper<CompetitorStatistics> {

	@Override
	public CompetitorStatistics mapRow(ResultSet rs, int rowNum) throws SQLException {
		CompetitorStatistics stat = new CompetitorStatistics();
		stat.setId(rs.getLong("id"));
		stat.setaHitPercentage(rs.getDouble("ahitpercentage"));
		stat.setaHits(rs.getInt("ahits"));
		stat.setAdditionalPenalties(rs.getInt("additionalpenalties"));
		stat.setcHits(rs.getInt("chits"));
		stat.setdHits(rs.getInt("dhits"));
		stat.setDivisionPoints(rs.getDouble("divisionpoints"));
		stat.setDivisionRank(rs.getInt("divisionrank"));
		if (rs.wasNull()) stat.setDivisionRank(null);
		stat.setDivisionScorePercentage(rs.getDouble("divisionscorepercentage"));
		stat.setMatchTime(rs.getDouble("matchtime"));
		stat.setMisses(rs.getInt("misses"));
		stat.setNoShootHits(rs.getInt("noshoothits"));
		stat.setProceduralPenalties(rs.getInt("proceduralpenalties"));
		stat.setSumOfPoints(rs.getInt("sumofpoints"));
		stat.setMatchId(rs.getLong("match_id"));
		stat.setCompetitorId(rs.getLong("competitor_id"));
		
		return stat;
	}

}
