package fi.ipsc_result_server.domain.ResultData;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import fi.ipsc_result_server.domain.Competitor;
import fi.ipsc_result_server.domain.Match;
import fi.ipsc_result_server.domain.ScoreCard;

@Entity
public class CompetitorResultData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@ManyToOne
	private Match match; 
	
	@ManyToOne
	private Competitor competitor;
	
	// ScoreCard instances mapped to Stage id's
	private Map<String, ScoreCard> scoreCards; 

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Map<String, ScoreCard> getScoreCards() {
		return scoreCards;
	}

	public void setScoreCards(Map<String, ScoreCard> scoreCards) {
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
