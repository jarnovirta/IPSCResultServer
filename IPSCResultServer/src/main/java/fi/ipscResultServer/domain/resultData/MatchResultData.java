package fi.ipscResultServer.domain.resultData;

import java.util.List;

import fi.ipscResultServer.domain.Match;

public class MatchResultData {
	private Long id;

	private Match match; 
	
	private String division;
		
	private List<MatchResultDataLine> dataLines;
	
	public MatchResultData() { }
	
	public MatchResultData(Match match, String division) {
		this.match = match;
		this.division = division;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public List<MatchResultDataLine> getDataLines() {
		return dataLines;
	}

	public void setDataLines(List<MatchResultDataLine> dataLines) {
		this.dataLines = dataLines;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	
}
