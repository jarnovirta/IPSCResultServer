package fi.ipscResultServer.repository.springJDBCRepository.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CompetitorCategoriesMapper implements RowMapper<Object[]>{

	@Override
	public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Object[] { 
				rs.getLong("competitor_id"), 
				rs.getString("categories") 
				};
	}
}
