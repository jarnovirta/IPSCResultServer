package fi.ipsc_result_server.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StageScore implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	@Id
//	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	@JsonProperty("stage_uuid")
//	@Column(nullable = false, length = 36)
	private String stageId;	
	
	@JsonProperty("stage_number")
//	@Column(nullable = false)
	private int stageNumber;
	
//	@ManyToOne(cascade = CascadeType.MERGE)
	private Stage stage;
	
	@JsonProperty("stage_stagescores")
//	@OneToMany(mappedBy = "stageScore", cascade = CascadeType.ALL)
	private List<ScoreCard> scoreCards;
	
//	@ManyToOne(cascade = CascadeType.MERGE)
	private MatchScore matchScore;

	public String getStageId() {
		return stageId;
	}

	public void setStageId(String stageId) {
		this.stageId = stageId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public MatchScore getMatchScore() {
		return matchScore;
	}

	public void setMatchScore(MatchScore matchScore) {
		this.matchScore = matchScore;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
