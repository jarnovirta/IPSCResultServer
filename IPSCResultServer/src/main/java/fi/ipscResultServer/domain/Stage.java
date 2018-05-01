package fi.ipscResultServer.domain;

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
	@ManyToOne
	@JoinColumn(nullable = false)
	private Match match;
	
	@JsonProperty("stage_name")
	@Column(nullable = false)
	private String name;
	
	@JsonProperty("stage_number")
	@Column(nullable = false)
	private int stageNumber;
	
	@JsonProperty("stage_modifieddate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Calendar modifiedDate;

	@JsonProperty("stage_targets")
	@Transient
	private Target[] targets; 
	
	@JsonProperty("stage_poppers")
	@Transient
	private int poppers;
	
	private int maxPoints;
	
	@JsonProperty("stage_deleted")
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

	public int getStageNumber() {
		return stageNumber;
	}

	public void setStageNumber(int stageNumber) {
		this.stageNumber = stageNumber;
	}

	public Calendar getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Calendar modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stage other = (Stage) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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
}
