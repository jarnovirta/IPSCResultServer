package fi.ipsc_result_server.domain;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class ScoreCard implements Serializable {
	
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
	
	@ManyToOne(cascade = CascadeType.MERGE)
	private Competitor competitor;
	
	@ManyToOne(cascade = CascadeType.MERGE)
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
	@Column(nullable = false)
	private double[] time;
	
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
	@JsonIgnore
	private int proceduralPenalties;

//	@ManyToOne(cascade = CascadeType.MERGE)
	private StageScore stageScore;
	
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

	public double[] getTime() {
		return time;
	}

	public void setTime(double[] time) {
		this.time = time;
	}

	public int[] getPaperTargetHits() {
		return paperTargetHits;
	}

	public void setPaperTargetHits(int[] paperTargetHits) {
		this.paperTargetHits = paperTargetHits;

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
}
