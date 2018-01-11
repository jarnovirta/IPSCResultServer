package fi.ipsc_result_server.domain.ResultData;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import fi.ipsc_result_server.domain.Competitor;
import fi.ipsc_result_server.domain.Match;
import fi.ipsc_result_server.domain.ScoreCard;
import fi.ipsc_result_server.domain.Stage;

@Entity
public class CompetitorResultData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@ManyToOne(cascade = CascadeType.MERGE)
	private Match match; 
	
	@ManyToOne(cascade = CascadeType.MERGE)
	private Competitor competitor;
	
	@ElementCollection
	private Map<Stage, ScoreCard> scoreCards;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Map<Stage, ScoreCard> getScoreCards() {
		return scoreCards;
	}

	public void setScoreCards(Map<Stage, ScoreCard> scoreCards) {
		this.scoreCards = scoreCards;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public Competitor getCompetitor() {
		return competitor;
	}

	public void setCompetitor(Competitor competitor) {
		this.competitor = competitor;
	}
	
}
