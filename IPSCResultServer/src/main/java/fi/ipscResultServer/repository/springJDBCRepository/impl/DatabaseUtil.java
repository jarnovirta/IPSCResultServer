package fi.ipscResultServer.repository.springJDBCRepository.impl;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseUtil {
	@Autowired
	DataSource dataSource;
	
	@Autowired 
	DatabaseBuilder databaseBuilder;
	
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
    public void init() {
		jdbcTemplate = new JdbcTemplate(dataSource);
        databaseBuilder.runDatabaseBuildScript();
    }
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}
}
