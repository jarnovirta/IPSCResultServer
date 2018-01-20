package fi.ipscResultServer.domain;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "IPSCMatch")
public class Match implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@JsonProperty("match_id")
	@Column(length = 36)
	private String id;
	
	@JsonProperty("match_name")
	@Column(nullable = false)
	private String name;
	
	@Enumerated(EnumType.ORDINAL)
	private MatchStatus status = MatchStatus.CLOSED;
	
	
	@JsonProperty("match_level")
	private String level;

	@JsonProperty("match_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Calendar date;

	@JsonProperty("match_modifieddate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Calendar modifiedDate;

	@JsonProperty("match_cats")
	@Transient
	private List<String> divisionStrings;
	
	@Enumerated(EnumType.ORDINAL)
	private List<IPSCDivision> divisions;
	
	@Enumerated(EnumType.ORDINAL)
	private List<IPSCDivision> divisionsWithResults;
		
	@JsonProperty("match_stages")
	@OneToMany(mappedBy = "match", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@OrderColumn
	private List<Stage> stages;
	
	@JsonProperty("match_shooters")
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Competitor> competitors; 

	public Match() { }
	
	public Match(String id, String name, MatchStatus status) { 
		this.id = id;
		this.name = name;
		this.status = status;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public Calendar getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Calendar modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public List<String> getDivisionStrings() {
		return divisionStrings;
	}

	public void setDivisionStrings(List<String> divisionStrings) {
		this.divisionStrings = divisionStrings;
		this.divisions = new ArrayList<IPSCDivision>();
		for (String divisionString : this.divisionStrings) {
			this.divisions.add(IPSCDivision.valueOf(divisionString.toUpperCase()));
		}
	}

	public List<IPSCDivision> getDivisions() {
		return divisions;
	}

	public void setDivisions(List<IPSCDivision> divisions) {
		this.divisions = divisions;
	}

	public List<Stage> getStages() {
		return stages;
	}

	public void setStages(List<Stage> stages) {
		this.stages = stages;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public List<Competitor> getCompetitors() {
		return competitors;
	}

	public void setCompetitors(List<Competitor> competitors) {
		this.competitors = competitors;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<IPSCDivision> getDivisionsWithResults() {
		return divisionsWithResults;
	}

	public void setDivisionsWithResults(List<IPSCDivision> divisionsWithResults) {
		this.divisionsWithResults = divisionsWithResults;
	}

	public MatchStatus getStatus() {
		return status;
	}

	public void setStatus(MatchStatus status) {
		this.status = status;
	}
	
}
