package fi.ipsc_result_server.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MatchScore implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	@Id
//	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	@JsonProperty("match_id")
//	@Column(nullable = false, length = 36)
	private String matchId;
	
//	@OneToOne(cascade = CascadeType.MERGE)
	private Match match;
	
	@JsonProperty("match_scores")
//	@OneToMany(mappedBy = "matchScore", cascade = CascadeType.ALL)
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

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

}
