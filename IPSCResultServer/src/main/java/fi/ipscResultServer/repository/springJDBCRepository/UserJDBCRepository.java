package fi.ipscResultServer.repository.springJDBCRepository;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import fi.ipscResultServer.domain.User;
import fi.ipscResultServer.repository.springJDBCRepository.mapper.UserMapper;

@Repository
public class UserJDBCRepository {
	@Autowired
	private DatabaseUtil dbUtil;
	
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
    public void init() {
        jdbcTemplate = dbUtil.getJdbcTemplate();
    }
	
	public User getOne(Long id) {
		String query = "SELECT * FROM user WHERE id = ?";
		return jdbcTemplate.queryForObject(query, new Object[] { id }, new UserMapper());
	}
}
