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
	
	@ManyToOne(cascade = CascadeType.MERGE)
	private Stage stage;
	
	@OneToMany(mappedBy = "stageResultData", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<StageResultDataLine> dataLines;

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
	
}
