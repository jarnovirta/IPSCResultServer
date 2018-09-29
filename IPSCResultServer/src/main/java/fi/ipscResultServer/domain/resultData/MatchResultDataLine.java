package fi.ipscResultServer.domain.resultData;

import fi.ipscResultServer.domain.Competitor;

public class MatchResultDataLine implements Comparable<MatchResultDataLine> {
	private Long id;
	
	private MatchResultData matchResultData;
	
	private Competitor competitor;
	
	private Long competitorId;
	
	private double scorePercentage = 0;
	
	private double points = 0;

	private int rank;
	
	private int scoredStages;
	
	public MatchResultDataLine() { }
	public MatchResultDataLine(Competitor competitor, MatchResultData matchResultData) {
		this.competitor = competitor;
		this.matchResultData = matchResultData;
	}
	@Override
	public int compareTo(MatchResultDataLine compareToDataLine) {
	    final int EQUAL = 0;
	    if (this == compareToDataLine) return EQUAL;
	    return new Double(compareToDataLine.getPoints()).compareTo(new Double(this.points));
	}
	
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

	public double getScorePercentage() {
		return scorePercentage;
	}

	public void setScorePercentage(double scorePercentage) {
		this.scorePercentage = scorePercentage;
	}

	public double getPoints() {
		return points;
	}

	public void setPoints(double points) {
		this.points = points;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getScoredStages() {
		return scoredStages;
	}

	public void setScoredStages(int scoredStages) {
		this.scoredStages = scoredStages;
	}
	public Long getCompetitorId() {
		return competitorId;
	}
	public void setCompetitorId(Long competitorId) {
		this.competitorId = competitorId;
	}
	
	
}
