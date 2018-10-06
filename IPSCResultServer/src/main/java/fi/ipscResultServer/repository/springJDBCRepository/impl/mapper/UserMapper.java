package fi.ipscResultServer.repository.springJDBCRepository.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import fi.ipscResultServer.domain.User;

@Component
public class UserMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int row) throws SQLException {
		User user = new User();
		user.setId(rs.getLong("ID"));
		user.setEmail(rs.getString("EMAIL"));
		user.setEnabled(rs.getBoolean("ENABLED"));
		user.setFirstName(rs.getString("FIRSTNAME"));
		user.setLastName(rs.getString("LASTNAME"));
		user.setPassword(rs.getString("PASSWORD"));
		user.setPhone(rs.getString("PHONE"));
		user.setRole(User.UserRole.valueOf(rs.getString("ROLE")));
		user.setUsername(rs.getString("USERNAME"));
		return user;
	}
}
