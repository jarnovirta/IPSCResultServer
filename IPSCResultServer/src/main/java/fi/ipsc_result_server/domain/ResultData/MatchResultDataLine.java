package fi.ipsc_result_server.domain.ResultData;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import fi.ipsc_result_server.domain.Competitor;

@Entity
public class MatchResultDataLine {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	@ManyToOne
	private MatchResultData matchResultData;
	
	@ManyToOne
	private Competitor competitor;
	
	private int matchScorePercentage;
	
	private int matchPoints;

	private int rank;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MatchResultData getMatchResultData() {
		return matchResultData;
	}

	public void setMatchResultData(MatchResultData matchResultData) {
		this.matchResultData = matchResultData;
	}

	public Competitor getCompetitor() {
		return competitor;
	}

	public void setCompetitor(Competitor competitor) {
		this.competitor = competitor;
	}

	public int getMatchScorePercentage() {
		return matchScorePercentage;
	}

	public void setMatchScorePercentage(int matchScorePercentage) {
		this.matchScorePercentage = matchScorePercentage;
	}

	public int getMatchPoints() {
		return matchPoints;
	}

	public void setMatchPoints(int matchPoints) {
		this.matchPoints = matchPoints;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
}
