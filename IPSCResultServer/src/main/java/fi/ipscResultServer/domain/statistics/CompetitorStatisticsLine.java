package fi.ipscResultServer.domain.statistics;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import fi.ipscResultServer.domain.Competitor;

@Entity
public class CompetitorStatisticsLine implements Serializable, Comparable<CompetitorStatisticsLine> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	@ManyToOne
	private Competitor competitor;

	private double matchTime;
	
	private int aHits;
	
	private double aHitPercentage;
	
	private int cHits;
	
	private int dHits;
	
	private int misses;
	
	private int proceduralPenalties;
	
	private int noShootHits;
	
	private int sumOfPoints;
	
	private int divisionRank;
	
	private double divisionPoints;
	
	private double divisionScorePercentage;
	
	public CompetitorStatisticsLine() { }
	
	public CompetitorStatisticsLine(Competitor competitor) { 
		this.competitor = competitor;
	}
	
	@Override
	public int compareTo(CompetitorStatisticsLine competitorStatisticsLine) {
	    final int EQUAL = 0;
	    if (this == competitorStatisticsLine) return EQUAL;
	    if (this.divisionRank < competitorStatisticsLine.getDivisionRank()) return -1;
	    return 1;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public double getMatchTime() {
		return matchTime;
	}

	public void setMatchTime(double matchTime) {
		this.matchTime = matchTime;
	}

	public int getaHits() {
		return aHits;
	}

	public void setaHits(int aHits) {
		this.aHits = aHits;
	}

	public double getaHitPercentage() {
		return aHitPercentage;
	}

	public void setaHitPercentage(double aHitPercentage) {
		this.aHitPercentage = aHitPercentage;
	}

	public int getcHits() {
		return cHits;
	}

	public void setcHits(int cHits) {
		this.cHits = cHits;
	}

	public int getdHits() {
		return dHits;
	}

	public void setdHits(int dHits) {
		this.dHits = dHits;
	}

	public int getMisses() {
		return misses;
	}

	public void setMisses(int misses) {
		this.misses = misses;
	}

	public int getProceduralPenalties() {
		return proceduralPenalties;
	}

	public void setProceduralPenalties(int proceduralPenalties) {
		this.proceduralPenalties = proceduralPenalties;
	}

	public int getNoShootHits() {
		return noShootHits;
	}

	public void setNoShootHits(int noShootHits) {
		this.noShootHits = noShootHits;
	}

	public int getSumOfPoints() {
		return sumOfPoints;
	}

	public void setSumOfPoints(int sumOfPoints) {
		this.sumOfPoints = sumOfPoints;
	}

	public int getDivisionRank() {
		return divisionRank;
	}

	public void setDivisionRank(int divisionRank) {
		this.divisionRank = divisionRank;
	}

	public double getDivisionPoints() {
		return divisionPoints;
	}

	public void setDivisionPoints(double divisionPoints) {
		this.divisionPoints = divisionPoints;
	}

	public double getDivisionScorePercentage() {
		return divisionScorePercentage;
	}

	public void setDivisionScorePercentage(double divisionScorePercentage) {
		this.divisionScorePercentage = divisionScorePercentage;
	}

}
