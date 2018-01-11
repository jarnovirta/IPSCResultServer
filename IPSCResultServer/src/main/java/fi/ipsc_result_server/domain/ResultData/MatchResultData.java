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

import fi.ipsc_result_server.domain.Match;

@Entity
public class MatchResultData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@ManyToOne(cascade = CascadeType.MERGE)
	private Match match; 
	
	@OneToMany(mappedBy = "matchResultData", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<MatchResultDataLine> dataLines;
	
	public MatchResultData() { }
	
	public MatchResultData(Match match) {
		this.match = match;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<MatchResultDataLine> getDataLines() {
		return dataLines;
	}

	public void setDataLines(List<MatchResultDataLine> dataLines) {
		this.dataLines = dataLines;
	}
	
	
}
