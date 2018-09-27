package fi.ipscResultServer.domain;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Stage implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@JsonProperty("stage_uuid")
	@Column(length = 36)
	private String practiScoreId;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false)
	private Match match;
	
	@Transient
	private Long matchId;
	
	@JsonProperty("stage_name")
	@Column(nullable = false)
	private String name;
	
	@JsonProperty("stage_scoretype")
	@Transient
	private String scoreType;
	
	@JsonProperty("stage_number")
	@Column(nullable = false)
	private int stageNumber;

	@JsonProperty("stage_targets")
	@Transient
	private Target[] targets; 
	
	@JsonProperty("stage_poppers")
	@Transient
	private int poppers;
	
	private int maxPoints;
	
	@JsonProperty("stage_deleted")
	@Transient
	private boolean deleted = false;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPractiScoreId() {
		return practiScoreId;
	}

	public void setPractiScoreId(String practiScoreId) {
		this.practiScoreId = practiScoreId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getScoreType() {
		return scoreType;
	}

	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}

	public int getStageNumber() {
		return stageNumber;
	}

	public void setStageNumber(int stageNumber) {
		this.stageNumber = stageNumber;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}
	
	public Target[] getTargets() {
		return targets;
	}

	public void setTargets(Target[] targets) {
		this.targets = targets;
		setMaxPoints();
	}

	public int getPoppers() {
		return poppers;
	}

	public void setPoppers(int poppers) {
		this.poppers = poppers;
		setMaxPoints();
	}

	public int getMaxPoints() {
		return maxPoints;
	}

	public void setMaxPoints() {
		this.maxPoints = 0;
		if (this.targets != null && this.targets.length > 0) {
			for (Target target : this.targets) {
				this.maxPoints += target.getRequiredShots() * 5;
			}
		}
		this.maxPoints += poppers * 5;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void setMaxPoints(int maxPoints) {
		this.maxPoints = maxPoints;
	}

	public Long getMatchId() {
		return matchId;
	}

	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}
	
}
