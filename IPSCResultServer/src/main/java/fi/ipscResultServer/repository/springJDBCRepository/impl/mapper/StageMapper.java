package fi.ipscResultServer.repository.springJDBCRepository.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fi.ipscResultServer.domain.Stage;

public class StageMapper implements RowMapper<Stage> {

	@Override
	public Stage mapRow(ResultSet rs, int rowNum) throws SQLException {
		Stage stage = new Stage();
		stage.setId(rs.getLong("ID"));
		stage.setName(rs.getString("NAME"));
		stage.setMaxPoints(rs.getInt("MAXPOINTS"));
		stage.setPractiScoreId(rs.getString("PRACTISCOREID"));
		stage.setStageNumber(rs.getInt("STAGENUMBER"));
		stage.setMatchId(rs.getLong("MATCH_ID"));
		stage.setMatchStagesIndex(rs.getInt("STAGES_ORDER"));		
		return stage;
	}

}
