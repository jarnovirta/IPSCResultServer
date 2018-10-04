package fi.ipscResultServer.repository.springJDBCRepository.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fi.ipscResultServer.domain.statistics.CompetitorStatistics;

public class StatisticsMapper implements RowMapper<CompetitorStatistics> {

	@Override
	public CompetitorStatistics mapRow(ResultSet rs, int rowNum) throws SQLException {
		CompetitorStatistics stat = new CompetitorStatistics();
		stat.setCompetitorId(rs.getLong("competitor_id"));
		stat.setaHits(rs.getInt("ahitssum"));
		stat.setcHits(rs.getInt("chitssum"));
		stat.setdHits(rs.getInt("dhitssum"));
		stat.setMisses(rs.getInt("missessum"));
		stat.setNoShootHits(rs.getInt("nshitssum"));
		stat.setProceduralPenalties(rs.getInt("procsum"));
		stat.setAdditionalPenalties(rs.getInt("adpensum"));
		stat.setSumOfPoints(rs.getInt("pointssum"));
		stat.setMatchTime(rs.getDouble("timesum"));
		stat.setDivisionPoints(rs.getDouble("stagepointssum"));
		int shotsSum = stat.getaHits() + stat.getcHits() + stat.getdHits() + stat.getMisses() 
			+ stat.getNoShootHits();
		double aHitsPercentage = (double) stat.getaHits() / shotsSum * 100;
		stat.setaHitPercentage(aHitsPercentage);
		
		return stat;

	}
	

}
