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
public class MatchScore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	@JsonProperty("match_id")
	@Column(nullable = false, length = 36)
	private String matchId;
	
	@JsonProperty("match_scores")
	@OneToMany(cascade = CascadeType.ALL)
	private List<StageScore> stageScores;


	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<StageScore> getStageScores() {
		return stageScores;
	}

	public void setStageScores(List<StageScore> stageScores) {
		this.stageScores = stageScores;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
