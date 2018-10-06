package fi.ipscResultServer.domain.resultData;

import fi.ipscResultServer.domain.Competitor;
import fi.ipscResultServer.util.DataFormatUtils;

public class MatchResultDataLine implements Comparable<MatchResultDataLine> {
	private Long id;
	
//	private MatchResultData matchResultData;
	
	private Competitor competitor;
	
	private Long competitorId;
	
	private double scorePercentage = 0;
	
	private double points = 0;

	private int rank;
	
	private int scoredStages;
	
	public MatchResultDataLine() { }
	
	public MatchResultDataLine(Long competitorId, double points, int scoredStages) {
		this.competitorId = competitorId;
		this.points = points;
		this.scoredStages = scoredStages;

	}
	
	public MatchResultDataLine(int rank, Competitor competitor, double points, double scorePercentage) {
		this.rank = rank;
		this.competitor = competitor;
		this.points = points;
		this.scorePercentage = scorePercentage;
	}
	@Override
	public int compareTo(MatchResultDataLine compareToDataLine) {
	    final int EQUAL = 0;
	    if (this == compareToDataLine) return EQUAL;
	    return new Double(compareToDataLine.getPoints()).compareTo(new Double(this.points));
	}
	
	@Override
	public String toString() {
		return rank + ".\t" + competitor.getLastName() + ", " + competitor.getFirstName() + "\tpoints: " + points + "\t" + scorePercentage + " %";
	}
	
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!MatchResultDataLine.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        final MatchResultDataLine other = (MatchResultDataLine) obj;

        if (this.competitorId != null && other.getCompetitorId() != null && !this.competitorId.equals(other.getCompetitorId())) return false;
        
        if ((this.competitor.getFirstName() == null) ? (other.getCompetitor().getFirstName() != null) : !this.competitor.getFirstName().equals(other.getCompetitor().getFirstName())) {
            return false;
        }
        
        if ((this.competitor.getLastName() == null) ? (other.getCompetitor().getLastName() != null) : !this.competitor.getLastName().equals(other.getCompetitor().getLastName())) {
            return false;
        }

    	if (DataFormatUtils.round(this.scorePercentage, 1) != DataFormatUtils.round(other.getScorePercentage(), 1)) return false;
    	
    	if (DataFormatUtils.round(this.points, 1) != DataFormatUtils.round(other.getPoints(), 1)) return false;
    	
    	if (this.rank != other.getRank()) return false;
    	
    	if (this.scoredStages != 0 && other.getScoredStages() != 0 && this.scoredStages != other.getScoredStages()) return false;
    	
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.competitor != null ? this.competitor.getLastName().hashCode() : 0);
        hash = 53 * hash + (this.competitor != null ? this.competitor.getFirstName().hashCode() : 0);
        hash = 53 * hash + this.rank;
        return hash;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
