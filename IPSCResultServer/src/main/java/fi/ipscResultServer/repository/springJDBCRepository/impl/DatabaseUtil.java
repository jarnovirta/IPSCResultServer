package fi.ipscResultServer.repository.springJDBCRepository.impl;

import java.nio.charset.Charset;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

@Component
public class DatabaseUtil {

	@Autowired
	DataSource dataSource;
	
	private JdbcTemplate jdbcTemplate;
	
	private final String SQL_FILE_NAME = "createDatabase.sql";
	
	private final static Logger LOGGER = Logger.getLogger(DatabaseUtil.class);
	
	@PostConstruct
    public void init() {
		jdbcTemplate = new JdbcTemplate(dataSource);
        createTables();
    }
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	public void dropTables() {
		LOGGER.info("Dropping database tables");
		String[] sql = new String[] { 
				"DROP TABLE IF EXISTS scorecard;",
				"DROP TABLE IF EXISTS user;",
				"DROP TABLE IF EXISTS stage;",
				"DROP TABLE IF EXISTS match_divisions",
				"DROP TABLE IF EXISTS competitor_categories",
				"DROP TABLE IF EXISTS competitor",
				"DROP TABLE IF EXISTS ipscmatch"
		};
		for (int i = 0; i < sql.length ; i++) jdbcTemplate.execute(sql[i]);
	}
	
	public void createTables() {
		LOGGER.info("Creating database tables");
		try {
			Resource resource = new ClassPathResource(SQL_FILE_NAME);
			EncodedResource encodedResource = new EncodedResource(resource, Charset.forName("UTF-8"));
			ScriptUtils.executeSqlScript(dataSource.getConnection(), encodedResource);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
