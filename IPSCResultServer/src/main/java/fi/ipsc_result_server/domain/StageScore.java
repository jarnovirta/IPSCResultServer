package fi.ipsc_result_server.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class StageScore implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	@JsonProperty("stage_uuid")
	@Column(nullable = false)
	private String uuid;
	
	@JsonProperty("stage_number")
	@Column(nullable = false)
	private int stageNumber;
	
	@JsonProperty("stage_stagescores")
	@OneToMany(cascade = CascadeType.ALL)
	private List<ScoreCard> scoreCards;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getStageNumber() {
		return stageNumber;
	}

	public void setStageNumber(int stageNumber) {
		this.stageNumber = stageNumber;
	}

	public List<ScoreCard> getScoreCards() {
		return scoreCards;
	}

	public void setScoreCards(List<ScoreCard> scoreCards) {
		this.scoreCards = scoreCards;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
