package fi.ipscResultServer.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import fi.ipscResultServer.controller.api.ApiController;
import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.domain.Constants;
import fi.ipscResultServer.domain.Match;
import fi.ipscResultServer.domain.practiScore.PractiScoreMatchData;
import fi.ipscResultServer.domain.practiScore.PractiScoreMatchScore;
import fi.ipscResultServer.domain.resultData.MatchResultDataLine;
import fi.ipscResultServer.repository.springJDBCRepository.impl.DatabaseUtil;
import fi.ipscResultServer.service.MatchResultDataService;
import fi.ipscResultServer.service.PractiScoreDataService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-context.xml")
public class PractiScoreDataServiceImplTest {
	
	@Autowired
	private DatabaseUtil databaseUtil;
	
	@Autowired
	private PractiScoreDataService psDataService;
	
	@Autowired
	private MatchResultDataService matchResultDataService; 
	
	private final String MATCH_DEF_FILE = "testMatchDef.json";
	private final String MATCH_SCORE_FILE = "testMatchScore.json";
	
	private final static Logger LOGGER = Logger.getLogger(ApiController.class);
	private static boolean dataSetUp = false;
	
	private String matchPractiScoreId;
	
	@Before
	public void setUpBeforeClass() throws Exception {
		
		if (!dataSetUp) {
			LOGGER.info("Preparing database for testing");
			
			databaseUtil.dropTables();
			databaseUtil.createTables();
			
			try {
				PractiScoreMatchData data;
				ObjectMapper mapper = new ObjectMapper();
				String matchDefJson = readFile(MATCH_DEF_FILE);
				String scoresJson = readFile(MATCH_SCORE_FILE);
				
				Match match = mapper.readValue(matchDefJson, Match.class);
				matchPractiScoreId = match.getPractiScoreId();
				
				PractiScoreMatchScore score = mapper.readValue(scoresJson, PractiScoreMatchScore.class);
				
				data = new PractiScoreMatchData();
				data.setMatch(match);
				data.setMatchScore(score);
				if (data != null) psDataService.save(data);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			dataSetUp = true;
		}
	}

	@After
	public void tearDownAfterClass() throws Exception {

	}

	@Test
	@WithMockUser(username = "admin", authorities = { "ROLE_ADMIN", "ROLE_USER" })
	public void test() {
		
		LOGGER.info("*** Testing match overall results for divisions:");
		List<String[]> testDivisions = new ArrayList<String[]>();
		testDivisions.add(new String[] { Constants.COMBINED_DIVISION, "WasaMatchData/overall-combined.html" });
		testDivisions.add(new String[] { "Production", "WasaMatchData/overall-production.html" });
		testDivisions.add(new String[] { "Production Optics", "WasaMatchData/overall-productionoptics.html" });
		testDivisions.add(new String[] { "Classic", "WasaMatchData/overall-classic.html" });
		testDivisions.add(new String[] { "Revolver", "WasaMatchData/overall-revolver.html" });
		testDivisions.add(new String[] { "Standard", "WasaMatchData/overall-standard.html" });
		testDivisions.add(new String[] { "Open", "WasaMatchData/overall-open.html" });
		
		for (String[] testDiv : testDivisions) {
			String division = testDiv[0];
			String filePath = testDiv[1];
						
			LOGGER.info(division);	
			List<MatchResultDataLine> correctLines = parseMatchResultDataLines(filePath);
			List<MatchResultDataLine> databaseLines = matchResultDataService.findByMatchAndDivision(matchPractiScoreId, division).getDataLines();
			
			removeDQCompetitors(correctLines);
			compareDataLines(databaseLines, correctLines, division);
			
		}
	}
	private void compareDataLines(List<MatchResultDataLine> databaseLines, List<MatchResultDataLine> correctLines, String division) {
		Assert.assertEquals("Database should have " + correctLines.size() + " match result lines for division " + division
				+ ", has " + databaseLines.size(), correctLines.size(), databaseLines.size());
		
		for (MatchResultDataLine dbLine : databaseLines) {
			MatchResultDataLine correctLine = correctLines.get(databaseLines.indexOf(dbLine));
			
			Assert.assertEquals("Match result data line mismatch.", correctLine, dbLine);
		}
	}
	private void removeDQCompetitors(List<MatchResultDataLine> dataLines) {
		List<MatchResultDataLine> linesToRemove = new ArrayList<MatchResultDataLine>();
		for (MatchResultDataLine line : dataLines) {
			if (line.getCompetitor().isDisqualified()) linesToRemove.add(line);
		}
		dataLines.removeAll(linesToRemove);
	
	}
	private String readFile(String filePath) {
		try {
			Resource resource = new ClassPathResource(filePath);
			File file = resource.getFile();
						
			InputStream is = new FileInputStream(file); 
			BufferedReader buf = new BufferedReader(new InputStreamReader(is, "UTF-8")); 
			String line = buf.readLine(); 
			StringBuilder sb = new StringBuilder(); 
			while(line != null){ 
				sb.append(line).append("\n"); 
				line = buf.readLine(); 
				} 
			String fileAsString = sb.toString(); 
			buf.close();
			return fileAsString;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private List<MatchResultDataLine> parseMatchResultDataLines(String filePath) {
		List<MatchResultDataLine> lines = new ArrayList<MatchResultDataLine>();
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource(filePath).getFile());
			Document doc = Jsoup.parse(file, "UTF-8", "http://example.com/");
			Element resultTable = doc.getElementsByTag("table").get(0);
			Elements resultRows = resultTable.getElementsByTag("tr");
			for (Element row : resultRows.subList(2, resultRows.size())) {
				Elements cells = row.getElementsByTag("td");
				int rank = Integer.parseInt(cells.get(0).text());
				Competitor comp = new Competitor();
				String[] names = cells.get(1).text().split(",");
				String lastName = names[0].trim();
				if (lastName.length() > 3 && lastName.substring(0, 4).equals("(DQ)")) {
					comp.setDisqualified(true);
					lastName = lastName.substring(5, lastName.length());
				}
				String firstName = names[1].trim();
				
				comp.setFirstName(firstName);
				comp.setLastName(lastName);
				for (int i = 1; i < names.length; i++) {
					firstName += names[i].trim();
				}
				double score = 0;
				String cellString = cells.get(7).text();
				if (cellString.length() > 0) score = Double.valueOf(cellString);
				double scorePercentage = 0;
				cellString = cells.get(8).text();
				if (cellString.length() > 1) {
					cellString = cellString.substring(0, cellString.length() - 2);
					scorePercentage = Double.valueOf(cellString);
				}
				
				MatchResultDataLine line = new MatchResultDataLine(rank, comp, score, scorePercentage);
				lines.add(line);
			}
			return lines;
		}
		catch (Exception e) {
			e.printStackTrace();
			return lines;
		}
	}
}
