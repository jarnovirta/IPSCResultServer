package fi.ipscResultServer.domain.ResultData;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import fi.ipscResultServer.domain.IPSCDivision;
import fi.ipscResultServer.domain.Match;

@Entity
public class MatchResultData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@ManyToOne
	private Match match; 
	
	@Enumerated(EnumType.ORDINAL)
	private IPSCDivision division;
		
	@OneToMany(mappedBy = "matchResultData", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MatchResultDataLine> dataLines;
	
	public MatchResultData() { }
	
	public MatchResultData(Match match, IPSCDivision division) {
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<MatchResultDataLine> getDataLines() {
		return dataLines;
	}

	public void setDataLines(List<MatchResultDataLine> dataLines) {
		this.dataLines = dataLines;
	}

	public IPSCDivision getDivision() {
		return division;
	}

	public void setDivision(IPSCDivision division) {
		this.division = division;
	}
}
