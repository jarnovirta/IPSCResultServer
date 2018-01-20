package fi.ipsc_result_server.domain;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class ScoreCard implements Serializable, Comparable<ScoreCard> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	@JsonProperty("shtr")
	@Column(nullable = false, length = 36)
	private String competitorId;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Competitor competitor;
	
	@ManyToOne
	private Stage stage;
	
	private String stageId;
	
	@JsonProperty("mod")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Calendar modified;

	
	@JsonProperty("popm")
	@Column(nullable = false)
	private int popperMisses;
	
	@JsonProperty("poph")
	@Column(nullable = false)
	private int popperHits;
	
	@JsonProperty("popnpm")
	@Column(nullable = false)
	private int popperNonPenaltyMisses;
	
	@JsonProperty("rawpts")
	@Column(nullable = false)
	private int points;
	
	@JsonProperty("str")
	private double[] stringTimes;
	
	@Column(nullable = false)
	private double time;
	
	private double roundedTime;
	
	@JsonProperty("ts")
	@Transient
	private int[] paperTargetHits;
	
	@JsonIgnore
	private int aHits;
	
	@JsonIgnore
	private int cHits;
	
	@JsonIgnore
	private int dHits;
	
	@JsonIgnore
	private int misses;
	
	@JsonIgnore
	private int noshootHits;
	
	@JsonProperty("proc")
	private int proceduralPenalties;
	
	@JsonIgnore
	@Column(nullable = false)
	private double hitFactor;

	@JsonIgnore
	private StageScore stageScore;
	
	@JsonIgnore
	private int stageScorePercentage;
	
	@JsonIgnore
	private int stagePoints;
	
	@JsonIgnore
	private int stageRank;
	
	@Override
	public int compareTo(ScoreCard compareToScoreCard) {
	    final int EQUAL = 0;
	    if (this == compareToScoreCard) return EQUAL;
	    return new Double(compareToScoreCard.getHitFactor()).compareTo(new Double(this.hitFactor));
	}
	public String getShooterId() {
		return competitorId;
	}

	public void setShooterId(String shooterId) {
		this.competitorId = shooterId;
	}

	public Calendar getModified() {
		return modified;
	}

	public void setModified(Calendar modified) {
		this.modified = modified;

	}

	public int getPopperMisses() {
		return popperMisses;
	}

	public void setPopperMisses(int popperMisses) {
		this.popperMisses = popperMisses;
	}

	public int getPopperHits() {
		return popperHits;
	}

	public void setPopperHits(int popperHits) {
		this.popperHits = popperHits;
	}

	public int getPopperNonPenaltyMisses() {
		return popperNonPenaltyMisses;
	}

	public void setPopperNonPenaltyMisses(int popperNonPenaltyMisses) {
		this.popperNonPenaltyMisses = popperNonPenaltyMisses;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public double getTime() {
		return time;
	}

	public int[] getPaperTargetHits() {
		return paperTargetHits;
	}

	public void setPaperTargetHits(int[] paperTargetHits) {
		this.paperTargetHits = paperTargetHits;

	}
	
	public void setHitsAndPoints() {
		// Count total hits from PractiScore hits data
		this.aHits = 0;
		this.cHits = 0;
		int cHitsBitShift = 8;
		this.dHits = 0;
		int dHitsBitShift = 12;
		this.noshootHits = 0;
		int noshootHitsBitShift = 16;
		this.misses = 0;
		int missesBitShift = 20;
		
		int bitMask = 0xF;

		for (int hits : paperTargetHits) {
			this.aHits += hits & bitMask;
			this.cHits += (hits >> cHitsBitShift) & bitMask;
			this.dHits += (hits >> dHitsBitShift) & bitMask;
			this.noshootHits += (hits >> noshootHitsBitShift) & bitMask;
			this.misses += (hits >> missesBitShift) & bitMask;
		}
		
		this.aHits += this.popperHits;
		this.misses += popperMisses;
		
		this.points -= noshootHits * 10;
		this.points -= misses * 10;
		this.points -= proceduralPenalties * 10;
		
		if (this.points > 0) this.hitFactor = this.points / this.time;
		else {
			this.points = 0;
			this.hitFactor = 0;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getaHits() {
		return aHits;
	}

	public void setaHits(int aHits) {
		this.aHits = aHits;
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

	public int getNoshootHits() {
		return noshootHits;
	}

	public void setNoshootHits(int noshootHits) {
		this.noshootHits = noshootHits;
	}

	public int getProceduralPenalties() {
		return proceduralPenalties;
	}

	public void setProceduralPenalties(int proceduralPenalties) {
		this.proceduralPenalties = proceduralPenalties;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public StageScore getStageScore() {
		return stageScore;
	}

	public void setStageScore(StageScore stageScore) {
		this.stageScore = stageScore;
	}

	public String getCompetitorId() {
		return competitorId;
	}

	public void setCompetitorId(String competitorId) {
		this.competitorId = competitorId;
	}

	public Competitor getCompetitor() {
		return competitor;
	}

	public void setCompetitor(Competitor competitor) {
		this.competitor = competitor;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public String getStageId() {
		return stageId;
	}

	public void setStageId(String stageId) {
		this.stageId = stageId;
	}

	public double getHitFactor() {
		return hitFactor;
	}

	public void setHitFactor(double hitFactor) {
		this.hitFactor = hitFactor;
	}

	public int getStageScorePercentage() {
		return stageScorePercentage;
	}

	public void setStageScorePercentage(int stageScorePercentage) {
		this.stageScorePercentage = stageScorePercentage;
	}

	public int getStagePoints() {
		return stagePoints;
	}

	public void setStagePoints(int stagePoints) {
		this.stagePoints = stagePoints;
	}

	public int getStageRank() {
		return stageRank;
	}

	public void setStageRank(int stageRank) {
		this.stageRank = stageRank;
	}

	public double[] getStringTimes() {
		return stringTimes;
	}

	public void setStringTimes(double[] stringTimes) {
		if (stringTimes != null && stringTimes.length > 0) {
			this.time = stringTimes[0];
		}
		this.stringTimes = stringTimes;
		
	}
	public double getRoundedTime() {
		return roundedTime;
	}
	public void setRoundedTime(double roundedTime) {
		this.roundedTime = roundedTime;
	}

	public void setTime(double time) {
		this.time = time;
	}

}
