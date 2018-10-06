package fi.ipscResultServer.repository.springJDBCRepository.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fi.ipscResultServer.domain.statistics.CompetitorStatistics;

public class StatisticsMapper implements RowMapper<CompetitorStatistics> {

	@Override
	public CompetitorStatistics mapRow(ResultSet rs, int rowNum) throws SQLException {
		CompetitorStatistics stat = new CompetitorStatistics();
		stat.setCompetitorId(rs.getLong("COMPETITOR_ID"));
		stat.setaHits(rs.getInt("AHITSSUM"));
		stat.setcHits(rs.getInt("CHITSSUM"));
		stat.setdHits(rs.getInt("DHITSSUM"));
		stat.setMisses(rs.getInt("MISSESSUM"));
		stat.setNoShootHits(rs.getInt("NSHITSSUM"));
		stat.setProceduralPenalties(rs.getInt("PROCSUM"));
		stat.setAdditionalPenalties(rs.getInt("ADPENSUM"));
		stat.setSumOfPoints(rs.getInt("POINTSSUM"));
		stat.setMatchTime(rs.getDouble("TIMESUM"));
		stat.setDivisionPoints(rs.getDouble("STAGEPOINTSSUM"));
		int shotsSum = stat.getaHits() + stat.getcHits() + stat.getdHits() + stat.getMisses() 
			+ stat.getNoShootHits();
		double aHitsPercentage = (double) stat.getaHits() / shotsSum * 100;
		stat.setaHitPercentage(aHitsPercentage);
		
		return stat;

	}
	

}
