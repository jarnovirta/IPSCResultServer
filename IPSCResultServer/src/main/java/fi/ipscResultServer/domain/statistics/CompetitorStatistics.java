package fi.ipscResultServer.domain.statistics;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import fi.ipscResultServer.domain.Match;

@Entity
public class CompetitorStatistics implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	@OneToOne
	private Match match;
	
	private String division;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CompetitorStatisticsLine> statisticsLines;
	
	public CompetitorStatistics() { }
	
	public CompetitorStatistics(Match match, String division) {
		this.match = match;
		this.division = division;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}
}
