package fi.ipscResultServer.repository.springJDBCRepository.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.PowerFactor;

@Component
public class CompetitorMapper implements RowMapper<Competitor> {
	public Competitor mapRow(ResultSet rs, int rowNum) throws SQLException {
		Competitor comp = new Competitor();
		comp.setId(rs.getLong("ID"));
		comp.setDisqualified(rs.getBoolean("DISQUALIFIED"));
		comp.setDivision(rs.getString("DIVISION"));
		comp.setFirstName(rs.getString("FIRSTNAME"));
		comp.setIpscAlias(rs.getString("IPSCALIAS"));
		comp.setLastName(rs.getString("LASTNAME"));
		comp.setPowerFactor(PowerFactor.values()[rs.getInt("POWERFACTOR")]);
		comp.setPractiScoreId(rs.getString("PRACTISCOREID"));
		comp.setShooterNumber(rs.getInt("SHOOTERNUMBER"));
		comp.setSquad(rs.getInt("SQUAD"));
		comp.setTeam(rs.getString("TEAM"));
		comp.setMatchId(rs.getLong("MATCH_ID"));
		comp.setCountry(rs.getString("COUNTRY"));
		return comp;
	}
}
