package fi.ipsc_result_server.domain.statistics;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import fi.ipsc_result_server.domain.IPSCDivision;
import fi.ipsc_result_server.domain.Match;

@Entity
public class CompetitorStatistics implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private String id;
	
	@OneToOne
	private Match match;
	
	@Enumerated(EnumType.ORDINAL)
	private IPSCDivision division;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	List<CompetitorStatisticsLine> statisticsLines;
	
	public CompetitorStatistics() { }
	
	public CompetitorStatistics(Match match, IPSCDivision division) {
		this.match = match;
		this.division = division;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public List<CompetitorStatisticsLine> getStatisticsLines() {
		return statisticsLines;
	}

	public void setStatisticsLines(List<CompetitorStatisticsLine> statisticsLines) {
		this.statisticsLines = statisticsLines;
	}

	public IPSCDivision getDivision() {
		return division;
	}

	public void setDivision(IPSCDivision division) {
		this.division = division;
	}
}
