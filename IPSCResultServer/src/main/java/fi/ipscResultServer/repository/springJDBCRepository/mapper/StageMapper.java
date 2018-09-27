package fi.ipscResultServer.repository.springJDBCRepository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fi.ipscResultServer.domain.Stage;

public class StageMapper implements RowMapper<Stage> {

	@Override
	public Stage mapRow(ResultSet rs, int rowNum) throws SQLException {
		Stage stage = new Stage();
		stage.setId(rs.getLong("id"));
		stage.setName(rs.getString("name"));
		stage.setMaxPoints(rs.getInt("maxpoints"));
		stage.setPractiScoreId(rs.getString("practiscoreid"));
		stage.setStageNumber(rs.getInt("stagenumber"));
		stage.setMatchId(rs.getLong("match_id"));
		stage.setMatchStagesIndex(rs.getInt("stages_ORDER"));		
		return stage;
	}

}
