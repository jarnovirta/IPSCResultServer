package fi.ipscResultServer.domain;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreCard {
	
	private Long id;
	
	@JsonProperty("shtr")
	private String competitorPractiScoreId;
	
	
	private Competitor competitor;
	
	private Long competitorId;
	
	private Stage stage;
	
	private Long stageId;
	
	@JsonProperty("mod")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	private Calendar modified;
	
	@JsonProperty("popm")
	private int popperMisses = 0;
	
	@JsonProperty("poph")
	private int popperHits = 0;
	
	@JsonProperty("popns")
	private int popperNoshootHits = 0;
	
	@JsonProperty("popnpm")
	private int popperNonPenaltyMisses = 0;
	
	@JsonProperty("rawpts")
	private int points = 0;
	
	@JsonProperty("str")
	private double[] stringTimes;
	
	private double time;
	
	@JsonProperty("ts")
	private int[] paperTargetHits = {};
	
	private int aHits = 0;
	
	private int cHits = 0;
	
	private int dHits = 0;
	
	private int misses = 0;
	
	private int noshootHits = 0;
	
	@JsonProperty("proc")
	private int proceduralPenalties = 0;
	
	@JsonProperty("apen")
	private int additionalPenalties = 0;
	
	private double hitFactor;
	
	private int stagePoints;
	
	private int stageRank;
	
	public String getCompetitorPractiScoreId() {
		return competitorPractiScoreId;
	}

	public void setCompetitorPractiScoreId(String competitorPractiScoreId) {
		this.competitorPractiScoreId = competitorPractiScoreId;
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
	public int getPopperNoshootHits() {
		return popperNoshootHits;
	}
	public void setPopperNoshootHits(int popperNoshootHits) {
		this.popperNoshootHits = popperNoshootHits;
		this.noshootHits += popperNoshootHits;
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
		int cHitsBitShift = 8;
		int dHitsBitShift = 12;
		int noshootHitsBitShift = 16;
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
		this.noshootHits += popperNoshootHits;
		
		this.points -= noshootHits * 10;
		this.points -= misses * 10;
		this.points -= proceduralPenalties * 10;
		this.points -= additionalPenalties;
		
		if (this.points >=0 && this.time > 0 && competitor.getPowerFactor() != null 
				&& !competitor.getPowerFactor().equals("SUBMINOR")) {
			this.hitFactor = this.points / this.time;
		}
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

	public double getHitFactor() {
		return hitFactor;
	}

	public void setHitFactor(double hitFactor) {
		this.hitFactor = hitFactor;
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

	public void setStringTimes(double[] stringTimes) {
		if (stringTimes != null && stringTimes.length > 0) {
			this.time = stringTimes[0];
		}
	}

	public void setTime(double time) {
		this.time = time;
	}

	public int getAdditionalPenalties() {
		return additionalPenalties;
	}

	public void setAdditionalPenalties(int additionalPenalties) {
		this.additionalPenalties = additionalPenalties;
	}

	public Long getStageId() {
		return stageId;
	}

	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}

	public Long getCompetitorId() {
		return competitorId;
	}

	public void setCompetitorId(Long competitorId) {
		this.competitorId = competitorId;
	}
	
}
