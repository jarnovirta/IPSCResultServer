package fi.ipsc_result_server.domain.ResultData;

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

import fi.ipsc_result_server.domain.IPSCDivision;
import fi.ipsc_result_server.domain.Stage;

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
	
	private IPSCDivision division;
	
	@OneToMany(mappedBy = "stageResultData", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<StageResultDataLine> dataLines;

	public StageResultData() { }
	
	public StageResultData(Stage stage, IPSCDivision division) { 
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

	public IPSCDivision getDivision() {
		return division;
	}

	public void setDivision(IPSCDivision division) {
		this.division = division;
	}
	
}
