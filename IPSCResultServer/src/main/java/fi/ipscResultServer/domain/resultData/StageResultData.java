package fi.ipscResultServer.domain.resultData;

import java.util.List;

import fi.ipscResultServer.domain.Stage;

public class StageResultData {
	
	private Long id;
	
	private Stage stage;
	
	private String division;
	
	private List<StageResultDataLine> dataLines;

	public StageResultData() { }
	
	public StageResultData(Stage stage, String division) { 
		this.stage = stage;
		this.division = division;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public List<StageResultDataLine> getDataLines() {
		return dataLines;
	}

	public void setDataLines(List<StageResultDataLine> dataLines) {
		this.dataLines = dataLines;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}
	
}
