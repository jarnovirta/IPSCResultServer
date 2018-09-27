package fi.ipscResultServer.repository.springJDBCRepository;

import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

@Component
public class DatabaseBuilder {
	private final static Logger LOGGER = Logger.getLogger(DatabaseBuilder.class);
	private final static String SQL_FILE_NAME = "createDatabase.sql";
	
	@Autowired
	private DatabaseUtil dbUtil;
	
	public void runDatabaseBuildScript() {
		LOGGER.info("Running database build script");
		try {
			Resource resource = new ClassPathResource(SQL_FILE_NAME);
			EncodedResource encodedResource = new EncodedResource(resource, Charset.forName("UTF-8"));
			ScriptUtils.executeSqlScript(dbUtil.getDataSource().getConnection(), encodedResource);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
