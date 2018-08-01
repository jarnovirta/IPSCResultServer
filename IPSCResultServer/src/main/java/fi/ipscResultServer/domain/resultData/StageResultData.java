package fi.ipscResultServer.domain.resultData;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import fi.ipscResultServer.domain.Stage;

@Entity
public class StageResultData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	@ManyToOne
	private Stage stage;
	
	private String division;
	
	@OneToMany(mappedBy = "stageResultData", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
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

	public static long getSerialversionuid() {
		return serialVersionUID;
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
