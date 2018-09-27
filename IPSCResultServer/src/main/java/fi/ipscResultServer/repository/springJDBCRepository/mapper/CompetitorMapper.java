package fi.ipscResultServer.repository.springJDBCRepository.mapper;

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
		comp.setId(rs.getLong("id"));
		comp.setDisqualified(rs.getBoolean("disqualified"));
		comp.setDivision(rs.getString("division"));
		comp.setFirstName(rs.getString("firstname"));
		comp.setIpscAlias(rs.getString("ipscalias"));
		comp.setLastName(rs.getString("lastname"));
		comp.setPowerFactor(PowerFactor.values()[rs.getInt("powerfactor")]);
		comp.setPractiScoreId(rs.getString("practiscoreid"));
		comp.setShooterNumber(rs.getInt("shooternumber"));
		comp.setSquad(rs.getInt("squad"));
		comp.setTeam(rs.getString("team"));
		return comp;
	}
}
