package fi.ipscResultServer.repository.springJDBCRepository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fi.ipscResultServer.domain.User;
import fi.ipscResultServer.repository.springJDBCRepository.UserRepository;
import fi.ipscResultServer.repository.springJDBCRepository.impl.mapper.UserMapper;

@Repository
public class UserRepositoryImpl implements UserRepository {
	@Autowired
	private DatabaseUtil dbUtil;
	
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
    public void init() {
        jdbcTemplate = dbUtil.getJdbcTemplate();
    }
	
	public User getOne(Long id) {
		try {
			String query = "SELECT * FROM USER WHERE ID = ?";
			return jdbcTemplate.queryForObject(query, new Object[] { id }, new UserMapper());
		}
		catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	@Transactional
	public User save(User user) {
		String sql = "INSERT INTO USER (EMAIL, ENABLED, FIRSTNAME, LASTNAME, PASSWORD, PHONE, ROLE, USERNAME)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql, new String[] { "ID" });
				if (user.getEmail() == null) ps.setNull(1, java.sql.Types.NULL);
				else ps.setString(1, user.getEmail());
				ps.setBoolean(2, user.isEnabled());
				if (user.getFirstName() == null) ps.setNull(3, java.sql.Types.NULL);
				else ps.setString(3, user.getFirstName());
				if (user.getLastName() == null) ps.setNull(4, java.sql.Types.NULL);
				else ps.setString(4, user.getLastName());
				ps.setString(5, user.getPassword());
				if (user.getPhone() == null) ps.setNull(5, java.sql.Types.NULL);
				else ps.setString(6, user.getPhone());
				ps.setString(7, user.getRole().toString());
				ps.setString(8, user.getUsername());				
				return ps;
			}
		}, keyHolder);
		user.setId(keyHolder.getKey().longValue());
		
		return user;
	}
	@Transactional
	public void updateUser(User user) {
		String sql = "UPDATE USER SET EMAIL = ?, ENABLED = ?, FIRSTNAME = ?, LASTNAME = ?, "
				+ "PASSWORD = ?, PHONE = ?, ROLE = ?, USERNAME = ? WHERE ID = ?";
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql);
				if (user.getEmail() == null) ps.setNull(1, java.sql.Types.NULL);
				else ps.setString(1, user.getEmail());
				ps.setBoolean(2, user.isEnabled());
				if (user.getFirstName() == null) ps.setNull(3, java.sql.Types.NULL);
				else ps.setString(3, user.getFirstName());
				if (user.getLastName() == null) ps.setNull(4, java.sql.Types.NULL);
				else ps.setString(4, user.getLastName());
				ps.setString(5, user.getPassword());
				if (user.getPhone() == null) ps.setNull(5, java.sql.Types.NULL);
				else ps.setString(6, user.getPhone());
				ps.setString(7, user.getRole().toString());
				ps.setString(8, user.getUsername());				
				ps.setLong(9, user.getId());
				return ps;
			}
		});
	}
			
	public List<User> findEnabledUsers() {
		String sql = "SELECT * FROM USER WHERE ENABLED = 1";
		return jdbcTemplate.query(sql, new UserMapper());
	}
	
	public User findByUsername(String username) {
		String sql = "SELECT * FROM USER WHERE USERNAME = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] { username }, new UserMapper());
	}
	
}
